import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { PlantSubcategoryDetailComponent } from './plant-subcategory-detail.component';

describe('PlantSubcategory Management Detail Component', () => {
  let comp: PlantSubcategoryDetailComponent;
  let fixture: ComponentFixture<PlantSubcategoryDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [PlantSubcategoryDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./plant-subcategory-detail.component').then(m => m.PlantSubcategoryDetailComponent),
              resolve: { plantSubcategory: () => of({ id: 10167 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(PlantSubcategoryDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(PlantSubcategoryDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load plantSubcategory on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', PlantSubcategoryDetailComponent);

      // THEN
      expect(instance.plantSubcategory()).toEqual(expect.objectContaining({ id: 10167 }));
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
