export interface IManufacturerModel {
  id: number;
  modelId?: number | null;
  modelName?: string | null;
}

export type NewManufacturerModel = Omit<IManufacturerModel, 'id'> & { id: null };
