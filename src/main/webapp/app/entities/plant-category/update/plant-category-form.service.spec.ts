import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../plant-category.test-samples';

import { PlantCategoryFormService } from './plant-category-form.service';

describe('PlantCategory Form Service', () => {
  let service: PlantCategoryFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(PlantCategoryFormService);
  });

  describe('Service methods', () => {
    describe('createPlantCategoryFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createPlantCategoryFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            plantCategoryId: expect.any(Object),
            plantCategoryCode: expect.any(Object),
            plantCategoryName: expect.any(Object),
            plantCategoryDescription: expect.any(Object),
            isAudited: expect.any(Object),
          }),
        );
      });

      it('passing IPlantCategory should create a new form with FormGroup', () => {
        const formGroup = service.createPlantCategoryFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            plantCategoryId: expect.any(Object),
            plantCategoryCode: expect.any(Object),
            plantCategoryName: expect.any(Object),
            plantCategoryDescription: expect.any(Object),
            isAudited: expect.any(Object),
          }),
        );
      });
    });

    describe('getPlantCategory', () => {
      it('should return NewPlantCategory for default PlantCategory initial value', () => {
        const formGroup = service.createPlantCategoryFormGroup(sampleWithNewData);

        const plantCategory = service.getPlantCategory(formGroup) as any;

        expect(plantCategory).toMatchObject(sampleWithNewData);
      });

      it('should return NewPlantCategory for empty PlantCategory initial value', () => {
        const formGroup = service.createPlantCategoryFormGroup();

        const plantCategory = service.getPlantCategory(formGroup) as any;

        expect(plantCategory).toMatchObject({});
      });

      it('should return IPlantCategory', () => {
        const formGroup = service.createPlantCategoryFormGroup(sampleWithRequiredData);

        const plantCategory = service.getPlantCategory(formGroup) as any;

        expect(plantCategory).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IPlantCategory should not enable id FormControl', () => {
        const formGroup = service.createPlantCategoryFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewPlantCategory should disable id FormControl', () => {
        const formGroup = service.createPlantCategoryFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
