package com.processpuzzle.file.application;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.google.common.collect.Maps;

@Component
@ConfigurationProperties( prefix = "img-server" )
public class FileServerProperties extends FileWebAppProperties {
   private static final Logger logger = LoggerFactory.getLogger( FileServerProperties.class );
   private String dateFormatter = "yyyy-MM-dd";
   private String dateTimeFormatter = "yyyy-MM-dd HH:mm:ss";
   private int downloadTokenExpiration = 1;
   private String storageService = "fileSystem";
   private Map<String, String> users = Maps.newHashMap(); 
   private String zoneOffset = "+02:00";

   public FileServerProperties( @Autowired Environment environment ) {
      super( environment );
   }
   
   // public accessors and mutators
   public String formatDate( final LocalDateTime date ){
      return date.format( getDateFormatter() );
   }
   
   public String formatDateTime( final LocalDateTime dateTime ){
      return dateTime.format( getDateTimeFormatter() );
   }
   
   public LocalDate parseDate( final String dateText ){
      return LocalDate.parse( dateText, getDateFormatter() );
   }
   
   public BigDecimal parseDateTimeAsMilliseconds( final String dateText ){
      return BigDecimal.valueOf( parseDateTime( dateText ).toInstant( getZoneOffset() ).toEpochMilli() );
   }
   
   public LocalDateTime parseDateTime( final String dateTimeText ){
      LocalDateTime dateTime = null;
      try{
         dateTime = LocalDateTime.parse( dateTimeText, getDateTimeFormatter() );
      }catch( DateTimeParseException e ){
         dateTime = LocalDateTime.of( LocalDate.parse( dateTimeText, getDateFormatter() ), LocalTime.of( 0, 0, 0 ));
      }
      return dateTime;
   }
   
   @PostConstruct public void postCustruct(){
      logger.info( "IMG-Server is configured." );
   }

   // properties
   // @formatter:off
   public DateTimeFormatter getDateFormatter() { return DateTimeFormatter.ofPattern( dateFormatter ); }
   public DateTimeFormatter getDateTimeFormatter() { return DateTimeFormatter.ofPattern( dateTimeFormatter ); }
   public int getDownloadTokenExpiration() { return downloadTokenExpiration; }
   public String getStorageService() { return storageService; }
   public String getUser( String userName ) { return users.get( userName ); }
   public Map<String, String> getUsers() { return users; }
   public ZoneOffset getZoneOffset() { return ZoneOffset.of( zoneOffset ); }
   public void setDateFormatter( String dateFormatter ) { this.dateFormatter = dateFormatter; }
   public void setDateTimeFormatter( String dateTimeFormatter ) { this.dateTimeFormatter = dateTimeFormatter; }
   public void setDownloadTokenExpiration( int downloadTokenExpiration ) { this.downloadTokenExpiration = downloadTokenExpiration; }
   public void setStorageService( String storageService ) { this.storageService = storageService; }
   public void setUsers( Map<String, String> users ) { this.users = users; }
   public void setZoneOffset( String zoneOffset ) { this.zoneOffset = zoneOffset; }
   // @formatter:on
   
}
