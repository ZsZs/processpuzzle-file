import {ComponentFixture, TestBed} from '@angular/core/testing';
import {By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core';

import {FileUploadComponent} from './file-upload.component';

describe('FileUploadComponent', () => {
    let comp: FileUploadComponent;
    let fixture: ComponentFixture<FileUploadComponent>;
    let de: DebugElement;
    /* tslint:disable-next-line */
    let el: HTMLElement;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [FileUploadComponent], // declare the test component
        });

        fixture = TestBed.createComponent(FileUploadComponent);

        comp = fixture.componentInstance; // BannerComponent test instance

        // query for the title <h1> by CSS element selector
        de = fixture.debugElement.query(By.css('h1'));
        el = de.nativeElement;
    });

    it('should create component', () => expect(comp).toBeDefined());

    it('should have expected <h1> text', () => {
        fixture.detectChanges();
        expect(el.innerText).toMatch('File Upload' );
    });
});
