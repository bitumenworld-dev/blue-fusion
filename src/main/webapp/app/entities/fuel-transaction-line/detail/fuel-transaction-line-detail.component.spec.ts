import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuelTransactionLineDetailComponent } from './fuel-transaction-line-detail.component';

describe('FuelTransactionLine Management Detail Component', () => {
  let comp: FuelTransactionLineDetailComponent;
  let fixture: ComponentFixture<FuelTransactionLineDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuelTransactionLineDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./fuel-transaction-line-detail.component').then(m => m.FuelTransactionLineDetailComponent),
              resolve: { fuelTransactionLine: () => of({ id: 26931 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuelTransactionLineDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuelTransactionLineDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load fuelTransactionLine on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuelTransactionLineDetailComponent);

      // THEN
      expect(instance.fuelTransactionLine()).toEqual(expect.objectContaining({ id: 26931 }));
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
