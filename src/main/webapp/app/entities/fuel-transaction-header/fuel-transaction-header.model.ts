import { FuelType } from 'app/entities/enumerations/fuel-type.model';

export interface IFuelTransactionHeader {
  id: number;
  fuelTransactionHeaderId?: number | null;
  companyId?: number | null;
  supplierId?: number | null;
  transactionTypeId?: number | null;
  fuelType?: keyof typeof FuelType | null;
  orderNumber?: string | null;
  deliveryNote?: string | null;
  grvNumber?: string | null;
  invoiceNumber?: string | null;
  pricePerLitre?: number | null;
  note?: string | null;
  registrationNumber?: string | null;
  attendeeId?: number | null;
  operatorId?: number | null;
}

export type NewFuelTransactionHeader = Omit<IFuelTransactionHeader, 'id'> & { id: null };
