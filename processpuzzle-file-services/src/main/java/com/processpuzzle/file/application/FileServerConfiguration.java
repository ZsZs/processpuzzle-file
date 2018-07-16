package com.processpuzzle.file.application;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Source;

import org.h2.server.web.WebServlet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.web.WebMvcRegistrationsAdapter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.http.MediaType;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.ResourceHttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.http.converter.support.AllEncompassingFormHttpMessageConverter;
import org.springframework.http.converter.xml.SourceHttpMessageConverter;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.module.SimpleModule;

@Configuration
public class FileServerConfiguration extends WebMvcConfigurerAdapter {
   private static final String SPRING_HATEOAS_OBJECT_MAPPER = "_halObjectMapper";
   @Autowired @Qualifier( SPRING_HATEOAS_OBJECT_MAPPER ) private ObjectMapper springHateoasObjectMapper;
   @Autowired private Jackson2ObjectMapperBuilder springBootObjectMapperBuilder;

   @Bean public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
      ByteArrayHttpMessageConverter arrayHttpMessageConverter = new ByteArrayHttpMessageConverter();
      arrayHttpMessageConverter.setSupportedMediaTypes( getSupportedMediaTypes() );
      return arrayHttpMessageConverter;
   }

   @Bean public FilterRegistrationBean corsFilter() {
      UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
      CorsConfiguration config = new CorsConfiguration();
      config.setAllowCredentials( true );
      config.addAllowedOrigin( "*" );
      config.addAllowedHeader( "*" );
      config.addAllowedMethod( "*" );
      source.registerCorsConfiguration( "/**", config );
      FilterRegistrationBean bean = new FilterRegistrationBean( new CorsFilter( source ) );
      bean.setOrder( 0 );
      return bean;
   }

   @Override public void configureMessageConverters( List<HttpMessageConverter<?>> converters ) {
      StringHttpMessageConverter stringConverter = new StringHttpMessageConverter();
      stringConverter.setWriteAcceptCharset( false );

      converters.add( byteArrayHttpMessageConverter() );
      converters.add( stringConverter );
      converters.add( new ResourceHttpMessageConverter() );
      converters.add( new SourceHttpMessageConverter<Source>() );
      converters.add( new AllEncompassingFormHttpMessageConverter() );
      converters.add( jackson2Converter() );
   }

   @Bean public MappingJackson2HttpMessageConverter jackson2Converter() {
      MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
      converter.setObjectMapper( objectMapper() );
      return converter;
   }
   
   @Bean( name = "objectMapper" ) @Primary public ObjectMapper objectMapper() {
      springHateoasObjectMapper.configure( SerializationFeature.INDENT_OUTPUT, true );
      this.springBootObjectMapperBuilder.configure( this.springHateoasObjectMapper );
      springHateoasObjectMapper.setSerializationInclusion( Include.NON_NULL );

      registerJsonTypeMappings();
      
      return springHateoasObjectMapper;
   }

   // protected, private helper methods
   private List<MediaType> getSupportedMediaTypes() {
      List<MediaType> list = new ArrayList<>();
      list.add( MediaType.IMAGE_JPEG );
      list.add( MediaType.IMAGE_PNG );
      list.add( MediaType.APPLICATION_PDF );
      list.add( MediaType.APPLICATION_OCTET_STREAM );
      return list;
   }

   private void registerJsonTypeMappings() {
      SimpleModule jsonMapperModule = new SimpleModule();
      jsonMapperModule.addAbstractTypeMapping( Contact.class, ContactEntity.class );
      jsonMapperModule.addAbstractTypeMapping( Document.class, DocumentEntity.class );
      jsonMapperModule.addAbstractTypeMapping( Part.class, PartEntity.class );
      jsonMapperModule.addAbstractTypeMapping( Project.class, ProjectEntity.class );
      jsonMapperModule.addAbstractTypeMapping( Tool.class, ToolEntity.class );
      springHateoasObjectMapper.registerModule( jsonMapperModule );
   }

   @Configuration
   @SuppressWarnings( "squid:MaximumInheritanceDepth" )
   public class WebConfig {
      @Bean ServletRegistrationBean h2servletRegistration() {
         ServletRegistrationBean registrationBean = new ServletRegistrationBean( new WebServlet() );
         registrationBean.addUrlMappings( "/console/*" );
         return registrationBean;
      }

      @Bean public WebMvcRegistrationsAdapter webMvcRegistrationsHandlerMapping() {
         return new WebMvcRegistrationsAdapter() {
            @Override public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
               return new RequestMappingHandlerMapping() {
                  private static final String API_BASE_PATH = "api";

                  @Override protected void registerHandlerMethod( Object handler, Method method, RequestMappingInfo mapping ) {
                     Class<?> beanType = method.getDeclaringClass();
                     RequestMappingInfo newMapping;
                     if( AnnotationUtils.findAnnotation( beanType, RestController.class ) != null ){
                        PatternsRequestCondition apiPattern = new PatternsRequestCondition( API_BASE_PATH ).combine( mapping.getPatternsCondition() );

                        // @formatter:off
                        newMapping = new RequestMappingInfo( mapping.getName(), 
                                                             apiPattern, 
                                                             mapping.getMethodsCondition(), 
                                                             mapping.getParamsCondition(),
                                                             mapping.getHeadersCondition(), 
                                                             mapping.getConsumesCondition(), 
                                                             mapping.getProducesCondition(), 
                                                             mapping.getCustomCondition() );
                        // @formatter:on
                     }else {
                        newMapping = mapping;
                     }

                     super.registerHandlerMethod( handler, method, newMapping );
                  }
               };
            }
         };
      }
   }
}
