export interface ICompany {
  id: number;
  name?: string | null;
  accessKey?: string | null;
  address?: string | null;
  isActive?: boolean | null;
  isIAC?: boolean | null;
  usesFuelSystem?: boolean | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
