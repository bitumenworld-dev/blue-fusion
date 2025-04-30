import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IContractDivision } from '../contract-division.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../contract-division.test-samples';

import { ContractDivisionService } from './contract-division.service';

const requireRestSample: IContractDivision = {
  ...sampleWithRequiredData,
};

describe('ContractDivision Service', () => {
  let service: ContractDivisionService;
  let httpMock: HttpTestingController;
  let expectedResult: IContractDivision | IContractDivision[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ContractDivisionService);
    httpMock = TestBed.inject(HttpTestingController);
  });

  describe('Service methods', () => {
    it('should find an element', () => {
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.find(123).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should create a ContractDivision', () => {
      const contractDivision = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(contractDivision).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ContractDivision', () => {
      const contractDivision = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(contractDivision).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ContractDivision', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ContractDivision', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ContractDivision', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addContractDivisionToCollectionIfMissing', () => {
      it('should add a ContractDivision to an empty array', () => {
        const contractDivision: IContractDivision = sampleWithRequiredData;
        expectedResult = service.addContractDivisionToCollectionIfMissing([], contractDivision);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contractDivision);
      });

      it('should not add a ContractDivision to an array that contains it', () => {
        const contractDivision: IContractDivision = sampleWithRequiredData;
        const contractDivisionCollection: IContractDivision[] = [
          {
            ...contractDivision,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addContractDivisionToCollectionIfMissing(contractDivisionCollection, contractDivision);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ContractDivision to an array that doesn't contain it", () => {
        const contractDivision: IContractDivision = sampleWithRequiredData;
        const contractDivisionCollection: IContractDivision[] = [sampleWithPartialData];
        expectedResult = service.addContractDivisionToCollectionIfMissing(contractDivisionCollection, contractDivision);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contractDivision);
      });

      it('should add only unique ContractDivision to an array', () => {
        const contractDivisionArray: IContractDivision[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const contractDivisionCollection: IContractDivision[] = [sampleWithRequiredData];
        expectedResult = service.addContractDivisionToCollectionIfMissing(contractDivisionCollection, ...contractDivisionArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const contractDivision: IContractDivision = sampleWithRequiredData;
        const contractDivision2: IContractDivision = sampleWithPartialData;
        expectedResult = service.addContractDivisionToCollectionIfMissing([], contractDivision, contractDivision2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(contractDivision);
        expect(expectedResult).toContain(contractDivision2);
      });

      it('should accept null and undefined values', () => {
        const contractDivision: IContractDivision = sampleWithRequiredData;
        expectedResult = service.addContractDivisionToCollectionIfMissing([], null, contractDivision, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(contractDivision);
      });

      it('should return initial array if no ContractDivision is added', () => {
        const contractDivisionCollection: IContractDivision[] = [sampleWithRequiredData];
        expectedResult = service.addContractDivisionToCollectionIfMissing(contractDivisionCollection, undefined, null);
        expect(expectedResult).toEqual(contractDivisionCollection);
      });
    });

    describe('compareContractDivision', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareContractDivision(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18852 };
        const entity2 = null;

        const compareResult1 = service.compareContractDivision(entity1, entity2);
        const compareResult2 = service.compareContractDivision(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18852 };
        const entity2 = { id: 31405 };

        const compareResult1 = service.compareContractDivision(entity1, entity2);
        const compareResult2 = service.compareContractDivision(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18852 };
        const entity2 = { id: 18852 };

        const compareResult1 = service.compareContractDivision(entity1, entity2);
        const compareResult2 = service.compareContractDivision(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
