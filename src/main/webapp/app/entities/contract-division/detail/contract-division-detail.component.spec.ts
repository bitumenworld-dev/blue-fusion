import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ContractDivisionDetailComponent } from './contract-division-detail.component';

describe('ContractDivision Management Detail Component', () => {
  let comp: ContractDivisionDetailComponent;
  let fixture: ComponentFixture<ContractDivisionDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ContractDivisionDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./contract-division-detail.component').then(m => m.ContractDivisionDetailComponent),
              resolve: { contractDivision: () => of({ id: 18852 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ContractDivisionDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ContractDivisionDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load contractDivision on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ContractDivisionDetailComponent);

      // THEN
      expect(instance.contractDivision()).toEqual(expect.objectContaining({ id: 18852 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
