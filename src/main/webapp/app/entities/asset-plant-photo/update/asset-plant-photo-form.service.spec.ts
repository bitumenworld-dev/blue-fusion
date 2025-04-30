import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../asset-plant-photo.test-samples';

import { AssetPlantPhotoFormService } from './asset-plant-photo-form.service';

describe('AssetPlantPhoto Form Service', () => {
  let service: AssetPlantPhotoFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssetPlantPhotoFormService);
  });

  describe('Service methods', () => {
    describe('createAssetPlantPhotoFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssetPlantPhotoFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetPlantPhotoId: expect.any(Object),
            name: expect.any(Object),
            image: expect.any(Object),
            assetPlantId: expect.any(Object),
            assetPlantPhotoLabel: expect.any(Object),
          }),
        );
      });

      it('passing IAssetPlantPhoto should create a new form with FormGroup', () => {
        const formGroup = service.createAssetPlantPhotoFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetPlantPhotoId: expect.any(Object),
            name: expect.any(Object),
            image: expect.any(Object),
            assetPlantId: expect.any(Object),
            assetPlantPhotoLabel: expect.any(Object),
          }),
        );
      });
    });

    describe('getAssetPlantPhoto', () => {
      it('should return NewAssetPlantPhoto for default AssetPlantPhoto initial value', () => {
        const formGroup = service.createAssetPlantPhotoFormGroup(sampleWithNewData);

        const assetPlantPhoto = service.getAssetPlantPhoto(formGroup) as any;

        expect(assetPlantPhoto).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssetPlantPhoto for empty AssetPlantPhoto initial value', () => {
        const formGroup = service.createAssetPlantPhotoFormGroup();

        const assetPlantPhoto = service.getAssetPlantPhoto(formGroup) as any;

        expect(assetPlantPhoto).toMatchObject({});
      });

      it('should return IAssetPlantPhoto', () => {
        const formGroup = service.createAssetPlantPhotoFormGroup(sampleWithRequiredData);

        const assetPlantPhoto = service.getAssetPlantPhoto(formGroup) as any;

        expect(assetPlantPhoto).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssetPlantPhoto should not enable id FormControl', () => {
        const formGroup = service.createAssetPlantPhotoFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssetPlantPhoto should disable id FormControl', () => {
        const formGroup = service.createAssetPlantPhotoFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
