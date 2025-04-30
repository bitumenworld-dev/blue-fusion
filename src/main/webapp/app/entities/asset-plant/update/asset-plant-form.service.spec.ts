import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../asset-plant.test-samples';

import { AssetPlantFormService } from './asset-plant-form.service';

describe('AssetPlant Form Service', () => {
  let service: AssetPlantFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(AssetPlantFormService);
  });

  describe('Service methods', () => {
    describe('createAssetPlantFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createAssetPlantFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetPlantId: expect.any(Object),
            fleetNumber: expect.any(Object),
            numberPlate: expect.any(Object),
            fleetDescription: expect.any(Object),
            ownerId: expect.any(Object),
            accessibleByCompany: expect.any(Object),
            driverOrOperator: expect.any(Object),
            plantCategoryId: expect.any(Object),
            plantSubcategoryId: expect.any(Object),
            manufacturerId: expect.any(Object),
            modelId: expect.any(Object),
            yearOfManufacture: expect.any(Object),
            colour: expect.any(Object),
            horseOrTrailer: expect.any(Object),
            smrReaderType: expect.any(Object),
            currentSmrIndex: expect.any(Object),
            engineNumber: expect.any(Object),
            engineCapacityCc: expect.any(Object),
            currentSiteId: expect.any(Object),
            currentContractId: expect.any(Object),
            currentOperatorId: expect.any(Object),
            ledgerCode: expect.any(Object),
            fuelType: expect.any(Object),
            tankCapacityLitres: expect.any(Object),
            consumptionUnit: expect.any(Object),
            plantHoursStatus: expect.any(Object),
            isPrimeMover: expect.any(Object),
            capacityTons: expect.any(Object),
            capacityM3Loose: expect.any(Object),
            capacityM3Tight: expect.any(Object),
            maximumConsumption: expect.any(Object),
            minimumConsumption: expect.any(Object),
            maximumSmrOnFullTank: expect.any(Object),
            trackConsumption: expect.any(Object),
            trackSmrReading: expect.any(Object),
            trackService: expect.any(Object),
            isDeleted: expect.any(Object),
            requestWeeklyMileage: expect.any(Object),
            sent: expect.any(Object),
            chassisNumber: expect.any(Object),
            currentLocation: expect.any(Object),
          }),
        );
      });

      it('passing IAssetPlant should create a new form with FormGroup', () => {
        const formGroup = service.createAssetPlantFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            assetPlantId: expect.any(Object),
            fleetNumber: expect.any(Object),
            numberPlate: expect.any(Object),
            fleetDescription: expect.any(Object),
            ownerId: expect.any(Object),
            accessibleByCompany: expect.any(Object),
            driverOrOperator: expect.any(Object),
            plantCategoryId: expect.any(Object),
            plantSubcategoryId: expect.any(Object),
            manufacturerId: expect.any(Object),
            modelId: expect.any(Object),
            yearOfManufacture: expect.any(Object),
            colour: expect.any(Object),
            horseOrTrailer: expect.any(Object),
            smrReaderType: expect.any(Object),
            currentSmrIndex: expect.any(Object),
            engineNumber: expect.any(Object),
            engineCapacityCc: expect.any(Object),
            currentSiteId: expect.any(Object),
            currentContractId: expect.any(Object),
            currentOperatorId: expect.any(Object),
            ledgerCode: expect.any(Object),
            fuelType: expect.any(Object),
            tankCapacityLitres: expect.any(Object),
            consumptionUnit: expect.any(Object),
            plantHoursStatus: expect.any(Object),
            isPrimeMover: expect.any(Object),
            capacityTons: expect.any(Object),
            capacityM3Loose: expect.any(Object),
            capacityM3Tight: expect.any(Object),
            maximumConsumption: expect.any(Object),
            minimumConsumption: expect.any(Object),
            maximumSmrOnFullTank: expect.any(Object),
            trackConsumption: expect.any(Object),
            trackSmrReading: expect.any(Object),
            trackService: expect.any(Object),
            isDeleted: expect.any(Object),
            requestWeeklyMileage: expect.any(Object),
            sent: expect.any(Object),
            chassisNumber: expect.any(Object),
            currentLocation: expect.any(Object),
          }),
        );
      });
    });

    describe('getAssetPlant', () => {
      it('should return NewAssetPlant for default AssetPlant initial value', () => {
        const formGroup = service.createAssetPlantFormGroup(sampleWithNewData);

        const assetPlant = service.getAssetPlant(formGroup) as any;

        expect(assetPlant).toMatchObject(sampleWithNewData);
      });

      it('should return NewAssetPlant for empty AssetPlant initial value', () => {
        const formGroup = service.createAssetPlantFormGroup();

        const assetPlant = service.getAssetPlant(formGroup) as any;

        expect(assetPlant).toMatchObject({});
      });

      it('should return IAssetPlant', () => {
        const formGroup = service.createAssetPlantFormGroup(sampleWithRequiredData);

        const assetPlant = service.getAssetPlant(formGroup) as any;

        expect(assetPlant).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IAssetPlant should not enable id FormControl', () => {
        const formGroup = service.createAssetPlantFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewAssetPlant should disable id FormControl', () => {
        const formGroup = service.createAssetPlantFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
