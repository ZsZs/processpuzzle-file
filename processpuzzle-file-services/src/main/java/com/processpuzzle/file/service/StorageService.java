package com.processpuzzle.file.service;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

import de.computerkomplett.img.server.document.DocumentNotFoundException;

import java.nio.file.Path;
import java.util.stream.Stream;

public interface StorageService {
   void delete( String documentId ) throws StorageDeleteException, DocumentNotFoundException;
   void deleteAll();
   void init() throws StorageInitException;
   Path load( String documentId ) throws DocumentNotFoundException, StorageFileNotFoundException;
   Stream<Path> loadAll() throws StorageFileNotFoundException;
   Resource loadAsResource( String documentId ) throws StorageFileNotFoundException, DocumentNotFoundException;
   void store( MultipartFile file, String path ) throws StoreFileException;
}
