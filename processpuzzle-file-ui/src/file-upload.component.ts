import { Component } from '@angular/core';
import { HttpClient, HttpEventType } from '@angular/common/http';

@Component( {
   selector: 'file-upload-component',
   templateUrl: './file-upload.component.html'
} )
export class FileUploadComponent {
   selectedFile: File = null;

   constructor( private httpClient: HttpClient ) {
   }

   // public accessors and mutators

   // event handling methods
   onFileSelected( event: any ) {
      this.selectedFile = event.target.files[0];
   }

   onUpload() {
      const uploadData = new FormData();
      uploadData.append( 'file', this.selectedFile, this.selectedFile.name );
      this.httpClient.post( '', uploadData , { reportProgress: true, observe: 'events' }).subscribe( event => {
         if ( event.type === HttpEventType.UploadProgress ) {
            console.log( 'Upload Progress: ' + Math.round( event.loaded / event.total ) * 100 + '%' );
         } else if ( event.type === HttpEventType.Response ) {
            console.log( event );
         }
      });
   }

   // protected, private helper methods
}
