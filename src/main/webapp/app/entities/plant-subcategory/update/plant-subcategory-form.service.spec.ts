import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../plant-subcategory.test-samples';

import { PlantSubcategoryFormService } from './plant-subcategory-form.service';

describe('PlantSubcategory Form Service', () => {
  let service: PlantSubcategoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlantSubcategoryFormService);
  });

  describe('Service methods', () => {
    describe('createPlantSubcategoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlantSubcategoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            plantSubcategoryId: expect.any(Object),
            plantSubcategoryCode: expect.any(Object),
            plantSubcategoryName: expect.any(Object),
            plantSubcategoryDescription: expect.any(Object),
            isAudited: expect.any(Object),
          }),
        );
      });

      it('passing IPlantSubcategory should create a new form with FormGroup', () => {
        const formGroup = service.createPlantSubcategoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            plantSubcategoryId: expect.any(Object),
            plantSubcategoryCode: expect.any(Object),
            plantSubcategoryName: expect.any(Object),
            plantSubcategoryDescription: expect.any(Object),
            isAudited: expect.any(Object),
          }),
        );
      });
    });

    describe('getPlantSubcategory', () => {
      it('should return NewPlantSubcategory for default PlantSubcategory initial value', () => {
        const formGroup = service.createPlantSubcategoryFormGroup(sampleWithNewData);

        const plantSubcategory = service.getPlantSubcategory(formGroup) as any;

        expect(plantSubcategory).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlantSubcategory for empty PlantSubcategory initial value', () => {
        const formGroup = service.createPlantSubcategoryFormGroup();

        const plantSubcategory = service.getPlantSubcategory(formGroup) as any;

        expect(plantSubcategory).toMatchObject({});
      });

      it('should return IPlantSubcategory', () => {
        const formGroup = service.createPlantSubcategoryFormGroup(sampleWithRequiredData);

        const plantSubcategory = service.getPlantSubcategory(formGroup) as any;

        expect(plantSubcategory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlantSubcategory should not enable id FormControl', () => {
        const formGroup = service.createPlantSubcategoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlantSubcategory should disable id FormControl', () => {
        const formGroup = service.createPlantSubcategoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
