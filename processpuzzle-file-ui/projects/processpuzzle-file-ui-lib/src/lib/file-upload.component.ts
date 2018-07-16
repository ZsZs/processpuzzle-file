import { Component } from '@angular/core';
import { AngularFireStorage, AngularFireUploadTask } from 'angularfire2/storage';
import { Observable } from 'rxjs';
import { map, filter, finalize } from 'rxjs/operators';

@Component( {
   selector: 'ppf-file-upload-component',
   templateUrl: './file-upload.component.html'
} )
export class FileUploadComponent {
   downloadURL: Observable<string>;
   selectedFile: File = null;
   uploadProgress: Observable<number>;
   uploadState: any;
   uploadTask: AngularFireUploadTask;

   constructor( private afStorage: AngularFireStorage ) {}

   // public accessors and mutators

   // event handling methods
   onFileSelected( event: any ) {
      this.selectedFile = event.target.files[0];
   }

   onUpload() {
      let folderReference = this.afStorage.ref( '/upload/to/this-path/' + this.selectedFile.name );
      this.uploadTask = folderReference.put( this.selectedFile );
      this.uploadProgress = this.uploadTask.percentageChanges();
      this.uploadState = this.uploadTask.snapshotChanges().pipe( 
         map( s => this.uploadState = s.state ),
         finalize( () => this.downloadURL = folderReference.getDownloadURL() )
      ).subscribe();
   }

   // protected, private helper methods
}
