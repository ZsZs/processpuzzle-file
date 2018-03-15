package com.processpuzzle.file.service;

import java.text.MessageFormat;

public class StorageDeleteException extends StorageException {
   private static final long serialVersionUID = -7864198566365332289L;
   private static final String MESSAGE_TEMPLATE = "Failed to delete stored file: {0}.";
   private final String fileName;
   
   // constructors
   public StorageDeleteException( String fileName ) {
      this( fileName, null );
   }

   public StorageDeleteException( String fileName, Throwable cause ) {
      super( MessageFormat.format( MESSAGE_TEMPLATE, fileName ), cause );
      this.fileName = fileName;
   }
   
   // properties
   // @formatter:off
   public String getFileName() { return fileName; }
   // @formatter:on
}
