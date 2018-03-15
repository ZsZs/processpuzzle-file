package com.processpuzzle.file.service;

import java.text.MessageFormat;

public class StoreFileException extends StorageException {
   private static final long serialVersionUID = 8962950972764313256L;
   private static final String MESSAGE_TEMPLATE = "Failed to store file: {0}.";
   private final String fileName;
   
   // constructors
   public StoreFileException( String fileName ) {
      this( fileName, null );
   }

   public StoreFileException( String fileName, Throwable cause ) {
      super( MessageFormat.format( MESSAGE_TEMPLATE, fileName ), cause );
      this.fileName = fileName;
   }
   
   // properties
   // @formatter:off
   public String getFileName() { return fileName; }
   // @formatter:on

}
