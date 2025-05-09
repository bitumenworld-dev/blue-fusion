import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FuelIssuanceTypeService } from '../service/fuel-issuance-type.service';
import { IFuelIssuanceType } from '../fuel-issuance-type.model';
import { FuelIssuanceTypeFormService } from './fuel-issuance-type-form.service';

import { FuelIssuanceTypeUpdateComponent } from './fuel-issuance-type-update.component';

describe('FuelIssuanceType Management Update Component', () => {
  let comp: FuelIssuanceTypeUpdateComponent;
  let fixture: ComponentFixture<FuelIssuanceTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelIssuanceTypeFormService: FuelIssuanceTypeFormService;
  let fuelIssuanceTypeService: FuelIssuanceTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuelIssuanceTypeUpdateComponent],
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
      .overrideTemplate(FuelIssuanceTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelIssuanceTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelIssuanceTypeFormService = TestBed.inject(FuelIssuanceTypeFormService);
    fuelIssuanceTypeService = TestBed.inject(FuelIssuanceTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const fuelIssuanceType: IFuelIssuanceType = { id: 12535 };

      activatedRoute.data = of({ fuelIssuanceType });
      comp.ngOnInit();

      expect(comp.fuelIssuanceType).toEqual(fuelIssuanceType);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelIssuanceType>>();
      const fuelIssuanceType = { id: 17477 };
      jest.spyOn(fuelIssuanceTypeFormService, 'getFuelIssuanceType').mockReturnValue(fuelIssuanceType);
      jest.spyOn(fuelIssuanceTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelIssuanceType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelIssuanceType }));
      saveSubject.complete();

      // THEN
      expect(fuelIssuanceTypeFormService.getFuelIssuanceType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelIssuanceTypeService.update).toHaveBeenCalledWith(expect.objectContaining(fuelIssuanceType));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelIssuanceType>>();
      const fuelIssuanceType = { id: 17477 };
      jest.spyOn(fuelIssuanceTypeFormService, 'getFuelIssuanceType').mockReturnValue({ id: null });
      jest.spyOn(fuelIssuanceTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelIssuanceType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelIssuanceType }));
      saveSubject.complete();

      // THEN
      expect(fuelIssuanceTypeFormService.getFuelIssuanceType).toHaveBeenCalled();
      expect(fuelIssuanceTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelIssuanceType>>();
      const fuelIssuanceType = { id: 17477 };
      jest.spyOn(fuelIssuanceTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelIssuanceType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelIssuanceTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
