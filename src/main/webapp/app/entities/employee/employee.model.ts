export interface IEmployee {
  id: number;
  employeeId?: number | null;
  contractActive?: boolean | null;
}

export type NewEmployee = Omit<IEmployee, 'id'> & { id: null };
