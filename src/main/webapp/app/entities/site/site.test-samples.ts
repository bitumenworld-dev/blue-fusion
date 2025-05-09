import { ISite, NewSite } from './site.model';

export const sampleWithRequiredData: ISite = {
  id: 15724,
};

export const sampleWithPartialData: ISite = {
  id: 5320,
  siteName: 'under',
  isActive: true,
  siteImageUrl: 'bog unique',
};

export const sampleWithFullData: ISite = {
  id: 31836,
  siteName: 'jacket disadvantage till',
  latitude: 'flawed boastfully',
  longitude: 'yahoo lovingly creature',
  isActive: false,
  siteNotes: 'hose',
  siteImageUrl: 'before provided clumsy',
  companyId: 22607,
};

export const sampleWithNewData: NewSite = {
  id: null,
};

Object.freeze(sampleWithNewData);
Object.freeze(sampleWithRequiredData);
Object.freeze(sampleWithPartialData);
Object.freeze(sampleWithFullData);
