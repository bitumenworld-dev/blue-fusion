import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { FuelPumpService } from '../service/fuel-pump.service';
import { IFuelPump } from '../fuel-pump.model';
import { FuelPumpFormService } from './fuel-pump-form.service';

import { FuelPumpUpdateComponent } from './fuel-pump-update.component';

describe('FuelPump Management Update Component', () => {
  let comp: FuelPumpUpdateComponent;
  let fixture: ComponentFixture<FuelPumpUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let fuelPumpFormService: FuelPumpFormService;
  let fuelPumpService: FuelPumpService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [FuelPumpUpdateComponent],
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
      .overrideTemplate(FuelPumpUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(FuelPumpUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    fuelPumpFormService = TestBed.inject(FuelPumpFormService);
    fuelPumpService = TestBed.inject(FuelPumpService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const fuelPump: IFuelPump = { id: 7058 };

      activatedRoute.data = of({ fuelPump });
      comp.ngOnInit();

      expect(comp.fuelPump).toEqual(fuelPump);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelPump>>();
      const fuelPump = { id: 3331 };
      jest.spyOn(fuelPumpFormService, 'getFuelPump').mockReturnValue(fuelPump);
      jest.spyOn(fuelPumpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelPump });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelPump }));
      saveSubject.complete();

      // THEN
      expect(fuelPumpFormService.getFuelPump).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(fuelPumpService.update).toHaveBeenCalledWith(expect.objectContaining(fuelPump));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelPump>>();
      const fuelPump = { id: 3331 };
      jest.spyOn(fuelPumpFormService, 'getFuelPump').mockReturnValue({ id: null });
      jest.spyOn(fuelPumpService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelPump: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: fuelPump }));
      saveSubject.complete();

      // THEN
      expect(fuelPumpFormService.getFuelPump).toHaveBeenCalled();
      expect(fuelPumpService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IFuelPump>>();
      const fuelPump = { id: 3331 };
      jest.spyOn(fuelPumpService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ fuelPump });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(fuelPumpService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
