import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../fuel-transaction-line.test-samples';

import { FuelTransactionLineFormService } from './fuel-transaction-line-form.service';

describe('FuelTransactionLine Form Service', () => {
  let service: FuelTransactionLineFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelTransactionLineFormService);
  });

  describe('Service methods', () => {
    describe('createFuelTransactionLineFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelTransactionLineFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelTransactionLineId: expect.any(Object),
            fuelTransactionHeaderId: expect.any(Object),
            assetPlantId: expect.any(Object),
            contractDivisionId: expect.any(Object),
            issuanceTypeId: expect.any(Object),
            pumpId: expect.any(Object),
            storageUnitId: expect.any(Object),
            litres: expect.any(Object),
            meterReadingStart: expect.any(Object),
            meterReadingEnd: expect.any(Object),
            multiplier: expect.any(Object),
          }),
        );
      });

      it('passing IFuelTransactionLine should create a new form with FormGroup', () => {
        const formGroup = service.createFuelTransactionLineFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelTransactionLineId: expect.any(Object),
            fuelTransactionHeaderId: expect.any(Object),
            assetPlantId: expect.any(Object),
            contractDivisionId: expect.any(Object),
            issuanceTypeId: expect.any(Object),
            pumpId: expect.any(Object),
            storageUnitId: expect.any(Object),
            litres: expect.any(Object),
            meterReadingStart: expect.any(Object),
            meterReadingEnd: expect.any(Object),
            multiplier: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuelTransactionLine', () => {
      it('should return NewFuelTransactionLine for default FuelTransactionLine initial value', () => {
        const formGroup = service.createFuelTransactionLineFormGroup(sampleWithNewData);

        const fuelTransactionLine = service.getFuelTransactionLine(formGroup) as any;

        expect(fuelTransactionLine).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelTransactionLine for empty FuelTransactionLine initial value', () => {
        const formGroup = service.createFuelTransactionLineFormGroup();

        const fuelTransactionLine = service.getFuelTransactionLine(formGroup) as any;

        expect(fuelTransactionLine).toMatchObject({});
      });

      it('should return IFuelTransactionLine', () => {
        const formGroup = service.createFuelTransactionLineFormGroup(sampleWithRequiredData);

        const fuelTransactionLine = service.getFuelTransactionLine(formGroup) as any;

        expect(fuelTransactionLine).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelTransactionLine should not enable id FormControl', () => {
        const formGroup = service.createFuelTransactionLineFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelTransactionLine should disable id FormControl', () => {
        const formGroup = service.createFuelTransactionLineFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
