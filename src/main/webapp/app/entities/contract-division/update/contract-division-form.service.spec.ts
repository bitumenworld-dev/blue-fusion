import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../contract-division.test-samples';

import { ContractDivisionFormService } from './contract-division-form.service';

describe('ContractDivision Form Service', () => {
  let service: ContractDivisionFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ContractDivisionFormService);
  });

  describe('Service methods', () => {
    describe('createContractDivisionFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createContractDivisionFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            contractDivisionId: expect.any(Object),
            contractDivisionNumber: expect.any(Object),
            companyId: expect.any(Object),
            buildSmartReference: expect.any(Object),
            shiftStart: expect.any(Object),
            shiftEnd: expect.any(Object),
            type: expect.any(Object),
            completed: expect.any(Object),
          }),
        );
      });

      it('passing IContractDivision should create a new form with FormGroup', () => {
        const formGroup = service.createContractDivisionFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            contractDivisionId: expect.any(Object),
            contractDivisionNumber: expect.any(Object),
            companyId: expect.any(Object),
            buildSmartReference: expect.any(Object),
            shiftStart: expect.any(Object),
            shiftEnd: expect.any(Object),
            type: expect.any(Object),
            completed: expect.any(Object),
          }),
        );
      });
    });

    describe('getContractDivision', () => {
      it('should return NewContractDivision for default ContractDivision initial value', () => {
        const formGroup = service.createContractDivisionFormGroup(sampleWithNewData);

        const contractDivision = service.getContractDivision(formGroup) as any;

        expect(contractDivision).toMatchObject(sampleWithNewData);
      });

      it('should return NewContractDivision for empty ContractDivision initial value', () => {
        const formGroup = service.createContractDivisionFormGroup();

        const contractDivision = service.getContractDivision(formGroup) as any;

        expect(contractDivision).toMatchObject({});
      });

      it('should return IContractDivision', () => {
        const formGroup = service.createContractDivisionFormGroup(sampleWithRequiredData);

        const contractDivision = service.getContractDivision(formGroup) as any;

        expect(contractDivision).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IContractDivision should not enable id FormControl', () => {
        const formGroup = service.createContractDivisionFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewContractDivision should disable id FormControl', () => {
        const formGroup = service.createContractDivisionFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
