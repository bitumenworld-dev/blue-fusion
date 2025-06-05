export interface StorageUnitTransaction {
  transactionDate?: Date | null;
  fuelTransactionType?: string | null;
  fuelType?: string | null;
  fleetNumber?: string | null;
  meterReadingStart?: number | null;
  meterReadingEnd?: number | null;
  litres?: number | null;
  contractDivision?: string | null;
  isFillUp?: boolean | null;
  fuelPump?: string | null;
  note?: string | null;
}

export interface StorageUnitPump {
  fuelPump?: string | null;
  meterReading?: number | null;
}

export interface StorageUnitTransactions {
  currentStorageBalance?: number | null;
  pumpReadings?: StorageUnitPump[] | null;
  transactions?: StorageUnitTransaction[] | null;
}
