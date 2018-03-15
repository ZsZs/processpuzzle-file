package com.processpuzzle.file.application;

import java.net.MalformedURLException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.http.client.utils.URIBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;

public abstract class ImgWebAppProperties {
   protected static final Logger logger = LoggerFactory.getLogger( ImgWebAppProperties.class );
   protected Environment environment;
   protected String contextPath;
   protected String host = "localhost";
   protected String port = "8124";
   protected String protocol = "http";
   protected int timeout = 1000;

   public ImgWebAppProperties( @Autowired Environment environment ) {
     this.environment = environment;
     contextPath = environment.getProperty( "server.contextPath" );
   }
   
   // public accessors and mutators
   public URL buildApplicationUrl() {
      URL applicationUrl = null;
      String hostUrl = null;
      String path = !contextPath.isEmpty() ? contextPath : "";
      try{
         URI uri = new URIBuilder().setScheme( protocol ).setHost( host ).setPort( new Integer( port )).setPath( path ).build();
         hostUrl = uri.toURL().toString();
         applicationUrl = new URL( hostUrl );
      }catch( MalformedURLException | NumberFormatException | URISyntaxException e ){
         logger.error( "Failed to build application URL.", e );
      }
      
      return applicationUrl;
   }
   
   @java.lang.SuppressWarnings( "squid:S1075" )
   public URL buildResoruceUrl( String resourceUri ) {
      URL urlForResource = null;
      String hostUrl = null;
      String path = !contextPath.isEmpty() ? contextPath + "/" + resourceUri : resourceUri;
      try{
         URI uri = new URIBuilder().setScheme( protocol ).setHost( host ).setPort( new Integer( port )).setPath( path ).build();
         hostUrl = uri.toURL().toString();
         urlForResource = new URL( hostUrl );
      }catch( MalformedURLException | NumberFormatException | URISyntaxException e ){
         logger.error( "Failed to build resource URI.", e );
      }
      
      return urlForResource;
   }

   // properties
   // @formatter:off
   public String getContextPath() { return contextPath; }
   public String getHost() { return host; }
   public String getPort() { return port; }
   public String getProtocol() { return protocol; }
   public int getTimeout() { return timeout; }
   public void setContextPath( String contextPath ) { this.contextPath = contextPath; }
   public void setHost( String host ) { this.host = host; }
   public void setPort( String port ) { this.port = port; }
   public void setProtocol( String protocol ) { this.protocol = protocol; }
   public void setTimeout( int timeout ) { this.timeout = timeout; }
   // @formatter:on
   
   // protected private helper methods
}
