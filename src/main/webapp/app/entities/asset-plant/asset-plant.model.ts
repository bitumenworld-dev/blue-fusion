import { DriverOrOperator } from 'app/entities/enumerations/driver-or-operator.model';
import { HorseOrTrailer } from 'app/entities/enumerations/horse-or-trailer.model';
import { SMRReaderType } from 'app/entities/enumerations/smr-reader-type.model';
import { FuelType } from 'app/entities/enumerations/fuel-type.model';
import { ConsumptionUnit } from 'app/entities/enumerations/consumption-unit.model';
import { PlantHoursStatus } from 'app/entities/enumerations/plant-hours-status.model';

export interface AssetPlant {
  assetPlantId: number;
  fleetNumber?: string | null;
  numberPlate?: string | null;
  fleetDescription?: string | null;
  owner?: string | null;
  chassisNumber?: string | null;
  yearOfManufacture?: number | null;
  colour?: string | null;
  currentSmrIndex?: number | null;
  engineNumber?: string | null;
  engineCapacityCc?: string | null;
  ledgerCode?: string | null;
  tankCapacityLitres?: number | null;
  capacityTons?: number | null;
  capacityM3Loose?: number | null;
  capacityM3Tight?: number | null;
  maximumConsumption?: number | null;
  minimumConsumption?: number | null;
  maximumSmrOnFullTank?: number | null;
  trackConsumption?: boolean | null;
  trackSmrReading?: boolean | null;
  requestWeeklyMileage?: boolean | null;
  sent?: boolean | null;
  plantCategory?: string | null;
  plantSubcategory?: string | null;
  make?: string | null;
  model?: string | null;
  horseOrTrailer?: string | null;
  smrReaderType?: string | null;
  fuelType?: string | null;
  isPrimeMover?: boolean | null;
  currentSite?: string | null;
  currentContract?: string | null;
  currentOperator?: string | null;
  trackService?: boolean | null;
  currentLocation?: string | null;
}

export type NewAssetPlant = Omit<AssetPlant, 'assetPlantId'> & { assetPlantId: null };
