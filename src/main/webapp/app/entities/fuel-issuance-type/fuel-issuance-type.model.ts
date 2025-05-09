export interface IFuelIssuanceType {
  id: number;
  fuelIssueTypeId?: number | null;
  fuelIssueType?: string | null;
}

export type NewFuelIssuanceType = Omit<IFuelIssuanceType, 'id'> & { id: null };
