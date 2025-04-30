export interface IPlantSubcategory {
  id: number;
  plantSubcategoryId?: number | null;
  plantSubcategoryCode?: string | null;
  plantSubcategoryName?: string | null;
  plantSubcategoryDescription?: string | null;
  isAudited?: boolean | null;
}

export type NewPlantSubcategory = Omit<IPlantSubcategory, 'id'> & { id: null };
