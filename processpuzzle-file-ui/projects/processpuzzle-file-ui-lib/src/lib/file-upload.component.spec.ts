import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { AngularFireStorage } from 'angularfire2/storage';

import { FileUploadComponent } from './file-upload.component';
import { AngularFireStorageReferenceStub, CommonServiceModuleStub } from './common-service-module-stub';

describe('FileUploadComponent', () => {
    const mockParts = [ new Blob(['you construct a file...'], {type: 'text/plain'}), ' Same way as you do with blob', new Uint16Array([33]) ];
    const mockFile = new File( mockParts, 'sample.txt' );
    const mockEvent: Event = <Event><any>{ 'target': { files: [mockFile] }};

    let fileUploadComponent: FileUploadComponent;
    let fixture: ComponentFixture<FileUploadComponent>;
    let de: DebugElement;
    /* tslint:disable-next-line */
    let el: HTMLElement;
    let afStorage: AngularFireStorage;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [FileUploadComponent],
            imports: [ CommonServiceModuleStub ],
            providers: []
        });

        fixture = TestBed.createComponent(FileUploadComponent);
        fileUploadComponent = fixture.componentInstance;

        afStorage = TestBed.get( AngularFireStorage );

        // query for the title <h1> by CSS element selector
        de = fixture.debugElement.query(By.css('h6'));
        el = de.nativeElement;
    });

    it( 'should create component', () => expect( fileUploadComponent ).toBeDefined());

    it( 'should have expected <h6> text', () => {
        fixture.detectChanges();
        expect(el.innerText).toMatch('File Upload' );
    });

    it( 'onFileSelected() remembers file name', () => {
        fileUploadComponent.onFileSelected( mockEvent );
        expect( fileUploadComponent.selectedFile ).toEqual( mockFile );
    });

    it( 'onUpload() send selected files data', () => {
        const spy = spyOn( afStorage, 'ref' ).and.returnValue( new AngularFireStorageReferenceStub() );

        fileUploadComponent.onFileSelected( mockEvent );

        fileUploadComponent.onUpload();

        expect( afStorage.ref ).toHaveBeenCalled();
    });
});
