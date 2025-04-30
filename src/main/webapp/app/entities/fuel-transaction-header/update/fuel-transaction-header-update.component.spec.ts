import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FuelTransactionHeaderService } from '../service/fuel-transaction-header.service';
import { IFuelTransactionHeader } from '../fuel-transaction-header.model';
import { FuelTransactionHeaderFormService } from './fuel-transaction-header-form.service';

import { FuelTransactionHeaderUpdateComponent } from './fuel-transaction-header-update.component';

describe('FuelTransactionHeader Management Update Component', () => {
  let comp: FuelTransactionHeaderUpdateComponent;
  let fixture: ComponentFixture<FuelTransactionHeaderUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelTransactionHeaderFormService: FuelTransactionHeaderFormService;
  let fuelTransactionHeaderService: FuelTransactionHeaderService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuelTransactionHeaderUpdateComponent],
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
      .overrideTemplate(FuelTransactionHeaderUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelTransactionHeaderUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelTransactionHeaderFormService = TestBed.inject(FuelTransactionHeaderFormService);
    fuelTransactionHeaderService = TestBed.inject(FuelTransactionHeaderService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const fuelTransactionHeader: IFuelTransactionHeader = { id: 29654 };

      activatedRoute.data = of({ fuelTransactionHeader });
      comp.ngOnInit();

      expect(comp.fuelTransactionHeader).toEqual(fuelTransactionHeader);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionHeader>>();
      const fuelTransactionHeader = { id: 20230 };
      jest.spyOn(fuelTransactionHeaderFormService, 'getFuelTransactionHeader').mockReturnValue(fuelTransactionHeader);
      jest.spyOn(fuelTransactionHeaderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionHeader });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTransactionHeader }));
      saveSubject.complete();

      // THEN
      expect(fuelTransactionHeaderFormService.getFuelTransactionHeader).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelTransactionHeaderService.update).toHaveBeenCalledWith(expect.objectContaining(fuelTransactionHeader));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionHeader>>();
      const fuelTransactionHeader = { id: 20230 };
      jest.spyOn(fuelTransactionHeaderFormService, 'getFuelTransactionHeader').mockReturnValue({ id: null });
      jest.spyOn(fuelTransactionHeaderService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionHeader: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTransactionHeader }));
      saveSubject.complete();

      // THEN
      expect(fuelTransactionHeaderFormService.getFuelTransactionHeader).toHaveBeenCalled();
      expect(fuelTransactionHeaderService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionHeader>>();
      const fuelTransactionHeader = { id: 20230 };
      jest.spyOn(fuelTransactionHeaderService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionHeader });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelTransactionHeaderService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
