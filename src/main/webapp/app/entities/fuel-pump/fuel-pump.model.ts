import dayjs from 'dayjs/esm';

export interface FuelPump {
  id: number;
  fuelPumpCode?: string | null;
  storageId?: number | null;
  storage?: string | null;
  description?: string | null;
  validFrom?: dayjs.Dayjs | null;
  isActive?: boolean | null;
}

export type NewFuelPump = Omit<FuelPump, 'id'> & { id: null };
