import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFuelTransactionType, NewFuelTransactionType } from '../fuel-transaction-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelTransactionType for edit and NewFuelTransactionTypeFormGroupInput for create.
 */
type FuelTransactionTypeFormGroupInput = IFuelTransactionType | PartialWithRequiredKeyOf<NewFuelTransactionType>;

type FuelTransactionTypeFormDefaults = Pick<NewFuelTransactionType, 'id' | 'isActive'>;

type FuelTransactionTypeFormGroupContent = {
  id: FormControl<IFuelTransactionType['id'] | NewFuelTransactionType['id']>;
  fuelTransactionTypeId: FormControl<IFuelTransactionType['fuelTransactionTypeId']>;
  fuelTransactionType: FormControl<IFuelTransactionType['fuelTransactionType']>;
  isActive: FormControl<IFuelTransactionType['isActive']>;
};

export type FuelTransactionTypeFormGroup = FormGroup<FuelTransactionTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionTypeFormService {
  createFuelTransactionTypeFormGroup(fuelTransactionType: FuelTransactionTypeFormGroupInput = { id: null }): FuelTransactionTypeFormGroup {
    const fuelTransactionTypeRawValue = {
      ...this.getFormDefaults(),
      ...fuelTransactionType,
    };
    return new FormGroup<FuelTransactionTypeFormGroupContent>({
      id: new FormControl(
        { value: fuelTransactionTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fuelTransactionTypeId: new FormControl(fuelTransactionTypeRawValue.fuelTransactionTypeId),
      fuelTransactionType: new FormControl(fuelTransactionTypeRawValue.fuelTransactionType),
      isActive: new FormControl(fuelTransactionTypeRawValue.isActive),
    });
  }

  getFuelTransactionType(form: FuelTransactionTypeFormGroup): IFuelTransactionType | NewFuelTransactionType {
    return form.getRawValue() as IFuelTransactionType | NewFuelTransactionType;
  }

  resetForm(form: FuelTransactionTypeFormGroup, fuelTransactionType: FuelTransactionTypeFormGroupInput): void {
    const fuelTransactionTypeRawValue = { ...this.getFormDefaults(), ...fuelTransactionType };
    form.reset(
      {
        ...fuelTransactionTypeRawValue,
        id: { value: fuelTransactionTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuelTransactionTypeFormDefaults {
    return {
      id: null,
      isActive: false,
    };
  }
}
