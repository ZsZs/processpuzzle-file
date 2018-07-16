import { ComponentFixture, TestBed } from '@angular/core/testing';
import { By } from '@angular/platform-browser';
import { DebugElement } from '@angular/core';
import { HttpClientTestingModule, HttpTestingController } from '@angular/common/http/testing';

import {FileUploadComponent} from './file-upload.component';

describe('FileUploadComponent', () => {
    const mockParts = [ new Blob(['you construct a file...'], {type: 'text/plain'}), ' Same way as you do with blob', new Uint16Array([33]) ];
    const mockFile = new File( mockParts, 'sample.txt' );
    const mockEvent: Event = <Event><any>{ 'target': { files: [mockFile] }};
    
    let fileUploadComponent: FileUploadComponent;
    let fixture: ComponentFixture<FileUploadComponent>;
    let de: DebugElement;
    /* tslint:disable-next-line */
    let el: HTMLElement;
    let http: HttpTestingController;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [FileUploadComponent],
            imports: [HttpClientTestingModule]
        });

        fixture = TestBed.createComponent(FileUploadComponent);
        fileUploadComponent = fixture.componentInstance;

        http = TestBed.get( HttpTestingController );

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
        fileUploadComponent.onFileSelected( mockEvent );
        
        fileUploadComponent.onUpload();
        
        http.expectOne( '' );
        http.verify();
    });
});
