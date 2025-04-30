import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { AssetPlantDetailComponent } from './asset-plant-detail.component';

describe('AssetPlant Management Detail Component', () => {
  let comp: AssetPlantDetailComponent;
  let fixture: ComponentFixture<AssetPlantDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [AssetPlantDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./asset-plant-detail.component').then(m => m.AssetPlantDetailComponent),
              resolve: { assetPlant: () => of({ id: 18137 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(AssetPlantDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(AssetPlantDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load assetPlant on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', AssetPlantDetailComponent);

      // THEN
      expect(instance.assetPlant()).toEqual(expect.objectContaining({ id: 18137 }));
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
