package com.processpuzzle.file.application;

import java.io.IOException;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.google.common.io.ByteStreams;
import com.processpuzzle.file.service.FileSystemStorageService;


@Configuration
@Profile( "local" )
public class LocalConfiguration {
   private static final Logger logger = LoggerFactory.getLogger( LocalConfiguration.class );
   @Autowired private ResourceLoader resourceLoader;
   @Autowired private FileSystemStorageService storageService;

   @PostConstruct public void initialize(){
      logger.info( "Initializing standalone configuration." );
      storeTestFiles();
   }
   
   @PreDestroy public void shutDown(){
      storageService.deleteAll();
   }
   
   // protected, private helper methods
   private void storeDocumentContent( Document document ) {
      String fileName = "classpath:sampleDocuments/" + document.getFileName();
      Resource fileResource = resourceLoader.getResource( fileName );
      MultipartFile fileToUpload;
      try{
         fileToUpload = new MockMultipartFile( document.getFileName(), document.getFileName(), document.getMimeType(), ByteStreams.toByteArray( fileResource.getInputStream() ));
         storageService.store( fileToUpload, document.getPath() );
      }catch( IOException e ){
         logger.error( "Failed read bytes of file: " + document.getFileName() );
      }catch( StoreFileException e ){
         logger.error( "Filed to store file: " + document.getFileName() );
      }      
   }
   
   private void storeTestFiles(){
      try{
         storageService.init();
      }catch( StorageInitException e ){
         logger.error( "Failed to initialize storage service.", e );
      }
      documentRepository.findAll().forEach( document -> storeDocumentContent( document ));
   }
}
