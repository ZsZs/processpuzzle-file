package com.processpuzzle.file.service;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import de.computerkomplett.img.server.application.RestApiController;
import de.computerkomplett.img.server.document.DocumentNotFoundException;
import de.computerkomplett.img.server.service.ImgService;


@RestApiController( "files" )
public class FileService implements ImgService {
   private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
   private static final Logger logger = LoggerFactory.getLogger( FileService.class );
   @Autowired private StorageService storageService;

   
   @DeleteMapping( path = "{documentId}" ) public void delete(  @PathVariable String documentId, HttpServletResponse response ) {
      logger.debug( "Deleting file { id : " + documentId + "}" );
      try{
         storageService.delete( documentId );
         response.setStatus( HttpStatus.NO_CONTENT.value() );         
      }catch( Exception e ){
         response.setStatus( HttpStatus.NOT_FOUND.value() );         
      }
   }
   
   @GetMapping( path = "{documentId}" ) public void download(  @PathVariable String documentId, HttpServletResponse response ) throws IOException, DocumentNotFoundException {
      logger.debug( "Downloading file { id : " + documentId + "}" );
      Resource resource = null;
      
      try{
         resource = storageService.loadAsResource( documentId );         
      }catch( StorageFileNotFoundException e ){
         logger.debug( "Failed to download file: " + documentId );
      }
      
      if( resource != null && resource.exists() ){
         String contentType = URLConnection.guessContentTypeFromName( resource.getFilename() );
         int contentLength = (int) resource.contentLength();
         String contentDisposition = determineContentDisposition( resource );
         logger.info( "Serving file: " + contentDisposition );
         response.setContentType( contentType );
         response.setContentLength( contentLength );
         response.setHeader( HEADER_CONTENT_DISPOSITION, contentDisposition );
         InputStream in = resource.getInputStream();
         IOUtils.copy( in, response.getOutputStream() );
      }else{
         response.setStatus( HttpStatus.NOT_FOUND.value() );
      }
   }
   
   @PostMapping( "" ) public void upload( @RequestParam( "file" ) MultipartFile file, @RequestParam( value = "path", required = true ) String path, HttpServletResponse response ) throws StoreFileException {
      storageService.store( file, path );
      response.setStatus( HttpStatus.CREATED.value() );
      logger.debug( "You successfully uploaded " + file.getOriginalFilename() + "!" );
   }   

   // protected, private helper methods
   protected String determineContentDisposition( Resource resource ) throws UnsupportedEncodingException {
      String contentDisposition = "inline; filename=" + resource.getFilename();
      return contentDisposition;
   }   
}
