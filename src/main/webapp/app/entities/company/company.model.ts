export interface ICompany {
  id: number;
  name?: string | null;
  description?: string | null;
  address?: string | null;
  isActive?: boolean | null;
  usesFuelSystem?: boolean | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
