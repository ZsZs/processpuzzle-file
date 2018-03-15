import {ComponentFixture, TestBed} from '@angular/core/testing';
import {By} from '@angular/platform-browser';
import {DebugElement} from '@angular/core';

import {SampleComponent} from './sample.component';

describe('SampleComponent', () => {
    let comp: SampleComponent;
    let fixture: ComponentFixture<SampleComponent>;
    let de: DebugElement;
    /* tslint:disable-next-line */
    let el: HTMLElement;

    beforeEach(() => {
        TestBed.configureTestingModule({
            declarations: [SampleComponent], // declare the test component
        });

        fixture = TestBed.createComponent(SampleComponent);

        comp = fixture.componentInstance; // BannerComponent test instance

        // query for the title <h1> by CSS element selector
        de = fixture.debugElement.query(By.css('h1'));
        el = de.nativeElement;
    });

    it('should create component', () => expect(comp).toBeDefined());

    it('should have expected <h1> text', () => {
        fixture.detectChanges();
        expect(el.innerText).toMatch('Sample component' );
    });
});
