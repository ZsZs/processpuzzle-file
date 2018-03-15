package com.processpuzzle.file.service;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileSystemUtils;
import org.springframework.web.multipart.MultipartFile;

import de.computerkomplett.img.document.Document;
import de.computerkomplett.img.server.document.DocumentNotFoundException;
import de.computerkomplett.img.server.document.DocumentRepository;

@Service
public class FileSystemStorageService implements StorageService {
   private static final Logger logger = LoggerFactory.getLogger( FileSystemStorageService.class );
   @Autowired private DocumentRepository documentRepository;
   private final Path rootLocation;

   // constructors
   @Autowired public FileSystemStorageService( StorageProperties properties ) {
      this.rootLocation = Paths.get( properties.getLocation() );
   }

   // public accessors and mutators
   @Override public void delete( String documentId ) throws StorageDeleteException, DocumentNotFoundException {
      Document document = documentRepository.findById( Long.parseLong( documentId ));
      if( document == null ) {
         throw new DocumentNotFoundException( documentId );
      }else {
         Path fullPath = determineFileFullPath( document.getFileName(), document.getPath() );
         logger.debug( "Deleting file: " + fullPath );
         try{
            Files.delete( fullPath );
         }catch( IOException e ){
            throw new StorageDeleteException( fullPath.toString(), e );
         }
      }
   }

   @Override public void deleteAll() {
      logger.info( "Deleting all stored files!" );
      FileSystemUtils.deleteRecursively( rootLocation.toFile() );
   }

   @Override public void init() throws StorageInitException {
      try{
         Files.createDirectory( rootLocation );
         logger.info( "Created file upload directory: " + rootLocation );
      }catch( FileAlreadyExistsException e ){
         logger.info( "File storage location: " + this.rootLocation + " already exists.");
         throw new StorageInitException( e );
      }catch( IOException e ){
         throw new StorageInitException( e );
      }
   }

   @Override public Path load( String documentId ) throws DocumentNotFoundException, StorageFileNotFoundException {
      Path fullPath = null;
      Document document = documentRepository.findById( Long.parseLong( documentId ));

      if( document == null ) {
         throw new DocumentNotFoundException( documentId );
      }else {
         fullPath = determineFileFullPath( document.getFileName(), document.getPath() );
         logger.debug( "Loading file: " + fullPath.toString() );
         
         if( fullPath == null || !Files.exists( fullPath ) ){
            logger.info( "File not found: " + fullPath );
            throw new StorageFileNotFoundException( fullPath.toString() );
         }
      }
      
      return fullPath;
   }

   @Override public Stream<Path> loadAll() throws StorageFileNotFoundException {
      logger.debug( "Loading all stored files." );
      try{
         return Files.walk( this.rootLocation, 1 ).filter( path -> !path.equals( this.rootLocation ) ).map( path -> this.rootLocation.relativize( path ) );
      }catch( IOException e ){
         throw new StorageFileNotFoundException( "multiple files", e );
      }
   }

   @Override public Resource loadAsResource( String documentId ) throws StorageFileNotFoundException, DocumentNotFoundException {
      Resource resource = null;
      logger.debug( "Loading binary content of document: " + documentId );
      Path file = load( documentId );
      try{
         resource = new ByteArrayResource( Files.readAllBytes( file )){
            @Override public String getFilename(){
               return file.getFileName().toString();
           }
         };
         if( !resource.exists() || !resource.isReadable() ){
            logger.info( "Could not read file: " + file.getFileName() );
         }
      }catch( IOException e ){
         logger.info( "Could not read file: " + file.getFileName() );
         throw new StorageFileNotFoundException( file.getFileName().toString(), e );
      }
      
      return resource;
   }
   
   @Override public void store( MultipartFile file, String path ) throws StoreFileException {
      Path fullPath = determineFileFullPath( file.getOriginalFilename(), path );
      logger.debug( "Stroring binary of document: " + fullPath );
      try{
         if( file.isEmpty() ){
            throw new StoreFileException( fullPath.toString() );
         }
         Files.createDirectories( fullPath );
         Files.copy( file.getInputStream(), fullPath, REPLACE_EXISTING );
      }catch( IOException e ){
         throw new StoreFileException( file.getOriginalFilename(), e );
      }
   }

   // properties
   // @formatter:off
   public Path getRootLocation() { return rootLocation; }
   // @formatter:on
   
   // protected, private helper methods
   private Path determineFileFullPath( final String fileName, final String path ) {
      String fullPath = (path != null && !path.isEmpty()) ? path + "/" : "";
      fullPath += fileName;
      fullPath = stripFilePrefix( fullPath );
      return this.rootLocation.resolve( fullPath );
   }
   
   private String stripFilePrefix( String fileName ){
      String[] searchList = { "classpath:", "file:", "http:" };
      String[] replacementList = { "", "", "" };
      return StringUtils.replaceEach( fileName, searchList, replacementList );
   }
}
