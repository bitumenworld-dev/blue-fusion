import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFuelTransactionLine, NewFuelTransactionLine } from '../fuel-transaction-line.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelTransactionLine for edit and NewFuelTransactionLineFormGroupInput for create.
 */
type FuelTransactionLineFormGroupInput = IFuelTransactionLine | PartialWithRequiredKeyOf<NewFuelTransactionLine>;

type FuelTransactionLineFormDefaults = Pick<NewFuelTransactionLine, 'id'>;

type FuelTransactionLineFormGroupContent = {
  id: FormControl<IFuelTransactionLine['id'] | NewFuelTransactionLine['id']>;
  fuelTransactionLineId: FormControl<IFuelTransactionLine['fuelTransactionLineId']>;
  fuelTransactionHeaderId: FormControl<IFuelTransactionLine['fuelTransactionHeaderId']>;
  assetPlantId: FormControl<IFuelTransactionLine['assetPlantId']>;
  contractDivisionId: FormControl<IFuelTransactionLine['contractDivisionId']>;
  issuanceTypeId: FormControl<IFuelTransactionLine['issuanceTypeId']>;
  pumpId: FormControl<IFuelTransactionLine['pumpId']>;
  storageUnitId: FormControl<IFuelTransactionLine['storageUnitId']>;
  litres: FormControl<IFuelTransactionLine['litres']>;
  meterReadingStart: FormControl<IFuelTransactionLine['meterReadingStart']>;
  meterReadingEnd: FormControl<IFuelTransactionLine['meterReadingEnd']>;
  multiplier: FormControl<IFuelTransactionLine['multiplier']>;
};

export type FuelTransactionLineFormGroup = FormGroup<FuelTransactionLineFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionLineFormService {
  createFuelTransactionLineFormGroup(fuelTransactionLine: FuelTransactionLineFormGroupInput = { id: null }): FuelTransactionLineFormGroup {
    const fuelTransactionLineRawValue = {
      ...this.getFormDefaults(),
      ...fuelTransactionLine,
    };
    return new FormGroup<FuelTransactionLineFormGroupContent>({
      id: new FormControl(
        { value: fuelTransactionLineRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fuelTransactionLineId: new FormControl(fuelTransactionLineRawValue.fuelTransactionLineId),
      fuelTransactionHeaderId: new FormControl(fuelTransactionLineRawValue.fuelTransactionHeaderId),
      assetPlantId: new FormControl(fuelTransactionLineRawValue.assetPlantId),
      contractDivisionId: new FormControl(fuelTransactionLineRawValue.contractDivisionId),
      issuanceTypeId: new FormControl(fuelTransactionLineRawValue.issuanceTypeId),
      pumpId: new FormControl(fuelTransactionLineRawValue.pumpId),
      storageUnitId: new FormControl(fuelTransactionLineRawValue.storageUnitId),
      litres: new FormControl(fuelTransactionLineRawValue.litres),
      meterReadingStart: new FormControl(fuelTransactionLineRawValue.meterReadingStart),
      meterReadingEnd: new FormControl(fuelTransactionLineRawValue.meterReadingEnd),
      multiplier: new FormControl(fuelTransactionLineRawValue.multiplier),
    });
  }

  getFuelTransactionLine(form: FuelTransactionLineFormGroup): IFuelTransactionLine | NewFuelTransactionLine {
    return form.getRawValue() as IFuelTransactionLine | NewFuelTransactionLine;
  }

  resetForm(form: FuelTransactionLineFormGroup, fuelTransactionLine: FuelTransactionLineFormGroupInput): void {
    const fuelTransactionLineRawValue = { ...this.getFormDefaults(), ...fuelTransactionLine };
    form.reset(
      {
        ...fuelTransactionLineRawValue,
        id: { value: fuelTransactionLineRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuelTransactionLineFormDefaults {
    return {
      id: null,
    };
  }
}
