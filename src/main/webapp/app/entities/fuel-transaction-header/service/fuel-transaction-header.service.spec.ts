import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuelTransactionHeader } from '../fuel-transaction-header.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../fuel-transaction-header.test-samples';

import { FuelTransactionHeaderService } from './fuel-transaction-header.service';

const requireRestSample: IFuelTransactionHeader = {
  ...sampleWithRequiredData,
};

describe('FuelTransactionHeader Service', () => {
  let service: FuelTransactionHeaderService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelTransactionHeader | IFuelTransactionHeader[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuelTransactionHeaderService);
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

    it('should create a FuelTransactionHeader', () => {
      const fuelTransactionHeader = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelTransactionHeader).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelTransactionHeader', () => {
      const fuelTransactionHeader = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelTransactionHeader).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelTransactionHeader', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelTransactionHeader', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelTransactionHeader', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelTransactionHeaderToCollectionIfMissing', () => {
      it('should add a FuelTransactionHeader to an empty array', () => {
        const fuelTransactionHeader: IFuelTransactionHeader = sampleWithRequiredData;
        expectedResult = service.addFuelTransactionHeaderToCollectionIfMissing([], fuelTransactionHeader);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTransactionHeader);
      });

      it('should not add a FuelTransactionHeader to an array that contains it', () => {
        const fuelTransactionHeader: IFuelTransactionHeader = sampleWithRequiredData;
        const fuelTransactionHeaderCollection: IFuelTransactionHeader[] = [
          {
            ...fuelTransactionHeader,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelTransactionHeaderToCollectionIfMissing(fuelTransactionHeaderCollection, fuelTransactionHeader);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelTransactionHeader to an array that doesn't contain it", () => {
        const fuelTransactionHeader: IFuelTransactionHeader = sampleWithRequiredData;
        const fuelTransactionHeaderCollection: IFuelTransactionHeader[] = [sampleWithPartialData];
        expectedResult = service.addFuelTransactionHeaderToCollectionIfMissing(fuelTransactionHeaderCollection, fuelTransactionHeader);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTransactionHeader);
      });

      it('should add only unique FuelTransactionHeader to an array', () => {
        const fuelTransactionHeaderArray: IFuelTransactionHeader[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelTransactionHeaderCollection: IFuelTransactionHeader[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTransactionHeaderToCollectionIfMissing(
          fuelTransactionHeaderCollection,
          ...fuelTransactionHeaderArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelTransactionHeader: IFuelTransactionHeader = sampleWithRequiredData;
        const fuelTransactionHeader2: IFuelTransactionHeader = sampleWithPartialData;
        expectedResult = service.addFuelTransactionHeaderToCollectionIfMissing([], fuelTransactionHeader, fuelTransactionHeader2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTransactionHeader);
        expect(expectedResult).toContain(fuelTransactionHeader2);
      });

      it('should accept null and undefined values', () => {
        const fuelTransactionHeader: IFuelTransactionHeader = sampleWithRequiredData;
        expectedResult = service.addFuelTransactionHeaderToCollectionIfMissing([], null, fuelTransactionHeader, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTransactionHeader);
      });

      it('should return initial array if no FuelTransactionHeader is added', () => {
        const fuelTransactionHeaderCollection: IFuelTransactionHeader[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTransactionHeaderToCollectionIfMissing(fuelTransactionHeaderCollection, undefined, null);
        expect(expectedResult).toEqual(fuelTransactionHeaderCollection);
      });
    });

    describe('compareFuelTransactionHeader', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelTransactionHeader(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 20230 };
        const entity2 = null;

        const compareResult1 = service.compareFuelTransactionHeader(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionHeader(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 20230 };
        const entity2 = { id: 29654 };

        const compareResult1 = service.compareFuelTransactionHeader(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionHeader(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 20230 };
        const entity2 = { id: 20230 };

        const compareResult1 = service.compareFuelTransactionHeader(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionHeader(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
