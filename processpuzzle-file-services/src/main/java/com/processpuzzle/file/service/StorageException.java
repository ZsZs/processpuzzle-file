package com.processpuzzle.file.service;

public class StorageException extends Exception {
   private static final long serialVersionUID = -8343813814722816574L;

   // constructors
   public StorageException( String message ) {
      super( message );
   }

   public StorageException( String message, Throwable cause ) {
      super( message, cause );
   }
}
