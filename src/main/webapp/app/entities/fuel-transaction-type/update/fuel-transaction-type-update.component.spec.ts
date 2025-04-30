import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FuelTransactionTypeService } from '../service/fuel-transaction-type.service';
import { IFuelTransactionType } from '../fuel-transaction-type.model';
import { FuelTransactionTypeFormService } from './fuel-transaction-type-form.service';

import { FuelTransactionTypeUpdateComponent } from './fuel-transaction-type-update.component';

describe('FuelTransactionType Management Update Component', () => {
  let comp: FuelTransactionTypeUpdateComponent;
  let fixture: ComponentFixture<FuelTransactionTypeUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelTransactionTypeFormService: FuelTransactionTypeFormService;
  let fuelTransactionTypeService: FuelTransactionTypeService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuelTransactionTypeUpdateComponent],
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
      .overrideTemplate(FuelTransactionTypeUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelTransactionTypeUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelTransactionTypeFormService = TestBed.inject(FuelTransactionTypeFormService);
    fuelTransactionTypeService = TestBed.inject(FuelTransactionTypeService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const fuelTransactionType: IFuelTransactionType = { id: 3949 };

      activatedRoute.data = of({ fuelTransactionType });
      comp.ngOnInit();

      expect(comp.fuelTransactionType).toEqual(fuelTransactionType);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionType>>();
      const fuelTransactionType = { id: 30874 };
      jest.spyOn(fuelTransactionTypeFormService, 'getFuelTransactionType').mockReturnValue(fuelTransactionType);
      jest.spyOn(fuelTransactionTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTransactionType }));
      saveSubject.complete();

      // THEN
      expect(fuelTransactionTypeFormService.getFuelTransactionType).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelTransactionTypeService.update).toHaveBeenCalledWith(expect.objectContaining(fuelTransactionType));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionType>>();
      const fuelTransactionType = { id: 30874 };
      jest.spyOn(fuelTransactionTypeFormService, 'getFuelTransactionType').mockReturnValue({ id: null });
      jest.spyOn(fuelTransactionTypeService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionType: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTransactionType }));
      saveSubject.complete();

      // THEN
      expect(fuelTransactionTypeFormService.getFuelTransactionType).toHaveBeenCalled();
      expect(fuelTransactionTypeService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionType>>();
      const fuelTransactionType = { id: 30874 };
      jest.spyOn(fuelTransactionTypeService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionType });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelTransactionTypeService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
