package com.processpuzzle.file.service;

public class StorageInitException extends StorageException {
   private static final long serialVersionUID = 2910679434028171870L;
   private static final String MESSAGE_TEMPLATE = "Failed to initialize storage service.";
   
   // constructors
   public StorageInitException() {
      this( null );
   }

   public StorageInitException( Throwable cause ) {
      super( MESSAGE_TEMPLATE, cause );
   }
}
