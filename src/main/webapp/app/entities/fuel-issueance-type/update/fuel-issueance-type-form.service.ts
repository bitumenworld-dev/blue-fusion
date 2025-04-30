import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFuelIssueanceType, NewFuelIssueanceType } from '../fuel-issueance-type.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelIssueanceType for edit and NewFuelIssueanceTypeFormGroupInput for create.
 */
type FuelIssueanceTypeFormGroupInput = IFuelIssueanceType | PartialWithRequiredKeyOf<NewFuelIssueanceType>;

type FuelIssueanceTypeFormDefaults = Pick<NewFuelIssueanceType, 'id'>;

type FuelIssueanceTypeFormGroupContent = {
  id: FormControl<IFuelIssueanceType['id'] | NewFuelIssueanceType['id']>;
  fuelIssueTypeId: FormControl<IFuelIssueanceType['fuelIssueTypeId']>;
  fuelIssueType: FormControl<IFuelIssueanceType['fuelIssueType']>;
};

export type FuelIssueanceTypeFormGroup = FormGroup<FuelIssueanceTypeFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelIssueanceTypeFormService {
  createFuelIssueanceTypeFormGroup(fuelIssueanceType: FuelIssueanceTypeFormGroupInput = { id: null }): FuelIssueanceTypeFormGroup {
    const fuelIssueanceTypeRawValue = {
      ...this.getFormDefaults(),
      ...fuelIssueanceType,
    };
    return new FormGroup<FuelIssueanceTypeFormGroupContent>({
      id: new FormControl(
        { value: fuelIssueanceTypeRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fuelIssueTypeId: new FormControl(fuelIssueanceTypeRawValue.fuelIssueTypeId),
      fuelIssueType: new FormControl(fuelIssueanceTypeRawValue.fuelIssueType),
    });
  }

  getFuelIssueanceType(form: FuelIssueanceTypeFormGroup): IFuelIssueanceType | NewFuelIssueanceType {
    return form.getRawValue() as IFuelIssueanceType | NewFuelIssueanceType;
  }

  resetForm(form: FuelIssueanceTypeFormGroup, fuelIssueanceType: FuelIssueanceTypeFormGroupInput): void {
    const fuelIssueanceTypeRawValue = { ...this.getFormDefaults(), ...fuelIssueanceType };
    form.reset(
      {
        ...fuelIssueanceTypeRawValue,
        id: { value: fuelIssueanceTypeRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuelIssueanceTypeFormDefaults {
    return {
      id: null,
    };
  }
}
