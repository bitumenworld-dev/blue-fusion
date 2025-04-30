import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../manufacturer-model.test-samples';

import { ManufacturerModelFormService } from './manufacturer-model-form.service';

describe('ManufacturerModel Form Service', () => {
  let service: ManufacturerModelFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(ManufacturerModelFormService);
  });

  describe('Service methods', () => {
    describe('createManufacturerModelFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createManufacturerModelFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modelId: expect.any(Object),
            modelName: expect.any(Object),
          }),
        );
      });

      it('passing IManufacturerModel should create a new form with FormGroup', () => {
        const formGroup = service.createManufacturerModelFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            modelId: expect.any(Object),
            modelName: expect.any(Object),
          }),
        );
      });
    });

    describe('getManufacturerModel', () => {
      it('should return NewManufacturerModel for default ManufacturerModel initial value', () => {
        const formGroup = service.createManufacturerModelFormGroup(sampleWithNewData);

        const manufacturerModel = service.getManufacturerModel(formGroup) as any;

        expect(manufacturerModel).toMatchObject(sampleWithNewData);
      });

      it('should return NewManufacturerModel for empty ManufacturerModel initial value', () => {
        const formGroup = service.createManufacturerModelFormGroup();

        const manufacturerModel = service.getManufacturerModel(formGroup) as any;

        expect(manufacturerModel).toMatchObject({});
      });

      it('should return IManufacturerModel', () => {
        const formGroup = service.createManufacturerModelFormGroup(sampleWithRequiredData);

        const manufacturerModel = service.getManufacturerModel(formGroup) as any;

        expect(manufacturerModel).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IManufacturerModel should not enable id FormControl', () => {
        const formGroup = service.createManufacturerModelFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewManufacturerModel should disable id FormControl', () => {
        const formGroup = service.createManufacturerModelFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
