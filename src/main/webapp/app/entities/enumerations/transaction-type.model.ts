export enum FuelTransactionType {
  FLEET_ISSUANCE = 'FLEET_ISSUANCE',
  DRUM_ISSUANCE = 'DRUM_ISSUANCE',
  WORKSHOP_ISSUANCE = 'WORKSHOP_ISSUANCE',
  THIRD_PARTY_ISSUANCE = 'THIRD_PARTY_ISSUANCE',
  CALIBRATION = 'CALIBRATION',
  TRANSFER = 'TRANSFER',
  GRV = 'GRV',
}

export const FuelTransactionTypes: FuelTransactionType[] = [
  FuelTransactionType.FLEET_ISSUANCE,
  FuelTransactionType.DRUM_ISSUANCE,
  FuelTransactionType.WORKSHOP_ISSUANCE,
  FuelTransactionType.THIRD_PARTY_ISSUANCE,
  FuelTransactionType.CALIBRATION,
  FuelTransactionType.GRV,
  FuelTransactionType.TRANSFER,
];
