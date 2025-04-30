export interface ISite {
  id: number;
  siteId?: number | null;
  siteName?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  isActive?: boolean | null;
  siteNotes?: string | null;
  siteImageUrl?: string | null;
  companyId?: number | null;
}

export type NewSite = Omit<ISite, 'id'> & { id: null };
