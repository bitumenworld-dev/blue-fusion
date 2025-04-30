import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../fuel-issueance-type.test-samples';

import { FuelIssueanceTypeFormService } from './fuel-issueance-type-form.service';

describe('FuelIssueanceType Form Service', () => {
  let service: FuelIssueanceTypeFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelIssueanceTypeFormService);
  });

  describe('Service methods', () => {
    describe('createFuelIssueanceTypeFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelIssueanceTypeFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelIssueTypeId: expect.any(Object),
            fuelIssueType: expect.any(Object),
          }),
        );
      });

      it('passing IFuelIssueanceType should create a new form with FormGroup', () => {
        const formGroup = service.createFuelIssueanceTypeFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelIssueTypeId: expect.any(Object),
            fuelIssueType: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuelIssueanceType', () => {
      it('should return NewFuelIssueanceType for default FuelIssueanceType initial value', () => {
        const formGroup = service.createFuelIssueanceTypeFormGroup(sampleWithNewData);

        const fuelIssueanceType = service.getFuelIssueanceType(formGroup) as any;

        expect(fuelIssueanceType).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelIssueanceType for empty FuelIssueanceType initial value', () => {
        const formGroup = service.createFuelIssueanceTypeFormGroup();

        const fuelIssueanceType = service.getFuelIssueanceType(formGroup) as any;

        expect(fuelIssueanceType).toMatchObject({});
      });

      it('should return IFuelIssueanceType', () => {
        const formGroup = service.createFuelIssueanceTypeFormGroup(sampleWithRequiredData);

        const fuelIssueanceType = service.getFuelIssueanceType(formGroup) as any;

        expect(fuelIssueanceType).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelIssueanceType should not enable id FormControl', () => {
        const formGroup = service.createFuelIssueanceTypeFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelIssueanceType should disable id FormControl', () => {
        const formGroup = service.createFuelIssueanceTypeFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
