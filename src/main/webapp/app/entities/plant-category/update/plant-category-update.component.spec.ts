import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PlantCategoryService } from '../service/plant-category.service';
import { IPlantCategory } from '../plant-category.model';
import { PlantCategoryFormService } from './plant-category-form.service';

import { PlantCategoryUpdateComponent } from './plant-category-update.component';

describe('PlantCategory Management Update Component', () => {
  let comp: PlantCategoryUpdateComponent;
  let fixture: ComponentFixture<PlantCategoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plantCategoryFormService: PlantCategoryFormService;
  let plantCategoryService: PlantCategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlantCategoryUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(PlantCategoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantCategoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plantCategoryFormService = TestBed.inject(PlantCategoryFormService);
    plantCategoryService = TestBed.inject(PlantCategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const plantCategory: IPlantCategory = { id: 13748 };

      activatedRoute.data = of({ plantCategory });
      comp.ngOnInit();

      expect(comp.plantCategory).toEqual(plantCategory);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlantCategory>>();
      const plantCategory = { id: 7148 };
      jest.spyOn(plantCategoryFormService, 'getPlantCategory').mockReturnValue(plantCategory);
      jest.spyOn(plantCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantCategory }));
      saveSubject.complete();

      // THEN
      expect(plantCategoryFormService.getPlantCategory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(plantCategoryService.update).toHaveBeenCalledWith(expect.objectContaining(plantCategory));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlantCategory>>();
      const plantCategory = { id: 7148 };
      jest.spyOn(plantCategoryFormService, 'getPlantCategory').mockReturnValue({ id: null });
      jest.spyOn(plantCategoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantCategory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantCategory }));
      saveSubject.complete();

      // THEN
      expect(plantCategoryFormService.getPlantCategory).toHaveBeenCalled();
      expect(plantCategoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlantCategory>>();
      const plantCategory = { id: 7148 };
      jest.spyOn(plantCategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantCategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plantCategoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
