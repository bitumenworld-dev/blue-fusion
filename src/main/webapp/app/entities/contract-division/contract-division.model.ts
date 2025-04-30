import { DivisionType } from 'app/entities/enumerations/division-type.model';

export interface IContractDivision {
  id: number;
  contractDivisionId?: number | null;
  contractDivisionNumber?: string | null;
  companyId?: number | null;
  buildSmartReference?: string | null;
  shiftStart?: string | null;
  shiftEnd?: string | null;
  type?: keyof typeof DivisionType | null;
  completed?: boolean | null;
}

export type NewContractDivision = Omit<IContractDivision, 'id'> & { id: null };
