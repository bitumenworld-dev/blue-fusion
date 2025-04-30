import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuelTransactionTypeDetailComponent } from './fuel-transaction-type-detail.component';

describe('FuelTransactionType Management Detail Component', () => {
  let comp: FuelTransactionTypeDetailComponent;
  let fixture: ComponentFixture<FuelTransactionTypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuelTransactionTypeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./fuel-transaction-type-detail.component').then(m => m.FuelTransactionTypeDetailComponent),
              resolve: { fuelTransactionType: () => of({ id: 30874 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuelTransactionTypeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuelTransactionTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load fuelTransactionType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuelTransactionTypeDetailComponent);

      // THEN
      expect(instance.fuelTransactionType()).toEqual(expect.objectContaining({ id: 30874 }));
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
