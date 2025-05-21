import { FuelType } from 'app/entities/enumerations/fuel-type.model';

export interface FuelTransaction {
  id: number;
  fuelTransactionId?: number | null;
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
  fuelTransactionLineId?: number | null;
  assetPlantId?: number | null;
  contractDivisionId?: number | null;
  issuanceTypeId?: number | null;
  pumpId?: number | null;
  storageId?: number | null;
  litres?: number | null;
  meterReadingStart?: number | null;
  meterReadingEnd?: number | null;
  multiplier?: number | null;
}

export type NewFuelTransaction = Omit<FuelTransaction, 'id'> & { id: null };
