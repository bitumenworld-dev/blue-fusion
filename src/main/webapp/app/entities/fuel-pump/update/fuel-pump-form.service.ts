import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { FuelPump, NewFuelPump } from '../fuel-pump.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelPump for edit and NewFuelPumpFormGroupInput for create.
 */
type FuelPumpFormGroupInput = FuelPump | PartialWithRequiredKeyOf<NewFuelPump>;

type FuelPumpFormDefaults = Pick<NewFuelPump, 'id'>;

type FuelPumpFormGroupContent = {
  id: FormControl<FuelPump['id'] | NewFuelPump['id']>;
  fuelPumpCode: FormControl<FuelPump['fuelPumpCode']>;
  storageId: FormControl<FuelPump['storageId']>;
  storage: FormControl<FuelPump['storage']>;
  description: FormControl<FuelPump['description']>;
  validFrom: FormControl<FuelPump['validFrom']>;
  isActive: FormControl<FuelPump['isActive']>;
};

export type FuelPumpFormGroup = FormGroup<FuelPumpFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelPumpFormService {
  createFuelPumpFormGroup(fuelPump: FuelPumpFormGroupInput = { id: null }): FuelPumpFormGroup {
    const fuelPumpRawValue = {
      ...this.getFormDefaults(),
      ...fuelPump,
    };
    return new FormGroup<FuelPumpFormGroupContent>({
      id: new FormControl(
        { value: fuelPumpRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fuelPumpCode: new FormControl(fuelPumpRawValue.fuelPumpCode),
      storageId: new FormControl(fuelPumpRawValue.storageId),
      storage: new FormControl(fuelPumpRawValue.storage),
      description: new FormControl(fuelPumpRawValue.description),
      validFrom: new FormControl(fuelPumpRawValue.validFrom),
      isActive: new FormControl(fuelPumpRawValue.isActive),
    });
  }

  getFuelPump(form: FuelPumpFormGroup): FuelPump | NewFuelPump {
    return form.getRawValue() as FuelPump | NewFuelPump;
  }

  resetForm(form: FuelPumpFormGroup, fuelPump: FuelPumpFormGroupInput): void {
    const fuelPumpRawValue = { ...this.getFormDefaults(), ...fuelPump };
    form.reset(
      {
        ...fuelPumpRawValue,
        id: { value: fuelPumpRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuelPumpFormDefaults {
    return {
      id: null,
    };
  }
}
