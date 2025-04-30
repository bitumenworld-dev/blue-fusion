import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IAssetPlantServiceReading, NewAssetPlantServiceReading } from '../asset-plant-service-reading.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IAssetPlantServiceReading for edit and NewAssetPlantServiceReadingFormGroupInput for create.
 */
type AssetPlantServiceReadingFormGroupInput = IAssetPlantServiceReading | PartialWithRequiredKeyOf<NewAssetPlantServiceReading>;

type AssetPlantServiceReadingFormDefaults = Pick<NewAssetPlantServiceReading, 'id'>;

type AssetPlantServiceReadingFormGroupContent = {
  id: FormControl<IAssetPlantServiceReading['id'] | NewAssetPlantServiceReading['id']>;
  assetPlantServiceReadingId: FormControl<IAssetPlantServiceReading['assetPlantServiceReadingId']>;
  assetPlantId: FormControl<IAssetPlantServiceReading['assetPlantId']>;
  nextServiceSmrReading: FormControl<IAssetPlantServiceReading['nextServiceSmrReading']>;
  estimatedUnitsPerDay: FormControl<IAssetPlantServiceReading['estimatedUnitsPerDay']>;
  estimatedNextServiceDate: FormControl<IAssetPlantServiceReading['estimatedNextServiceDate']>;
  latestSmrReadings: FormControl<IAssetPlantServiceReading['latestSmrReadings']>;
  serviceInterval: FormControl<IAssetPlantServiceReading['serviceInterval']>;
  lastServiceDate: FormControl<IAssetPlantServiceReading['lastServiceDate']>;
  latestSmrDate: FormControl<IAssetPlantServiceReading['latestSmrDate']>;
  lastServiceSmr: FormControl<IAssetPlantServiceReading['lastServiceSmr']>;
  serviceUnit: FormControl<IAssetPlantServiceReading['serviceUnit']>;
};

export type AssetPlantServiceReadingFormGroup = FormGroup<AssetPlantServiceReadingFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class AssetPlantServiceReadingFormService {
  createAssetPlantServiceReadingFormGroup(
    assetPlantServiceReading: AssetPlantServiceReadingFormGroupInput = { id: null },
  ): AssetPlantServiceReadingFormGroup {
    const assetPlantServiceReadingRawValue = {
      ...this.getFormDefaults(),
      ...assetPlantServiceReading,
    };
    return new FormGroup<AssetPlantServiceReadingFormGroupContent>({
      id: new FormControl(
        { value: assetPlantServiceReadingRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      assetPlantServiceReadingId: new FormControl(assetPlantServiceReadingRawValue.assetPlantServiceReadingId),
      assetPlantId: new FormControl(assetPlantServiceReadingRawValue.assetPlantId),
      nextServiceSmrReading: new FormControl(assetPlantServiceReadingRawValue.nextServiceSmrReading),
      estimatedUnitsPerDay: new FormControl(assetPlantServiceReadingRawValue.estimatedUnitsPerDay),
      estimatedNextServiceDate: new FormControl(assetPlantServiceReadingRawValue.estimatedNextServiceDate),
      latestSmrReadings: new FormControl(assetPlantServiceReadingRawValue.latestSmrReadings),
      serviceInterval: new FormControl(assetPlantServiceReadingRawValue.serviceInterval),
      lastServiceDate: new FormControl(assetPlantServiceReadingRawValue.lastServiceDate),
      latestSmrDate: new FormControl(assetPlantServiceReadingRawValue.latestSmrDate),
      lastServiceSmr: new FormControl(assetPlantServiceReadingRawValue.lastServiceSmr),
      serviceUnit: new FormControl(assetPlantServiceReadingRawValue.serviceUnit),
    });
  }

  getAssetPlantServiceReading(form: AssetPlantServiceReadingFormGroup): IAssetPlantServiceReading | NewAssetPlantServiceReading {
    return form.getRawValue() as IAssetPlantServiceReading | NewAssetPlantServiceReading;
  }

  resetForm(form: AssetPlantServiceReadingFormGroup, assetPlantServiceReading: AssetPlantServiceReadingFormGroupInput): void {
    const assetPlantServiceReadingRawValue = { ...this.getFormDefaults(), ...assetPlantServiceReading };
    form.reset(
      {
        ...assetPlantServiceReadingRawValue,
        id: { value: assetPlantServiceReadingRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): AssetPlantServiceReadingFormDefaults {
    return {
      id: null,
    };
  }
}
