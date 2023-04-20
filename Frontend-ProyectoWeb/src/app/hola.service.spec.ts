import { TestBed } from '@angular/core/testing';

import { HolaService } from './hola.service';

describe('HolaService', () => {
  let service: HolaService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(HolaService);
  });

  it('should be created', () => {
    expect(service).toBeTruthy();
  });
});
