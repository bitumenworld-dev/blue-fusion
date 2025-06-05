import { Injectable, inject } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { ApplicationConfigService } from 'app/core/config/application-config.service';
import { createRequestOption } from 'app/core/request/request-util';
import { ContractDivision, NewContractDivision } from '../contract-division.model';

export type PartialUpdateContractDivision = Partial<ContractDivision> & Pick<ContractDivision, 'contractDivisionId'>;

export type ContractDivisionEntityResponseType = HttpResponse<ContractDivision>;
export type ContractDivisionEntityArrayResponseType = HttpResponse<ContractDivision[]>;

@Injectable({ providedIn: 'root' })
export class ContractDivisionService {
  protected readonly http = inject(HttpClient);
  protected readonly applicationConfigService = inject(ApplicationConfigService);

  protected resourceUrl = this.applicationConfigService.getEndpointFor('api/contract-divisions');

  create(contractDivision: NewContractDivision): Observable<ContractDivisionEntityResponseType> {
    return this.http.post<ContractDivision>(this.resourceUrl, contractDivision, { observe: 'response' });
  }

  update(contractDivision: ContractDivision): Observable<ContractDivisionEntityResponseType> {
    return this.http.put<ContractDivision>(
      `${this.resourceUrl}/${this.getContractDivisionIdentifier(contractDivision)}`,
      contractDivision,
      { observe: 'response' },
    );
  }

  partialUpdate(contractDivision: PartialUpdateContractDivision): Observable<ContractDivisionEntityResponseType> {
    return this.http.patch<ContractDivision>(
      `${this.resourceUrl}/${this.getContractDivisionIdentifier(contractDivision)}`,
      contractDivision,
      { observe: 'response' },
    );
  }

  find(id: number): Observable<ContractDivisionEntityResponseType> {
    return this.http.get<ContractDivision>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<ContractDivisionEntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ContractDivision[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  getContractDivisionIdentifier(contractDivision: Pick<ContractDivision, 'contractDivisionId'>): number {
    return contractDivision.contractDivisionId;
  }
}
