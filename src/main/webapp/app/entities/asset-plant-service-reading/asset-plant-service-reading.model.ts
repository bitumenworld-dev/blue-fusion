import dayjs from 'dayjs/esm';
import { ServiceUnit } from 'app/entities/enumerations/service-unit.model';

export interface IAssetPlantServiceReading {
  id: number;
  assetPlantServiceReadingId?: number | null;
  assetPlantId?: number | null;
  nextServiceSmrReading?: number | null;
  estimatedUnitsPerDay?: number | null;
  estimatedNextServiceDate?: dayjs.Dayjs | null;
  latestSmrReadings?: number | null;
  serviceInterval?: number | null;
  lastServiceDate?: dayjs.Dayjs | null;
  latestSmrDate?: dayjs.Dayjs | null;
  lastServiceSmr?: number | null;
  serviceUnit?: keyof typeof ServiceUnit | null;
}

export type NewAssetPlantServiceReading = Omit<IAssetPlantServiceReading, 'id'> & { id: null };
