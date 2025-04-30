export interface IFuelPump {
  id: number;
  fuelPumpId?: number | null;
  fuelPumpNumber?: string | null;
  currentStorageUnitId?: number | null;
}

export type NewFuelPump = Omit<IFuelPump, 'id'> & { id: null };
