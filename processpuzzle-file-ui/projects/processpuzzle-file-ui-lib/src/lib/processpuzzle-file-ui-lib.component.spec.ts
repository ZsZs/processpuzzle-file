import { async, ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpClientTestingModule } from '@angular/common/http/testing';

import { FileUploadComponent } from './file-upload.component';
import { ProcesspuzzleFileUiLibComponent } from './processpuzzle-file-ui-lib.component';

describe('ProcesspuzzleFileUiLibComponent', () => {
  let component: ProcesspuzzleFileUiLibComponent;
  let fixture: ComponentFixture<ProcesspuzzleFileUiLibComponent>;

  beforeEach(async(() => {
    TestBed.configureTestingModule({
      declarations: [ FileUploadComponent, ProcesspuzzleFileUiLibComponent ],
      imports: [HttpClientTestingModule]
    })
    .compileComponents();
  }));

  beforeEach(() => {
    fixture = TestBed.createComponent(ProcesspuzzleFileUiLibComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
