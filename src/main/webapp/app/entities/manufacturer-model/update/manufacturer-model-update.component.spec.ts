import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ManufacturerModelService } from '../service/manufacturer-model.service';
import { IManufacturerModel } from '../manufacturer-model.model';
import { ManufacturerModelFormService } from './manufacturer-model-form.service';

import { ManufacturerModelUpdateComponent } from './manufacturer-model-update.component';

describe('ManufacturerModel Management Update Component', () => {
  let comp: ManufacturerModelUpdateComponent;
  let fixture: ComponentFixture<ManufacturerModelUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let manufacturerModelFormService: ManufacturerModelFormService;
  let manufacturerModelService: ManufacturerModelService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ManufacturerModelUpdateComponent],
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
      .overrideTemplate(ManufacturerModelUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ManufacturerModelUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    manufacturerModelFormService = TestBed.inject(ManufacturerModelFormService);
    manufacturerModelService = TestBed.inject(ManufacturerModelService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const manufacturerModel: IManufacturerModel = { id: 4064 };

      activatedRoute.data = of({ manufacturerModel });
      comp.ngOnInit();

      expect(comp.manufacturerModel).toEqual(manufacturerModel);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IManufacturerModel>>();
      const manufacturerModel = { id: 16163 };
      jest.spyOn(manufacturerModelFormService, 'getManufacturerModel').mockReturnValue(manufacturerModel);
      jest.spyOn(manufacturerModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ manufacturerModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: manufacturerModel }));
      saveSubject.complete();

      // THEN
      expect(manufacturerModelFormService.getManufacturerModel).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(manufacturerModelService.update).toHaveBeenCalledWith(expect.objectContaining(manufacturerModel));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IManufacturerModel>>();
      const manufacturerModel = { id: 16163 };
      jest.spyOn(manufacturerModelFormService, 'getManufacturerModel').mockReturnValue({ id: null });
      jest.spyOn(manufacturerModelService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ manufacturerModel: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: manufacturerModel }));
      saveSubject.complete();

      // THEN
      expect(manufacturerModelFormService.getManufacturerModel).toHaveBeenCalled();
      expect(manufacturerModelService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IManufacturerModel>>();
      const manufacturerModel = { id: 16163 };
      jest.spyOn(manufacturerModelService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ manufacturerModel });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(manufacturerModelService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
