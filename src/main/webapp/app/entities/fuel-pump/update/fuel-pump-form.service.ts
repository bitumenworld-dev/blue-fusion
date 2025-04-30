import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFuelPump, NewFuelPump } from '../fuel-pump.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelPump for edit and NewFuelPumpFormGroupInput for create.
 */
type FuelPumpFormGroupInput = IFuelPump | PartialWithRequiredKeyOf<NewFuelPump>;

type FuelPumpFormDefaults = Pick<NewFuelPump, 'id'>;

type FuelPumpFormGroupContent = {
  id: FormControl<IFuelPump['id'] | NewFuelPump['id']>;
  fuelPumpId: FormControl<IFuelPump['fuelPumpId']>;
  fuelPumpNumber: FormControl<IFuelPump['fuelPumpNumber']>;
  currentStorageUnitId: FormControl<IFuelPump['currentStorageUnitId']>;
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
      fuelPumpId: new FormControl(fuelPumpRawValue.fuelPumpId),
      fuelPumpNumber: new FormControl(fuelPumpRawValue.fuelPumpNumber),
      currentStorageUnitId: new FormControl(fuelPumpRawValue.currentStorageUnitId),
    });
  }

  getFuelPump(form: FuelPumpFormGroup): IFuelPump | NewFuelPump {
    return form.getRawValue() as IFuelPump | NewFuelPump;
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
