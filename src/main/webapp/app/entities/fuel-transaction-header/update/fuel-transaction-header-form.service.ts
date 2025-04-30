import { Injectable } from '@angular/core';
import { FormControl, FormGroup, Validators } from '@angular/forms';

import { IFuelTransactionHeader, NewFuelTransactionHeader } from '../fuel-transaction-header.model';

/**
 * A partial Type with required key is used as form input.
 */
type PartialWithRequiredKeyOf<T extends { id: unknown }> = Partial<Omit<T, 'id'>> & { id: T['id'] };

/**
 * Type for createFormGroup and resetForm argument.
 * It accepts IFuelTransactionHeader for edit and NewFuelTransactionHeaderFormGroupInput for create.
 */
type FuelTransactionHeaderFormGroupInput = IFuelTransactionHeader | PartialWithRequiredKeyOf<NewFuelTransactionHeader>;

type FuelTransactionHeaderFormDefaults = Pick<NewFuelTransactionHeader, 'id'>;

type FuelTransactionHeaderFormGroupContent = {
  id: FormControl<IFuelTransactionHeader['id'] | NewFuelTransactionHeader['id']>;
  fuelTransactionHeaderId: FormControl<IFuelTransactionHeader['fuelTransactionHeaderId']>;
  companyId: FormControl<IFuelTransactionHeader['companyId']>;
  supplierId: FormControl<IFuelTransactionHeader['supplierId']>;
  transactionTypeId: FormControl<IFuelTransactionHeader['transactionTypeId']>;
  fuelType: FormControl<IFuelTransactionHeader['fuelType']>;
  orderNumber: FormControl<IFuelTransactionHeader['orderNumber']>;
  deliveryNote: FormControl<IFuelTransactionHeader['deliveryNote']>;
  grvNumber: FormControl<IFuelTransactionHeader['grvNumber']>;
  invoiceNumber: FormControl<IFuelTransactionHeader['invoiceNumber']>;
  pricePerLitre: FormControl<IFuelTransactionHeader['pricePerLitre']>;
  note: FormControl<IFuelTransactionHeader['note']>;
  registrationNumber: FormControl<IFuelTransactionHeader['registrationNumber']>;
  attendeeId: FormControl<IFuelTransactionHeader['attendeeId']>;
  operatorId: FormControl<IFuelTransactionHeader['operatorId']>;
};

export type FuelTransactionHeaderFormGroup = FormGroup<FuelTransactionHeaderFormGroupContent>;

@Injectable({ providedIn: 'root' })
export class FuelTransactionHeaderFormService {
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
      fuelTransactionHeaderId: new FormControl(fuelTransactionHeaderRawValue.fuelTransactionHeaderId),
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

  getFuelTransactionHeader(form: FuelTransactionHeaderFormGroup): IFuelTransactionHeader | NewFuelTransactionHeader {
    return form.getRawValue() as IFuelTransactionHeader | NewFuelTransactionHeader;
  }

  resetForm(form: FuelTransactionHeaderFormGroup, fuelTransactionHeader: FuelTransactionHeaderFormGroupInput): void {
    const fuelTransactionHeaderRawValue = { ...this.getFormDefaults(), ...fuelTransactionHeader };
    form.reset(
      {
        ...fuelTransactionHeaderRawValue,
        id: { value: fuelTransactionHeaderRawValue.id, disabled: true },
      } as any /* cast to workaround https://github.com/angular/angular/issues/46458 */,
    );
  }

  private getFormDefaults(): FuelTransactionHeaderFormDefaults {
    return {
      id: null,
    };
  }
}
