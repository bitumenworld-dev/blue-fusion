import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AssetPlantServiceReadingDetailComponent } from './asset-plant-service-reading-detail.component';

describe('AssetPlantServiceReading Management Detail Component', () => {
  let comp: AssetPlantServiceReadingDetailComponent;
  let fixture: ComponentFixture<AssetPlantServiceReadingDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssetPlantServiceReadingDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () =>
                import('./asset-plant-service-reading-detail.component').then(m => m.AssetPlantServiceReadingDetailComponent),
              resolve: { assetPlantServiceReading: () => of({ id: 30847 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AssetPlantServiceReadingDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssetPlantServiceReadingDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load assetPlantServiceReading on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AssetPlantServiceReadingDetailComponent);

      // THEN
      expect(instance.assetPlantServiceReading()).toEqual(expect.objectContaining({ id: 30847 }));
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
