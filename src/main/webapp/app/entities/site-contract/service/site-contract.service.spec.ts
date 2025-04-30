import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { ISiteContract } from '../site-contract.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../site-contract.test-samples';

import { SiteContractService } from './site-contract.service';

const requireRestSample: ISiteContract = {
  ...sampleWithRequiredData,
};

describe('SiteContract Service', () => {
  let service: SiteContractService;
  let httpMock: HttpTestingController;
  let expectedResult: ISiteContract | ISiteContract[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(SiteContractService);
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

    it('should create a SiteContract', () => {
      const siteContract = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(siteContract).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a SiteContract', () => {
      const siteContract = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(siteContract).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a SiteContract', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of SiteContract', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a SiteContract', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addSiteContractToCollectionIfMissing', () => {
      it('should add a SiteContract to an empty array', () => {
        const siteContract: ISiteContract = sampleWithRequiredData;
        expectedResult = service.addSiteContractToCollectionIfMissing([], siteContract);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteContract);
      });

      it('should not add a SiteContract to an array that contains it', () => {
        const siteContract: ISiteContract = sampleWithRequiredData;
        const siteContractCollection: ISiteContract[] = [
          {
            ...siteContract,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addSiteContractToCollectionIfMissing(siteContractCollection, siteContract);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a SiteContract to an array that doesn't contain it", () => {
        const siteContract: ISiteContract = sampleWithRequiredData;
        const siteContractCollection: ISiteContract[] = [sampleWithPartialData];
        expectedResult = service.addSiteContractToCollectionIfMissing(siteContractCollection, siteContract);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteContract);
      });

      it('should add only unique SiteContract to an array', () => {
        const siteContractArray: ISiteContract[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const siteContractCollection: ISiteContract[] = [sampleWithRequiredData];
        expectedResult = service.addSiteContractToCollectionIfMissing(siteContractCollection, ...siteContractArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const siteContract: ISiteContract = sampleWithRequiredData;
        const siteContract2: ISiteContract = sampleWithPartialData;
        expectedResult = service.addSiteContractToCollectionIfMissing([], siteContract, siteContract2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(siteContract);
        expect(expectedResult).toContain(siteContract2);
      });

      it('should accept null and undefined values', () => {
        const siteContract: ISiteContract = sampleWithRequiredData;
        expectedResult = service.addSiteContractToCollectionIfMissing([], null, siteContract, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(siteContract);
      });

      it('should return initial array if no SiteContract is added', () => {
        const siteContractCollection: ISiteContract[] = [sampleWithRequiredData];
        expectedResult = service.addSiteContractToCollectionIfMissing(siteContractCollection, undefined, null);
        expect(expectedResult).toEqual(siteContractCollection);
      });
    });

    describe('compareSiteContract', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareSiteContract(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 7582 };
        const entity2 = null;

        const compareResult1 = service.compareSiteContract(entity1, entity2);
        const compareResult2 = service.compareSiteContract(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 7582 };
        const entity2 = { id: 17978 };

        const compareResult1 = service.compareSiteContract(entity1, entity2);
        const compareResult2 = service.compareSiteContract(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 7582 };
        const entity2 = { id: 7582 };

        const compareResult1 = service.compareSiteContract(entity1, entity2);
        const compareResult2 = service.compareSiteContract(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
