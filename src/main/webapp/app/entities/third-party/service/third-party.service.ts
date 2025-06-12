import { inject, Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { NewThirdParty, ThirdParty } from '../third-party.model';

export type ThirdPartyResponseType = HttpResponse<ThirdParty>;
export type ThirdPartyArrayResponseType = HttpResponse<ThirdParty[]>;

@Injectable({ providedIn: 'root' })
export class ThirdPartyService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('/api/third-party');

  create(thirdParty: NewThirdParty): Observable<ThirdPartyResponseType> {
    return this.http.post<ThirdParty>(this.resourceUrl, thirdParty, { observe: 'response' });
  }

  update(thirdParty: ThirdParty): Observable<ThirdPartyResponseType> {
    return this.http.put<ThirdParty>(`${this.resourceUrl}/${thirdParty.thirdPartyId}`, thirdParty, { observe: 'response' });
  }

  find(id: number): Observable<ThirdPartyResponseType> {
    return this.http.get<ThirdParty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<ThirdPartyArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ThirdParty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
