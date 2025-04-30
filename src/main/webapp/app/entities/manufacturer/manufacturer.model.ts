export interface IManufacturer {
  id: number;
  manufacturerId?: number | null;
  manufacturerName?: string | null;
}

export type NewManufacturer = Omit<IManufacturer, 'id'> & { id: null };
