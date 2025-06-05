import { FuelType } from 'app/entities/enumerations/fuel-type.model';
import { FuelTransactionType, IssuanceTransactionType } from '../enumerations/transaction-type.model';

export interface FuelTransaction {
  fuelTransactionId: number;
  companyId?: number | null;
  supplierId?: number | null;
  transactionType?: keyof typeof FuelTransactionType | null;
  issuanceType?: keyof typeof IssuanceTransactionType | null;
  fuelType?: keyof typeof FuelType | null;
  orderNumber?: string | null;
  deliveryNote?: string | null;
  transactionDate?: Date | null;
  grvNumber?: string | null;
  invoiceNumber?: string | null;
  pricePerLitre?: number | null;
  note?: string | null;
  registrationNumber?: string | null;
  smr?: string | null;
  attendeeId?: number | null;
  operatorId?: number | null;
  fuelTransactionLineId?: number | null;
  assetPlantId?: number | null;
  fleetNumber?: string | null;
  contractDivisionId?: number | null;
  issuanceTypeId?: number | null;
  pumpId?: number | null;
  storageId?: number | null;
  litres?: string | null;
  meterReadingStart?: string | null;
  meterReadingEnd?: string | null;
  isFillUp?: boolean | null;
}

export type NewFuelTransaction = Omit<FuelTransaction, 'fuelTransactionId'> & { fuelTransactionId: null };
