export interface IFuelIssueanceType {
  id: number;
  fuelIssueTypeId?: number | null;
  fuelIssueType?: string | null;
}

export type NewFuelIssueanceType = Omit<IFuelIssueanceType, 'id'> & { id: null };
