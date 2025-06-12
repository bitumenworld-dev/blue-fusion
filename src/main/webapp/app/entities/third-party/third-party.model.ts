export interface ThirdParty {
  thirdPartyId: number;
  thirdPartyName?: string | null;
  thirdPartyShortName?: string | null;
  usesFuelSystem?: boolean | null;
}

export type NewThirdParty = Omit<ThirdParty, 'thirdPartyId'> & { thirdPartyId: null };
