export interface IFuelTransactionLine {
  id: number;
  fuelTransactionLineId?: number | null;
  fuelTransactionHeaderId?: number | null;
  assetPlantId?: number | null;
  contractDivisionId?: number | null;
  issuanceTypeId?: number | null;
  pumpId?: number | null;
  storageUnitId?: number | null;
  litres?: number | null;
  meterReadingStart?: number | null;
  meterReadingEnd?: number | null;
  multiplier?: number | null;
}

export type NewFuelTransactionLine = Omit<IFuelTransactionLine, 'id'> & { id: null };
