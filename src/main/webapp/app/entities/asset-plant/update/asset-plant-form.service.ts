import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAssetPlant, NewAssetPlant } from '../asset-plant.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssetPlant for edit and NewAssetPlantFormGroupInput for create.
 */
type AssetPlantFormGroupInput = IAssetPlant | PartialWithRequiredKeyOf<NewAssetPlant>;

type AssetPlantFormDefaults = Pick<
  NewAssetPlant,
  'id' | 'isPrimeMover' | 'trackConsumption' | 'trackSmrReading' | 'trackService' | 'isDeleted' | 'requestWeeklyMileage' | 'sent'
>;

type AssetPlantFormGroupContent = {
  id: FormControl<IAssetPlant['id'] | NewAssetPlant['id']>;
  assetPlantId: FormControl<IAssetPlant['assetPlantId']>;
  fleetNumber: FormControl<IAssetPlant['fleetNumber']>;
  numberPlate: FormControl<IAssetPlant['numberPlate']>;
  fleetDescription: FormControl<IAssetPlant['fleetDescription']>;
  ownerId: FormControl<IAssetPlant['ownerId']>;
  accessibleByCompany: FormControl<IAssetPlant['accessibleByCompany']>;
  driverOrOperator: FormControl<IAssetPlant['driverOrOperator']>;
  plantCategoryId: FormControl<IAssetPlant['plantCategoryId']>;
  plantSubcategoryId: FormControl<IAssetPlant['plantSubcategoryId']>;
  manufacturerId: FormControl<IAssetPlant['manufacturerId']>;
  modelId: FormControl<IAssetPlant['modelId']>;
  yearOfManufacture: FormControl<IAssetPlant['yearOfManufacture']>;
  colour: FormControl<IAssetPlant['colour']>;
  horseOrTrailer: FormControl<IAssetPlant['horseOrTrailer']>;
  smrReaderType: FormControl<IAssetPlant['smrReaderType']>;
  currentSmrIndex: FormControl<IAssetPlant['currentSmrIndex']>;
  engineNumber: FormControl<IAssetPlant['engineNumber']>;
  engineCapacityCc: FormControl<IAssetPlant['engineCapacityCc']>;
  currentSiteId: FormControl<IAssetPlant['currentSiteId']>;
  currentContractId: FormControl<IAssetPlant['currentContractId']>;
  currentOperatorId: FormControl<IAssetPlant['currentOperatorId']>;
  ledgerCode: FormControl<IAssetPlant['ledgerCode']>;
  fuelType: FormControl<IAssetPlant['fuelType']>;
  tankCapacityLitres: FormControl<IAssetPlant['tankCapacityLitres']>;
  consumptionUnit: FormControl<IAssetPlant['consumptionUnit']>;
  plantHoursStatus: FormControl<IAssetPlant['plantHoursStatus']>;
  isPrimeMover: FormControl<IAssetPlant['isPrimeMover']>;
  capacityTons: FormControl<IAssetPlant['capacityTons']>;
  capacityM3Loose: FormControl<IAssetPlant['capacityM3Loose']>;
  capacityM3Tight: FormControl<IAssetPlant['capacityM3Tight']>;
  maximumConsumption: FormControl<IAssetPlant['maximumConsumption']>;
  minimumConsumption: FormControl<IAssetPlant['minimumConsumption']>;
  maximumSmrOnFullTank: FormControl<IAssetPlant['maximumSmrOnFullTank']>;
  trackConsumption: FormControl<IAssetPlant['trackConsumption']>;
  trackSmrReading: FormControl<IAssetPlant['trackSmrReading']>;
  trackService: FormControl<IAssetPlant['trackService']>;
  isDeleted: FormControl<IAssetPlant['isDeleted']>;
  requestWeeklyMileage: FormControl<IAssetPlant['requestWeeklyMileage']>;
  sent: FormControl<IAssetPlant['sent']>;
  chassisNumber: FormControl<IAssetPlant['chassisNumber']>;
  currentLocation: FormControl<IAssetPlant['currentLocation']>;
};

export type AssetPlantFormGroup = FormGroup<AssetPlantFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssetPlantFormService {
  createAssetPlantFormGroup(assetPlant: AssetPlantFormGroupInput = { id: null }): AssetPlantFormGroup {
    const assetPlantRawValue = {
      ...this.getFormDefaults(),
      ...assetPlant,
    };
    return new FormGroup<AssetPlantFormGroupContent>({
      id: new FormControl(
        { value: assetPlantRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      assetPlantId: new FormControl(assetPlantRawValue.assetPlantId),
      fleetNumber: new FormControl(assetPlantRawValue.fleetNumber),
      numberPlate: new FormControl(assetPlantRawValue.numberPlate),
      fleetDescription: new FormControl(assetPlantRawValue.fleetDescription),
      ownerId: new FormControl(assetPlantRawValue.ownerId),
      accessibleByCompany: new FormControl(assetPlantRawValue.accessibleByCompany),
      driverOrOperator: new FormControl(assetPlantRawValue.driverOrOperator),
      plantCategoryId: new FormControl(assetPlantRawValue.plantCategoryId),
      plantSubcategoryId: new FormControl(assetPlantRawValue.plantSubcategoryId),
      manufacturerId: new FormControl(assetPlantRawValue.manufacturerId),
      modelId: new FormControl(assetPlantRawValue.modelId),
      yearOfManufacture: new FormControl(assetPlantRawValue.yearOfManufacture),
      colour: new FormControl(assetPlantRawValue.colour),
      horseOrTrailer: new FormControl(assetPlantRawValue.horseOrTrailer),
      smrReaderType: new FormControl(assetPlantRawValue.smrReaderType),
      currentSmrIndex: new FormControl(assetPlantRawValue.currentSmrIndex),
      engineNumber: new FormControl(assetPlantRawValue.engineNumber),
      engineCapacityCc: new FormControl(assetPlantRawValue.engineCapacityCc),
      currentSiteId: new FormControl(assetPlantRawValue.currentSiteId),
      currentContractId: new FormControl(assetPlantRawValue.currentContractId),
      currentOperatorId: new FormControl(assetPlantRawValue.currentOperatorId),
      ledgerCode: new FormControl(assetPlantRawValue.ledgerCode),
      fuelType: new FormControl(assetPlantRawValue.fuelType),
      tankCapacityLitres: new FormControl(assetPlantRawValue.tankCapacityLitres),
      consumptionUnit: new FormControl(assetPlantRawValue.consumptionUnit),
      plantHoursStatus: new FormControl(assetPlantRawValue.plantHoursStatus),
      isPrimeMover: new FormControl(assetPlantRawValue.isPrimeMover),
      capacityTons: new FormControl(assetPlantRawValue.capacityTons),
      capacityM3Loose: new FormControl(assetPlantRawValue.capacityM3Loose),
      capacityM3Tight: new FormControl(assetPlantRawValue.capacityM3Tight),
      maximumConsumption: new FormControl(assetPlantRawValue.maximumConsumption),
      minimumConsumption: new FormControl(assetPlantRawValue.minimumConsumption),
      maximumSmrOnFullTank: new FormControl(assetPlantRawValue.maximumSmrOnFullTank),
      trackConsumption: new FormControl(assetPlantRawValue.trackConsumption),
      trackSmrReading: new FormControl(assetPlantRawValue.trackSmrReading),
      trackService: new FormControl(assetPlantRawValue.trackService),
      isDeleted: new FormControl(assetPlantRawValue.isDeleted),
      requestWeeklyMileage: new FormControl(assetPlantRawValue.requestWeeklyMileage),
      sent: new FormControl(assetPlantRawValue.sent),
      chassisNumber: new FormControl(assetPlantRawValue.chassisNumber),
      currentLocation: new FormControl(assetPlantRawValue.currentLocation),
    });
  }

  getAssetPlant(form: AssetPlantFormGroup): IAssetPlant | NewAssetPlant {
    return form.getRawValue() as IAssetPlant | NewAssetPlant;
  }

  resetForm(form: AssetPlantFormGroup, assetPlant: AssetPlantFormGroupInput): void {
    const assetPlantRawValue = { ...this.getFormDefaults(), ...assetPlant };
    form.reset(
      {
        ...assetPlantRawValue,
        id: { value: assetPlantRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AssetPlantFormDefaults {
    return {
      id: null,
      isPrimeMover: false,
      trackConsumption: false,
      trackSmrReading: false,
      trackService: false,
      isDeleted: false,
      requestWeeklyMileage: false,
      sent: false,
    };
  }
}
