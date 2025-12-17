import { TestBed } from '@angular/core/testing';

import { RezervacijeServisService } from './rezervacije-servis.service';

describe('RezervacijeServisService', () => {
  let service: RezervacijeServisService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(RezervacijeServisService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
