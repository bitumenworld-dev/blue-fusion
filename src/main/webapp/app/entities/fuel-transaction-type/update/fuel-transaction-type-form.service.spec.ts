import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../fuel-transaction-type.test-samples';

import { FuelTransactionTypeFormService } from './fuel-transaction-type-form.service';

describe('FuelTransactionType Form Service', () => {
  let service: FuelTransactionTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelTransactionTypeFormService);
  });

  describe('Service methods', () => {
    describe('createFuelTransactionTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelTransactionTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelTransactionTypeId: expect.any(Object),
            fuelTransactionType: expect.any(Object),
            isActive: expect.any(Object),
          }),
        );
      });

      it('passing IFuelTransactionType should create a new form with FormGroup', () => {
        const formGroup = service.createFuelTransactionTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelTransactionTypeId: expect.any(Object),
            fuelTransactionType: expect.any(Object),
            isActive: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuelTransactionType', () => {
      it('should return NewFuelTransactionType for default FuelTransactionType initial value', () => {
        const formGroup = service.createFuelTransactionTypeFormGroup(sampleWithNewData);

        const fuelTransactionType = service.getFuelTransactionType(formGroup) as any;

        expect(fuelTransactionType).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelTransactionType for empty FuelTransactionType initial value', () => {
        const formGroup = service.createFuelTransactionTypeFormGroup();

        const fuelTransactionType = service.getFuelTransactionType(formGroup) as any;

        expect(fuelTransactionType).toMatchObject({});
      });

      it('should return IFuelTransactionType', () => {
        const formGroup = service.createFuelTransactionTypeFormGroup(sampleWithRequiredData);

        const fuelTransactionType = service.getFuelTransactionType(formGroup) as any;

        expect(fuelTransactionType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelTransactionType should not enable id FormControl', () => {
        const formGroup = service.createFuelTransactionTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelTransactionType should disable id FormControl', () => {
        const formGroup = service.createFuelTransactionTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
