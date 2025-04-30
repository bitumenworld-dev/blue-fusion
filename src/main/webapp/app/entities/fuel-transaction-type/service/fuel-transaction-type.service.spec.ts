import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuelTransactionType } from '../fuel-transaction-type.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../fuel-transaction-type.test-samples';

import { FuelTransactionTypeService } from './fuel-transaction-type.service';

const requireRestSample: IFuelTransactionType = {
  ...sampleWithRequiredData,
};

describe('FuelTransactionType Service', () => {
  let service: FuelTransactionTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelTransactionType | IFuelTransactionType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuelTransactionTypeService);
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

    it('should create a FuelTransactionType', () => {
      const fuelTransactionType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelTransactionType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelTransactionType', () => {
      const fuelTransactionType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelTransactionType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelTransactionType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelTransactionType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelTransactionType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelTransactionTypeToCollectionIfMissing', () => {
      it('should add a FuelTransactionType to an empty array', () => {
        const fuelTransactionType: IFuelTransactionType = sampleWithRequiredData;
        expectedResult = service.addFuelTransactionTypeToCollectionIfMissing([], fuelTransactionType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTransactionType);
      });

      it('should not add a FuelTransactionType to an array that contains it', () => {
        const fuelTransactionType: IFuelTransactionType = sampleWithRequiredData;
        const fuelTransactionTypeCollection: IFuelTransactionType[] = [
          {
            ...fuelTransactionType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelTransactionTypeToCollectionIfMissing(fuelTransactionTypeCollection, fuelTransactionType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelTransactionType to an array that doesn't contain it", () => {
        const fuelTransactionType: IFuelTransactionType = sampleWithRequiredData;
        const fuelTransactionTypeCollection: IFuelTransactionType[] = [sampleWithPartialData];
        expectedResult = service.addFuelTransactionTypeToCollectionIfMissing(fuelTransactionTypeCollection, fuelTransactionType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTransactionType);
      });

      it('should add only unique FuelTransactionType to an array', () => {
        const fuelTransactionTypeArray: IFuelTransactionType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelTransactionTypeCollection: IFuelTransactionType[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTransactionTypeToCollectionIfMissing(fuelTransactionTypeCollection, ...fuelTransactionTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelTransactionType: IFuelTransactionType = sampleWithRequiredData;
        const fuelTransactionType2: IFuelTransactionType = sampleWithPartialData;
        expectedResult = service.addFuelTransactionTypeToCollectionIfMissing([], fuelTransactionType, fuelTransactionType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTransactionType);
        expect(expectedResult).toContain(fuelTransactionType2);
      });

      it('should accept null and undefined values', () => {
        const fuelTransactionType: IFuelTransactionType = sampleWithRequiredData;
        expectedResult = service.addFuelTransactionTypeToCollectionIfMissing([], null, fuelTransactionType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTransactionType);
      });

      it('should return initial array if no FuelTransactionType is added', () => {
        const fuelTransactionTypeCollection: IFuelTransactionType[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTransactionTypeToCollectionIfMissing(fuelTransactionTypeCollection, undefined, null);
        expect(expectedResult).toEqual(fuelTransactionTypeCollection);
      });
    });

    describe('compareFuelTransactionType', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelTransactionType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 30874 };
        const entity2 = null;

        const compareResult1 = service.compareFuelTransactionType(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 30874 };
        const entity2 = { id: 3949 };

        const compareResult1 = service.compareFuelTransactionType(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 30874 };
        const entity2 = { id: 30874 };

        const compareResult1 = service.compareFuelTransactionType(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
