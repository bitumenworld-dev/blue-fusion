import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { ManufacturerModelDetailComponent } from './manufacturer-model-detail.component';

describe('ManufacturerModel Management Detail Component', () => {
  let comp: ManufacturerModelDetailComponent;
  let fixture: ComponentFixture<ManufacturerModelDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [ManufacturerModelDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./manufacturer-model-detail.component').then(m => m.ManufacturerModelDetailComponent),
              resolve: { manufacturerModel: () => of({ id: 16163 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(ManufacturerModelDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(ManufacturerModelDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load manufacturerModel on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', ManufacturerModelDetailComponent);

      // THEN
      expect(instance.manufacturerModel()).toEqual(expect.objectContaining({ id: 16163 }));
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
