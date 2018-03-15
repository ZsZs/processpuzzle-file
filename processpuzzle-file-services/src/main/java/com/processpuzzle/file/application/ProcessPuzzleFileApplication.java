package com.processpuzzle.file.application;

import javax.annotation.PreDestroy;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ProcessPuzzleFileApplication {
   private static Logger logger = LoggerFactory.getLogger( ProcessPuzzleFileApplication.class );

   public static void main( String[] args ) {
      logger.info( "About to start ProcessPuzzle-File." );
      SpringApplication.run( ProcessPuzzleFileApplication.class, args );
   }

   @PreDestroy
   public void shutDown() {
      logger.info( "About to stop ProcessPuzzle-File." );
   }
}
