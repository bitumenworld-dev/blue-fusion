import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FuelTransactionLineService } from '../service/fuel-transaction-line.service';
import { IFuelTransactionLine } from '../fuel-transaction-line.model';
import { FuelTransactionLineFormService } from './fuel-transaction-line-form.service';

import { FuelTransactionLineUpdateComponent } from './fuel-transaction-line-update.component';

describe('FuelTransactionLine Management Update Component', () => {
  let comp: FuelTransactionLineUpdateComponent;
  let fixture: ComponentFixture<FuelTransactionLineUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelTransactionLineFormService: FuelTransactionLineFormService;
  let fuelTransactionLineService: FuelTransactionLineService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuelTransactionLineUpdateComponent],
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
      .overrideTemplate(FuelTransactionLineUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelTransactionLineUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelTransactionLineFormService = TestBed.inject(FuelTransactionLineFormService);
    fuelTransactionLineService = TestBed.inject(FuelTransactionLineService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const fuelTransactionLine: IFuelTransactionLine = { id: 29745 };

      activatedRoute.data = of({ fuelTransactionLine });
      comp.ngOnInit();

      expect(comp.fuelTransactionLine).toEqual(fuelTransactionLine);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionLine>>();
      const fuelTransactionLine = { id: 26931 };
      jest.spyOn(fuelTransactionLineFormService, 'getFuelTransactionLine').mockReturnValue(fuelTransactionLine);
      jest.spyOn(fuelTransactionLineService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionLine });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTransactionLine }));
      saveSubject.complete();

      // THEN
      expect(fuelTransactionLineFormService.getFuelTransactionLine).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelTransactionLineService.update).toHaveBeenCalledWith(expect.objectContaining(fuelTransactionLine));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionLine>>();
      const fuelTransactionLine = { id: 26931 };
      jest.spyOn(fuelTransactionLineFormService, 'getFuelTransactionLine').mockReturnValue({ id: null });
      jest.spyOn(fuelTransactionLineService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionLine: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelTransactionLine }));
      saveSubject.complete();

      // THEN
      expect(fuelTransactionLineFormService.getFuelTransactionLine).toHaveBeenCalled();
      expect(fuelTransactionLineService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelTransactionLine>>();
      const fuelTransactionLine = { id: 26931 };
      jest.spyOn(fuelTransactionLineService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelTransactionLine });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelTransactionLineService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
