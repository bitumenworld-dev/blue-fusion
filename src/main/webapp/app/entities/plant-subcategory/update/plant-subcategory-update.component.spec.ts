import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { PlantSubcategoryService } from '../service/plant-subcategory.service';
import { IPlantSubcategory } from '../plant-subcategory.model';
import { PlantSubcategoryFormService } from './plant-subcategory-form.service';

import { PlantSubcategoryUpdateComponent } from './plant-subcategory-update.component';

describe('PlantSubcategory Management Update Component', () => {
  let comp: PlantSubcategoryUpdateComponent;
  let fixture: ComponentFixture<PlantSubcategoryUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let plantSubcategoryFormService: PlantSubcategoryFormService;
  let plantSubcategoryService: PlantSubcategoryService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [PlantSubcategoryUpdateComponent],
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
      .overrideTemplate(PlantSubcategoryUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(PlantSubcategoryUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    plantSubcategoryFormService = TestBed.inject(PlantSubcategoryFormService);
    plantSubcategoryService = TestBed.inject(PlantSubcategoryService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const plantSubcategory: IPlantSubcategory = { id: 13792 };

      activatedRoute.data = of({ plantSubcategory });
      comp.ngOnInit();

      expect(comp.plantSubcategory).toEqual(plantSubcategory);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlantSubcategory>>();
      const plantSubcategory = { id: 10167 };
      jest.spyOn(plantSubcategoryFormService, 'getPlantSubcategory').mockReturnValue(plantSubcategory);
      jest.spyOn(plantSubcategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantSubcategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantSubcategory }));
      saveSubject.complete();

      // THEN
      expect(plantSubcategoryFormService.getPlantSubcategory).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(plantSubcategoryService.update).toHaveBeenCalledWith(expect.objectContaining(plantSubcategory));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlantSubcategory>>();
      const plantSubcategory = { id: 10167 };
      jest.spyOn(plantSubcategoryFormService, 'getPlantSubcategory').mockReturnValue({ id: null });
      jest.spyOn(plantSubcategoryService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantSubcategory: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: plantSubcategory }));
      saveSubject.complete();

      // THEN
      expect(plantSubcategoryFormService.getPlantSubcategory).toHaveBeenCalled();
      expect(plantSubcategoryService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IPlantSubcategory>>();
      const plantSubcategory = { id: 10167 };
      jest.spyOn(plantSubcategoryService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ plantSubcategory });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(plantSubcategoryService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
