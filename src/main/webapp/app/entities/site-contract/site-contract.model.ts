export interface ISiteContract {
  id: number;
  siteContractId?: number | null;
  siteId?: number | null;
  contractId?: number | null;
}

export type NewSiteContract = Omit<ISiteContract, 'id'> & { id: null };
