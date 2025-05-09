import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../fuel-issuance-type.test-samples';

import { FuelIssuanceTypeFormService } from './fuel-issuance-type-form.service';

describe('FuelIssuanceType Form Service', () => {
  let service: FuelIssuanceTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelIssuanceTypeFormService);
  });

  describe('Service methods', () => {
    describe('createFuelIssuanceTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelIssuanceTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelIssueTypeId: expect.any(Object),
            fuelIssueType: expect.any(Object),
          }),
        );
      });

      it('passing IFuelIssuanceType should create a new form with FormGroup', () => {
        const formGroup = service.createFuelIssuanceTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelIssueTypeId: expect.any(Object),
            fuelIssueType: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuelIssuanceType', () => {
      it('should return NewFuelIssuanceType for default FuelIssuanceType initial value', () => {
        const formGroup = service.createFuelIssuanceTypeFormGroup(sampleWithNewData);

        const fuelIssuanceType = service.getFuelIssuanceType(formGroup) as any;

        expect(fuelIssuanceType).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelIssuanceType for empty FuelIssuanceType initial value', () => {
        const formGroup = service.createFuelIssuanceTypeFormGroup();

        const fuelIssuanceType = service.getFuelIssuanceType(formGroup) as any;

        expect(fuelIssuanceType).toMatchObject({});
      });

      it('should return IFuelIssuanceType', () => {
        const formGroup = service.createFuelIssuanceTypeFormGroup(sampleWithRequiredData);

        const fuelIssuanceType = service.getFuelIssuanceType(formGroup) as any;

        expect(fuelIssuanceType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelIssuanceType should not enable id FormControl', () => {
        const formGroup = service.createFuelIssuanceTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelIssuanceType should disable id FormControl', () => {
        const formGroup = service.createFuelIssuanceTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
