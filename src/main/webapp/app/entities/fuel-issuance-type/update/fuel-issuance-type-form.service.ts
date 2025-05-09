import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFuelIssuanceType, NewFuelIssuanceType } from '../fuel-issuance-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelIssuanceType for edit and NewFuelIssuanceTypeFormGroupInput for create.
 */
type FuelIssuanceTypeFormGroupInput = IFuelIssuanceType | PartialWithRequiredKeyOf<NewFuelIssuanceType>;

type FuelIssuanceTypeFormDefaults = Pick<NewFuelIssuanceType, 'id'>;

type FuelIssuanceTypeFormGroupContent = {
  id: FormControl<IFuelIssuanceType['id'] | NewFuelIssuanceType['id']>;
  fuelIssueTypeId: FormControl<IFuelIssuanceType['fuelIssueTypeId']>;
  fuelIssueType: FormControl<IFuelIssuanceType['fuelIssueType']>;
};

export type FuelIssuanceTypeFormGroup = FormGroup<FuelIssuanceTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelIssuanceTypeFormService {
  createFuelIssuanceTypeFormGroup(fuelIssuanceType: FuelIssuanceTypeFormGroupInput = { id: null }): FuelIssuanceTypeFormGroup {
    const fuelIssuanceTypeRawValue = {
      ...this.getFormDefaults(),
      ...fuelIssuanceType,
    };
    return new FormGroup<FuelIssuanceTypeFormGroupContent>({
      id: new FormControl(
        { value: fuelIssuanceTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fuelIssueTypeId: new FormControl(fuelIssuanceTypeRawValue.fuelIssueTypeId),
      fuelIssueType: new FormControl(fuelIssuanceTypeRawValue.fuelIssueType),
    });
  }

  getFuelIssuanceType(form: FuelIssuanceTypeFormGroup): IFuelIssuanceType | NewFuelIssuanceType {
    return form.getRawValue() as IFuelIssuanceType | NewFuelIssuanceType;
  }

  resetForm(form: FuelIssuanceTypeFormGroup, fuelIssuanceType: FuelIssuanceTypeFormGroupInput): void {
    const fuelIssuanceTypeRawValue = { ...this.getFormDefaults(), ...fuelIssuanceType };
    form.reset(
      {
        ...fuelIssuanceTypeRawValue,
        id: { value: fuelIssuanceTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuelIssuanceTypeFormDefaults {
    return {
      id: null,
    };
  }
}
