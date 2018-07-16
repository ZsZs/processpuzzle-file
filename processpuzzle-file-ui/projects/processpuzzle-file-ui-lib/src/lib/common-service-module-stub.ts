import { Injectable, NgModule } from '@angular/core';
import { AngularFireStorage } from 'angularfire2/storage';
import { UploadMetadata, UploadTaskSnapshot } from 'angularfire2/storage/interfaces';
import { Observable } from 'rxjs';
import { of } from 'rxjs';

export class UploadTaskSnapshotStub implements UploadTaskSnapshot {
   public get bytesTransferred(): number { return 555; }
   public get downloadURL(): string { return 'something'; }
   public get metadata(): any { return null; }
   public get ref(): any { return null; }
   public get state(): any { return null; }
   public get task(): any { return null; }
   public get totalBytes(): number { return 1024; }

   pipe( data: any ): any {
      return null;
   }
}

export class AngularFireUploadTaskStub {
   snapshotChanges(): Observable<UploadTaskSnapshot | undefined> {
      return of( new UploadTaskSnapshotStub() );
   }

   percentageChanges(): Observable<number | undefined> {
      return of( 21 );
   }

   pause(): boolean {
      return false;
   }

   cancel(): boolean {
      return false;
   }

   resume(): boolean {
      return false;
   }
}

export class AngularFireStorageReferenceStub {
   put(data: any, metadata?: UploadMetadata | undefined ): AngularFireUploadTaskStub {
      return new AngularFireUploadTaskStub();
   }
}

@Injectable()
export class AngularFireStorageStub {
   ref( pathToFile: string ) {
      return new AngularFireStorageReferenceStub();
   }
}

@NgModule({
   providers: [{ provide: AngularFireStorage, useClass: AngularFireStorageStub }]
})

export class CommonServiceModuleStub {}

