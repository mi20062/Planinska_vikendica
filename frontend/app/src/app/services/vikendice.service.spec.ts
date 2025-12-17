import { TestBed } from '@angular/core/testing';

import { VikendiceService } from './vikendice.service';

describe('VikendiceService', () => {
  let service: VikendiceService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(VikendiceService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
