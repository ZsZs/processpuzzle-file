import { TestBed, inject } from '@angular/core/testing';

import { ProcesspuzzleFileUiLibService } from './processpuzzle-file-ui-lib.service';

describe('ProcesspuzzleFileUiLibService', () => {
  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [ProcesspuzzleFileUiLibService]
    });
  });

  it('should be created', inject([ProcesspuzzleFileUiLibService], (service: ProcesspuzzleFileUiLibService) => {
    expect(service).toBeTruthy();
  }));
});
