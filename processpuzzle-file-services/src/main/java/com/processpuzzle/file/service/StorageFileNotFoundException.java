package com.processpuzzle.file.service;

import java.text.MessageFormat;

public class StorageFileNotFoundException extends StorageException {
   private static final long serialVersionUID = -9115396949179291028L;
   private static final String MESSAGE_TEMPLATE = "Stored file: {0} not found.";
   private final String fileName;
   
   // constructors
   public StorageFileNotFoundException( String fileName ) {
      this( fileName, null );
   }

   public StorageFileNotFoundException( String fileName, Throwable cause ) {
      super( MessageFormat.format( MESSAGE_TEMPLATE, fileName ), cause );
      this.fileName = fileName;
   }
   
   // properties
   // @formatter:off
   public String getFileName() { return fileName; }
   // @formatter:on
}