export interface IWorkshop {
  id: number;
  workshopId?: number | null;
  siteId?: number | null;
  workshopName?: string | null;
}

export type NewWorkshop = Omit<IWorkshop, 'id'> & { id: null };
