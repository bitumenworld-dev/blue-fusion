export interface Site {
  siteId: number;
  companyId?: number | null;
  siteName?: string | null;
  latitude?: string | null;
  longitude?: string | null;
  isActive?: boolean | null;
  hasWorkshop?: boolean | null;
  siteNotes?: string | null;
  siteImage?: File | null;
  company?: string | null;
}

export type NewSite = Omit<Site, 'id'> & { id: null };
