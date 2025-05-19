import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { FuelTransaction, NewFuelTransaction } from '../fuel-transaction.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts FuelTransaction for edit and NewFuelTransactionFormGroupInput for create.
 */
type FuelTransactionHeaderFormGroupInput = FuelTransaction | PartialWithRequiredKeyOf<NewFuelTransaction>;

type FuelTransactionHeaderFormDefaults = Pick<NewFuelTransaction, 'id'>;

type FuelTransactionHeaderFormGroupContent = {
  id: FormControl<FuelTransaction['id'] | NewFuelTransaction['id']>;
  fuelTransactionHeaderId: FormControl<FuelTransaction['fuelTransactionId']>;
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
};

export type FuelTransactionHeaderFormGroup = FormGroup<FuelTransactionHeaderFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionFormService {
  createFuelTransactionHeaderFormGroup(
    fuelTransactionHeader: FuelTransactionHeaderFormGroupInput = { id: null },
  ): FuelTransactionHeaderFormGroup {
    const fuelTransactionHeaderRawValue = {
      ...this.getFormDefaults(),
      ...fuelTransactionHeader,
    };
    return new FormGroup<FuelTransactionHeaderFormGroupContent>({
      id: new FormControl(
        { value: fuelTransactionHeaderRawValue.id, disabled: true },
        {
          nonNullable: true,
          validators: [Validators.required],
        },
      ),
      fuelTransactionHeaderId: new FormControl(fuelTransactionHeaderRawValue.fuelTransactionId),
      companyId: new FormControl(fuelTransactionHeaderRawValue.companyId),
      supplierId: new FormControl(fuelTransactionHeaderRawValue.supplierId),
      transactionTypeId: new FormControl(fuelTransactionHeaderRawValue.transactionTypeId),
      fuelType: new FormControl(fuelTransactionHeaderRawValue.fuelType),
      orderNumber: new FormControl(fuelTransactionHeaderRawValue.orderNumber),
      deliveryNote: new FormControl(fuelTransactionHeaderRawValue.deliveryNote),
      grvNumber: new FormControl(fuelTransactionHeaderRawValue.grvNumber),
      invoiceNumber: new FormControl(fuelTransactionHeaderRawValue.invoiceNumber),
      pricePerLitre: new FormControl(fuelTransactionHeaderRawValue.pricePerLitre),
      note: new FormControl(fuelTransactionHeaderRawValue.note),
      registrationNumber: new FormControl(fuelTransactionHeaderRawValue.registrationNumber),
      attendeeId: new FormControl(fuelTransactionHeaderRawValue.attendeeId),
      operatorId: new FormControl(fuelTransactionHeaderRawValue.operatorId),
    });
  }

  getFuelTransactionHeader(form: FuelTransactionHeaderFormGroup): FuelTransaction | NewFuelTransaction {
    return form.getRawValue() as FuelTransaction | NewFuelTransaction;
  }

  resetForm(form: FuelTransactionHeaderFormGroup, fuelTransactionHeader: FuelTransactionHeaderFormGroupInput): void {
    const fuelTransactionHeaderRawValue = { ...this.getFormDefaults(), ...fuelTransactionHeader };
    form.reset({
      ...fuelTransactionHeaderRawValue,
      id: { value: fuelTransactionHeaderRawValue.id, disabled: true },
    } as any);
  }

  private getFormDefaults(): FuelTransactionHeaderFormDefaults {
    return {
      id: null,
    };
  }
}
