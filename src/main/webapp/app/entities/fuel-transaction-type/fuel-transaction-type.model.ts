export interface IFuelTransactionType {
  id: number;
  fuelTransactionTypeId?: number | null;
  fuelTransactionType?: string | null;
  isActive?: boolean | null;
}

export type NewFuelTransactionType = Omit<IFuelTransactionType, 'id'> & { id: null };
