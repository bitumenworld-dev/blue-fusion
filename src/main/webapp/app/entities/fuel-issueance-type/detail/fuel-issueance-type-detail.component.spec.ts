import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { FuelIssueanceTypeDetailComponent } from './fuel-issueance-type-detail.component';

describe('FuelIssueanceType Management Detail Component', () => {
  let comp: FuelIssueanceTypeDetailComponent;
  let fixture: ComponentFixture<FuelIssueanceTypeDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [FuelIssueanceTypeDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./fuel-issueance-type-detail.component').then(m => m.FuelIssueanceTypeDetailComponent),
              resolve: { fuelIssueanceType: () => of({ id: 17477 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(FuelIssueanceTypeDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(FuelIssueanceTypeDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load fuelIssueanceType on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', FuelIssueanceTypeDetailComponent);

      // THEN
      expect(instance.fuelIssueanceType()).toEqual(expect.objectContaining({ id: 17477 }));
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
