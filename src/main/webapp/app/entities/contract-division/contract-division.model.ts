import { DivisionType } from 'app/entities/enumerations/division-type.model';

export interface ContractDivision {
  contractDivisionId: number;
  contractDivisionNumber?: string | null;
  contractDivisionName?: string | null;
  company?: string | null;
  buildSmartReference?: string | null;
  shiftStart?: string | null;
  shiftEnd?: string | null;
  contractDivisionType?: keyof typeof DivisionType | null;
  completed?: boolean | null;
  hrHoursMondayThursday?: number | null;
  hrHoursFriday?: number | null;
  addHoursMondayFriday?: number | null;
  addHoursWeekend?: number | null;
}

export type NewContractDivision = Omit<ContractDivision, 'contractDivisionId'> & { contractDivisionId: null };
