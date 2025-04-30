import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ISiteContract, NewSiteContract } from '../site-contract.model';

export type PartialUpdateSiteContract = Partial<ISiteContract> & Pick<ISiteContract, 'id'>;

export type EntityResponseType = HttpResponse<ISiteContract>;
export type EntityArrayResponseType = HttpResponse<ISiteContract[]>;

@Injectable({ providedIn: 'root' })
export class SiteContractService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/site-contracts');

  create(siteContract: NewSiteContract): Observable<EntityResponseType> {
    return this.http.post<ISiteContract>(this.resourceUrl, siteContract, { observe: 'response' });
  }

  update(siteContract: ISiteContract): Observable<EntityResponseType> {
    return this.http.put<ISiteContract>(`${this.resourceUrl}/${this.getSiteContractIdentifier(siteContract)}`, siteContract, {
      observe: 'response',
    });
  }

  partialUpdate(siteContract: PartialUpdateSiteContract): Observable<EntityResponseType> {
    return this.http.patch<ISiteContract>(`${this.resourceUrl}/${this.getSiteContractIdentifier(siteContract)}`, siteContract, {
      observe: 'response',
    });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ISiteContract>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ISiteContract[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getSiteContractIdentifier(siteContract: Pick<ISiteContract, 'id'>): number {
    return siteContract.id;
  }

  compareSiteContract(o1: Pick<ISiteContract, 'id'> | null, o2: Pick<ISiteContract, 'id'> | null): boolean {
    return o1 && o2 ? this.getSiteContractIdentifier(o1) === this.getSiteContractIdentifier(o2) : o1 === o2;
  }

  addSiteContractToCollectionIfMissing<Type extends Pick<ISiteContract, 'id'>>(
    siteContractCollection: Type[],
    ...siteContractsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const siteContracts: Type[] = siteContractsToCheck.filter(isPresent);
    if (siteContracts.length > 0) {
      const siteContractCollectionIdentifiers = siteContractCollection.map(siteContractItem =>
        this.getSiteContractIdentifier(siteContractItem),
      );
      const siteContractsToAdd = siteContracts.filter(siteContractItem => {
        const siteContractIdentifier = this.getSiteContractIdentifier(siteContractItem);
        if (siteContractCollectionIdentifiers.includes(siteContractIdentifier)) {
          return false;
        }
        siteContractCollectionIdentifiers.push(siteContractIdentifier);
        return true;
      });
      return [...siteContractsToAdd, ...siteContractCollection];
    }
    return siteContractCollection;
  }
}
