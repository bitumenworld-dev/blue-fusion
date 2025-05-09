import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuelIssuanceTypeDetailComponent } from './fuel-issuance-type-detail.component';

describe('FuelIssuanceType Management Detail Component', () => {
  let comp: FuelIssuanceTypeDetailComponent;
  let fixture: ComponentFixture<FuelIssuanceTypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuelIssuanceTypeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./fuel-issuance-type-detail.component').then(m => m.FuelIssuanceTypeDetailComponent),
              resolve: { fuelIssuanceType: () => of({ id: 17477 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuelIssuanceTypeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuelIssuanceTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load fuelIssuanceType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuelIssuanceTypeDetailComponent);

      // THEN
      expect(instance.fuelIssuanceType()).toEqual(expect.objectContaining({ id: 17477 }));
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
