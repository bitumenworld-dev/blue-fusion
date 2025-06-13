import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { Site, NewSite } from '../site.model';

export type PartialUpdateSite = Partial<Site> & Pick<Site, 'siteId'>;

export type SiteResponseType = HttpResponse<Site>;
export type SiteArrayResponseType = HttpResponse<Site[]>;

@Injectable({ providedIn: 'root' })
export class SiteService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/sites');

  create(site: NewSite): Observable<SiteResponseType> {
    return this.http.post<Site>(this.resourceUrl, site, { observe: 'response' });
  }

  update(site: Site): Observable<SiteResponseType> {
    return this.http.put<Site>(`${this.resourceUrl}/${site.siteId}`, site, { observe: 'response' });
  }

  uploadImage(site: Site): Observable<SiteResponseType> {
    const formData = new FormData();
    // @ts-ignore
    formData.append('image', site.siteImage);
    return this.http.put<Site>(`${this.resourceUrl}/${site.siteId}`, formData, { observe: 'response' });
  }

  partialUpdate(site: PartialUpdateSite): Observable<SiteResponseType> {
    return this.http.patch<Site>(`${this.resourceUrl}/${site.siteId}`, site, { observe: 'response' });
  }

  find(id: number): Observable<SiteResponseType> {
    return this.http.get<Site>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<SiteArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<Site[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSiteImage(site: Pick<Site, 'siteImage'>): File | null | undefined {
    return site.siteImage;
  }
}
