import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuelTransactionHeaderDetailComponent } from './fuel-transaction-header-detail.component';

describe('FuelTransactionHeader Management Detail Component', () => {
  let comp: FuelTransactionHeaderDetailComponent;
  let fixture: ComponentFixture<FuelTransactionHeaderDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuelTransactionHeaderDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./fuel-transaction-header-detail.component').then(m => m.FuelTransactionHeaderDetailComponent),
              resolve: { fuelTransactionHeader: () => of({ id: 20230 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuelTransactionHeaderDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuelTransactionHeaderDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load fuelTransactionHeader on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuelTransactionHeaderDetailComponent);

      // THEN
      expect(instance.fuelTransactionHeader()).toEqual(expect.objectContaining({ id: 20230 }));
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
