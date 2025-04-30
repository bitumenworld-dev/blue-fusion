import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../site-contract.test-samples';

import { SiteContractFormService } from './site-contract-form.service';

describe('SiteContract Form Service', () => {
  let service: SiteContractFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(SiteContractFormService);
  });

  describe('Service methods', () => {
    describe('createSiteContractFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createSiteContractFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            siteContractId: expect.any(Object),
            siteId: expect.any(Object),
            contractId: expect.any(Object),
          }),
        );
      });

      it('passing ISiteContract should create a new form with FormGroup', () => {
        const formGroup = service.createSiteContractFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            siteContractId: expect.any(Object),
            siteId: expect.any(Object),
            contractId: expect.any(Object),
          }),
        );
      });
    });

    describe('getSiteContract', () => {
      it('should return NewSiteContract for default SiteContract initial value', () => {
        const formGroup = service.createSiteContractFormGroup(sampleWithNewData);

        const siteContract = service.getSiteContract(formGroup) as any;

        expect(siteContract).toMatchObject(sampleWithNewData);
      });

      it('should return NewSiteContract for empty SiteContract initial value', () => {
        const formGroup = service.createSiteContractFormGroup();

        const siteContract = service.getSiteContract(formGroup) as any;

        expect(siteContract).toMatchObject({});
      });

      it('should return ISiteContract', () => {
        const formGroup = service.createSiteContractFormGroup(sampleWithRequiredData);

        const siteContract = service.getSiteContract(formGroup) as any;

        expect(siteContract).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing ISiteContract should not enable id FormControl', () => {
        const formGroup = service.createSiteContractFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewSiteContract should disable id FormControl', () => {
        const formGroup = service.createSiteContractFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
