export interface ICompany {
  id: number;
  companyId?: number | null;
  name?: string | null;
  description?: string | null;
}

export type NewCompany = Omit<ICompany, 'id'> & { id: null };
