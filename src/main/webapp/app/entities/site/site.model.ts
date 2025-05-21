export interface ISite {
  id: number;
  companyId?: number | null;
  siteName?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  isActive?: boolean | null;
  siteNotes?: string | null;
  siteImage?: File | null;
  company?: string | null;
}

export type NewSite = Omit<ISite, 'id'> & { id: null };
