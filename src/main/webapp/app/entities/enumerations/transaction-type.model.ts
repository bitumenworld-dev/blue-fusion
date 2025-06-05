export enum FuelTransactionType {
  ISSUANCE = 'ISSUANCE',
  CALIBRATION = 'CALIBRATION',
  TRANSFER = 'TRANSFER',
  GRV = 'GRV',
}

export const FuelTransactionTypes: FuelTransactionType[] = [
  FuelTransactionType.ISSUANCE,
  FuelTransactionType.CALIBRATION,
  FuelTransactionType.GRV,
  FuelTransactionType.TRANSFER,
];

export enum IssuanceTransactionType {
  FLEET = 'FLEET',
  DRUM = 'DRUM',
  WORKSHOP = 'WORKSHOP',
  THIRD_PARTY = 'THIRD_PARTY',
}

export const IssuanceTransactionTypes: IssuanceTransactionType[] = [
  IssuanceTransactionType.DRUM,
  IssuanceTransactionType.FLEET,
  IssuanceTransactionType.WORKSHOP,
  IssuanceTransactionType.THIRD_PARTY,
];
