import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { FuelTransaction, NewFuelTransaction } from '../fuel-transaction.model';
import { IFuelTransactionLine } from '../../fuel-transaction-line/fuel-transaction-line.model';

type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelTransactionHeader for edit and NewFuelTransactionHeaderFormGroupInput for create.
 */
type FuelTransactionFormGroupInput = FuelTransaction | PartialWithRequiredKeyOf<NewFuelTransaction>;

type FuelTransactionFormDefaults = Pick<NewFuelTransaction, 'id'>;

type FuelTransactionFormGroupContent = {
  id: FormControl<FuelTransaction['id'] | NewFuelTransaction['id']>;
  fuelTransactionId: FormControl<FuelTransaction['fuelTransactionId']>;
  companyId: FormControl<FuelTransaction['companyId']>;
  supplierId: FormControl<FuelTransaction['supplierId']>;
  transactionTypeId: FormControl<FuelTransaction['transactionTypeId']>;
  fuelType: FormControl<FuelTransaction['fuelType']>;
  orderNumber: FormControl<FuelTransaction['orderNumber']>;
  deliveryNote: FormControl<FuelTransaction['deliveryNote']>;
  grvNumber: FormControl<FuelTransaction['grvNumber']>;
  invoiceNumber: FormControl<FuelTransaction['invoiceNumber']>;
  pricePerLitre: FormControl<FuelTransaction['pricePerLitre']>;
  note: FormControl<FuelTransaction['note']>;
  registrationNumber: FormControl<FuelTransaction['registrationNumber']>;
  attendeeId: FormControl<FuelTransaction['attendeeId']>;
  operatorId: FormControl<FuelTransaction['operatorId']>;
  fuelTransactionLineId: FormControl<FuelTransaction['fuelTransactionLineId']>;
  assetPlantId: FormControl<FuelTransaction['assetPlantId']>;
  contractDivisionId: FormControl<FuelTransaction['contractDivisionId']>;
  issuanceTypeId: FormControl<FuelTransaction['issuanceTypeId']>;
  pumpId: FormControl<FuelTransaction['pumpId']>;
  storageUnitId: FormControl<FuelTransaction['storageUnitId']>;
  litres: FormControl<FuelTransaction['litres']>;
  meterReadingStart: FormControl<FuelTransaction['meterReadingStart']>;
  meterReadingEnd: FormControl<FuelTransaction['meterReadingEnd']>;
  multiplier: FormControl<FuelTransaction['multiplier']>;
};

export type FuelTransactionFormGroup = FormGroup<FuelTransactionFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionFormService {
  createFuelTransactionFormGroup(fuelTransaction: FuelTransactionFormGroupInput = { id: null }): FuelTransactionFormGroup {
    const fuelTransactionRawValue = {
      ...this.getFormDefaults(),
      ...fuelTransaction,
    };
    return new FormGroup<FuelTransactionFormGroupContent>({
      id: new FormControl(
        { value: fuelTransactionRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fuelTransactionId: new FormControl(fuelTransactionRawValue.fuelTransactionId),
      companyId: new FormControl(fuelTransactionRawValue.companyId),
      supplierId: new FormControl(fuelTransactionRawValue.supplierId),
      transactionTypeId: new FormControl(fuelTransactionRawValue.transactionTypeId),
      fuelType: new FormControl(fuelTransactionRawValue.fuelType),
      orderNumber: new FormControl(fuelTransactionRawValue.orderNumber),
      deliveryNote: new FormControl(fuelTransactionRawValue.deliveryNote),
      grvNumber: new FormControl(fuelTransactionRawValue.grvNumber),
      invoiceNumber: new FormControl(fuelTransactionRawValue.invoiceNumber),
      pricePerLitre: new FormControl(fuelTransactionRawValue.pricePerLitre),
      note: new FormControl(fuelTransactionRawValue.note),
      registrationNumber: new FormControl(fuelTransactionRawValue.registrationNumber),
      attendeeId: new FormControl(fuelTransactionRawValue.attendeeId),
      operatorId: new FormControl(fuelTransactionRawValue.operatorId),
      fuelTransactionLineId: new FormControl(fuelTransactionRawValue.fuelTransactionLineId),
      assetPlantId: new FormControl(fuelTransactionRawValue.assetPlantId),
      contractDivisionId: new FormControl(fuelTransactionRawValue.contractDivisionId),
      issuanceTypeId: new FormControl(fuelTransactionRawValue.issuanceTypeId),
      pumpId: new FormControl(fuelTransactionRawValue.pumpId),
      storageUnitId: new FormControl(fuelTransactionRawValue.storageUnitId),
      litres: new FormControl(fuelTransactionRawValue.litres),
      meterReadingStart: new FormControl(fuelTransactionRawValue.meterReadingStart),
      meterReadingEnd: new FormControl(fuelTransactionRawValue.meterReadingEnd),
      multiplier: new FormControl(fuelTransactionRawValue.multiplier),
    });
  }

  getFuelTransaction(form: FuelTransactionFormGroup): FuelTransaction | NewFuelTransaction {
    return form.getRawValue() as FuelTransaction | NewFuelTransaction;
  }

  resetForm(form: FuelTransactionFormGroup, fuelTransaction: FuelTransactionFormGroupInput): void {
    const fuelTransactionRawValue = { ...this.getFormDefaults(), ...fuelTransaction };
    form.reset({
      ...fuelTransactionRawValue,
      id: { value: fuelTransactionRawValue.id, disabled: true },
    } as any);
  }

  private getFormDefaults(): FuelTransactionFormDefaults {
    return {
      id: null,
    };
  }
}
