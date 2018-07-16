import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';

// Third party modules
import { AngularFireModule } from 'angularfire2';
import { AngularFireStorageModule } from 'angularfire2/storage';
import { MaterializeModule } from "angular2-materialize";

// ProcessPuzzle Components
import { ProcesspuzzleFileUiLibComponent } from './processpuzzle-file-ui-lib.component';
import { firebaseConfiguration } from '../environments/firebase';
import { FileUploadComponent } from './file-upload.component';

@NgModule({
  imports: [
    AngularFireModule.initializeApp( firebaseConfiguration ),
    AngularFireStorageModule,
    CommonModule,
    HttpClientModule,
    MaterializeModule
  ],
  declarations: [
    FileUploadComponent,
    ProcesspuzzleFileUiLibComponent
  ],
  exports: [
    FileUploadComponent,
    ProcesspuzzleFileUiLibComponent
  ]
})
export class ProcesspuzzleFileUiLibModule { }
