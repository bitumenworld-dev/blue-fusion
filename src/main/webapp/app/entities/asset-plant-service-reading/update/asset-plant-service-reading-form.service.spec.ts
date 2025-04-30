import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../asset-plant-service-reading.test-samples';

import { AssetPlantServiceReadingFormService } from './asset-plant-service-reading-form.service';

describe('AssetPlantServiceReading Form Service', () => {
  let service: AssetPlantServiceReadingFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssetPlantServiceReadingFormService);
  });

  describe('Service methods', () => {
    describe('createAssetPlantServiceReadingFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssetPlantServiceReadingFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetPlantServiceReadingId: expect.any(Object),
            assetPlantId: expect.any(Object),
            nextServiceSmrReading: expect.any(Object),
            estimatedUnitsPerDay: expect.any(Object),
            estimatedNextServiceDate: expect.any(Object),
            latestSmrReadings: expect.any(Object),
            serviceInterval: expect.any(Object),
            lastServiceDate: expect.any(Object),
            latestSmrDate: expect.any(Object),
            lastServiceSmr: expect.any(Object),
            serviceUnit: expect.any(Object),
          }),
        );
      });

      it('passing IAssetPlantServiceReading should create a new form with FormGroup', () => {
        const formGroup = service.createAssetPlantServiceReadingFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetPlantServiceReadingId: expect.any(Object),
            assetPlantId: expect.any(Object),
            nextServiceSmrReading: expect.any(Object),
            estimatedUnitsPerDay: expect.any(Object),
            estimatedNextServiceDate: expect.any(Object),
            latestSmrReadings: expect.any(Object),
            serviceInterval: expect.any(Object),
            lastServiceDate: expect.any(Object),
            latestSmrDate: expect.any(Object),
            lastServiceSmr: expect.any(Object),
            serviceUnit: expect.any(Object),
          }),
        );
      });
    });

    describe('getAssetPlantServiceReading', () => {
      it('should return NewAssetPlantServiceReading for default AssetPlantServiceReading initial value', () => {
        const formGroup = service.createAssetPlantServiceReadingFormGroup(sampleWithNewData);

        const assetPlantServiceReading = service.getAssetPlantServiceReading(formGroup) as any;

        expect(assetPlantServiceReading).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssetPlantServiceReading for empty AssetPlantServiceReading initial value', () => {
        const formGroup = service.createAssetPlantServiceReadingFormGroup();

        const assetPlantServiceReading = service.getAssetPlantServiceReading(formGroup) as any;

        expect(assetPlantServiceReading).toMatchObject({});
      });

      it('should return IAssetPlantServiceReading', () => {
        const formGroup = service.createAssetPlantServiceReadingFormGroup(sampleWithRequiredData);

        const assetPlantServiceReading = service.getAssetPlantServiceReading(formGroup) as any;

        expect(assetPlantServiceReading).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssetPlantServiceReading should not enable id FormControl', () => {
        const formGroup = service.createAssetPlantServiceReadingFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssetPlantServiceReading should disable id FormControl', () => {
        const formGroup = service.createAssetPlantServiceReadingFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
