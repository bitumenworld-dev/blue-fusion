export interface Storage {
  id: number;
  storageCode?: string | null;
  buildSmartCode?: string | null;
  company?: string | null;
  site?: string | null;
  name?: string | null;
  storageContent?: string | null;
  capacity?: number | null;
  accessKey?: string | null;
  isActive?: boolean | null;
  isFixed?: boolean | null;
  companyId?: number | null;
  siteId?: number | null;
}

export type NewStorage = Omit<Storage, 'id'> & { id: null };
