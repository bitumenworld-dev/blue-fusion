import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { isPresent } from 'app/core/util/operators';
import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { IContractDivision, NewContractDivision } from '../contract-division.model';

export type PartialUpdateContractDivision = Partial<IContractDivision> & Pick<IContractDivision, 'id'>;

export type EntityResponseType = HttpResponse<IContractDivision>;
export type EntityArrayResponseType = HttpResponse<IContractDivision[]>;

@Injectable({ providedIn: 'root' })
export class ContractDivisionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contract-divisions');

  create(contractDivision: NewContractDivision): Observable<EntityResponseType> {
    return this.http.post<IContractDivision>(this.resourceUrl, contractDivision, { observe: 'response' });
  }

  update(contractDivision: IContractDivision): Observable<EntityResponseType> {
    return this.http.put<IContractDivision>(
      `${this.resourceUrl}/${this.getContractDivisionIdentifier(contractDivision)}`,
      contractDivision,
      { observe: 'response' },
    );
  }

  partialUpdate(contractDivision: PartialUpdateContractDivision): Observable<EntityResponseType> {
    return this.http.patch<IContractDivision>(
      `${this.resourceUrl}/${this.getContractDivisionIdentifier(contractDivision)}`,
      contractDivision,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContractDivision>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContractDivision[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContractDivisionIdentifier(contractDivision: Pick<IContractDivision, 'id'>): number {
    return contractDivision.id;
  }

  compareContractDivision(o1: Pick<IContractDivision, 'id'> | null, o2: Pick<IContractDivision, 'id'> | null): boolean {
    return o1 && o2 ? this.getContractDivisionIdentifier(o1) === this.getContractDivisionIdentifier(o2) : o1 === o2;
  }

  addContractDivisionToCollectionIfMissing<Type extends Pick<IContractDivision, 'id'>>(
    contractDivisionCollection: Type[],
    ...contractDivisionsToCheck: (Type | null | undefined)[]
  ): Type[] {
    const contractDivisions: Type[] = contractDivisionsToCheck.filter(isPresent);
    if (contractDivisions.length > 0) {
      const contractDivisionCollectionIdentifiers = contractDivisionCollection.map(contractDivisionItem =>
        this.getContractDivisionIdentifier(contractDivisionItem),
      );
      const contractDivisionsToAdd = contractDivisions.filter(contractDivisionItem => {
        const contractDivisionIdentifier = this.getContractDivisionIdentifier(contractDivisionItem);
        if (contractDivisionCollectionIdentifiers.includes(contractDivisionIdentifier)) {
          return false;
        }
        contractDivisionCollectionIdentifiers.push(contractDivisionIdentifier);
        return true;
      });
      return [...contractDivisionsToAdd, ...contractDivisionCollection];
    }
    return contractDivisionCollection;
  }
}
