import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../fuel-pump.test-samples';

import { FuelPumpFormService } from './fuel-pump-form.service';

describe('FuelPump Form Service', () => {
  let service: FuelPumpFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelPumpFormService);
  });

  describe('Service methods', () => {
    describe('createFuelPumpFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelPumpFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelPumpId: expect.any(Object),
            fuelPumpNumber: expect.any(Object),
            currentStorageUnitId: expect.any(Object),
          }),
        );
      });

      it('passing IFuelPump should create a new form with FormGroup', () => {
        const formGroup = service.createFuelPumpFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelPumpId: expect.any(Object),
            fuelPumpNumber: expect.any(Object),
            currentStorageUnitId: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuelPump', () => {
      it('should return NewFuelPump for default FuelPump initial value', () => {
        const formGroup = service.createFuelPumpFormGroup(sampleWithNewData);

        const fuelPump = service.getFuelPump(formGroup) as any;

        expect(fuelPump).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelPump for empty FuelPump initial value', () => {
        const formGroup = service.createFuelPumpFormGroup();

        const fuelPump = service.getFuelPump(formGroup) as any;

        expect(fuelPump).toMatchObject({});
      });

      it('should return IFuelPump', () => {
        const formGroup = service.createFuelPumpFormGroup(sampleWithRequiredData);

        const fuelPump = service.getFuelPump(formGroup) as any;

        expect(fuelPump).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelPump should not enable id FormControl', () => {
        const formGroup = service.createFuelPumpFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelPump should disable id FormControl', () => {
        const formGroup = service.createFuelPumpFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
