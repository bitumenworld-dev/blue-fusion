export interface IPlantCategory {
  id: number;
  plantCategoryId?: number | null;
  plantCategoryCode?: string | null;
  plantCategoryName?: string | null;
  plantCategoryDescription?: string | null;
  isAudited?: boolean | null;
}

export type NewPlantCategory = Omit<IPlantCategory, 'id'> & { id: null };
