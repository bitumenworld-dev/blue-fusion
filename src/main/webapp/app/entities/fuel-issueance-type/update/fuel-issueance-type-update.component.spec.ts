import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FuelIssueanceTypeService } from '../service/fuel-issueance-type.service';
import { IFuelIssueanceType } from '../fuel-issueance-type.model';
import { FuelIssueanceTypeFormService } from './fuel-issueance-type-form.service';

import { FuelIssueanceTypeUpdateComponent } from './fuel-issueance-type-update.component';

describe('FuelIssueanceType Management Update Component', () => {
  let comp: FuelIssueanceTypeUpdateComponent;
  let fixture: ComponentFixture<FuelIssueanceTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelIssueanceTypeFormService: FuelIssueanceTypeFormService;
  let fuelIssueanceTypeService: FuelIssueanceTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuelIssueanceTypeUpdateComponent],
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
      .overrideTemplate(FuelIssueanceTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelIssueanceTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelIssueanceTypeFormService = TestBed.inject(FuelIssueanceTypeFormService);
    fuelIssueanceTypeService = TestBed.inject(FuelIssueanceTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const fuelIssueanceType: IFuelIssueanceType = { id: 12535 };

      activatedRoute.data = of({ fuelIssueanceType });
      comp.ngOnInit();

      expect(comp.fuelIssueanceType).toEqual(fuelIssueanceType);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelIssueanceType>>();
      const fuelIssueanceType = { id: 17477 };
      jest.spyOn(fuelIssueanceTypeFormService, 'getFuelIssueanceType').mockReturnValue(fuelIssueanceType);
      jest.spyOn(fuelIssueanceTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelIssueanceType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelIssueanceType }));
      saveSubject.complete();

      // THEN
      expect(fuelIssueanceTypeFormService.getFuelIssueanceType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelIssueanceTypeService.update).toHaveBeenCalledWith(expect.objectContaining(fuelIssueanceType));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelIssueanceType>>();
      const fuelIssueanceType = { id: 17477 };
      jest.spyOn(fuelIssueanceTypeFormService, 'getFuelIssueanceType').mockReturnValue({ id: null });
      jest.spyOn(fuelIssueanceTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelIssueanceType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelIssueanceType }));
      saveSubject.complete();

      // THEN
      expect(fuelIssueanceTypeFormService.getFuelIssueanceType).toHaveBeenCalled();
      expect(fuelIssueanceTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelIssueanceType>>();
      const fuelIssueanceType = { id: 17477 };
      jest.spyOn(fuelIssueanceTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelIssueanceType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelIssueanceTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
