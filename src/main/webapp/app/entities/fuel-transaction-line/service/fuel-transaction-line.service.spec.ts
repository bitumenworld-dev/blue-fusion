import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuelTransactionLine } from '../fuel-transaction-line.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../fuel-transaction-line.test-samples';

import { FuelTransactionLineService } from './fuel-transaction-line.service';

const requireRestSample: IFuelTransactionLine = {
  ...sampleWithRequiredData,
};

describe('FuelTransactionLine Service', () => {
  let service: FuelTransactionLineService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelTransactionLine | IFuelTransactionLine[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuelTransactionLineService);
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

    it('should create a FuelTransactionLine', () => {
      const fuelTransactionLine = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelTransactionLine).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelTransactionLine', () => {
      const fuelTransactionLine = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelTransactionLine).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelTransactionLine', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelTransactionLine', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelTransactionLine', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelTransactionLineToCollectionIfMissing', () => {
      it('should add a FuelTransactionLine to an empty array', () => {
        const fuelTransactionLine: IFuelTransactionLine = sampleWithRequiredData;
        expectedResult = service.addFuelTransactionLineToCollectionIfMissing([], fuelTransactionLine);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTransactionLine);
      });

      it('should not add a FuelTransactionLine to an array that contains it', () => {
        const fuelTransactionLine: IFuelTransactionLine = sampleWithRequiredData;
        const fuelTransactionLineCollection: IFuelTransactionLine[] = [
          {
            ...fuelTransactionLine,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelTransactionLineToCollectionIfMissing(fuelTransactionLineCollection, fuelTransactionLine);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelTransactionLine to an array that doesn't contain it", () => {
        const fuelTransactionLine: IFuelTransactionLine = sampleWithRequiredData;
        const fuelTransactionLineCollection: IFuelTransactionLine[] = [sampleWithPartialData];
        expectedResult = service.addFuelTransactionLineToCollectionIfMissing(fuelTransactionLineCollection, fuelTransactionLine);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTransactionLine);
      });

      it('should add only unique FuelTransactionLine to an array', () => {
        const fuelTransactionLineArray: IFuelTransactionLine[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelTransactionLineCollection: IFuelTransactionLine[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTransactionLineToCollectionIfMissing(fuelTransactionLineCollection, ...fuelTransactionLineArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelTransactionLine: IFuelTransactionLine = sampleWithRequiredData;
        const fuelTransactionLine2: IFuelTransactionLine = sampleWithPartialData;
        expectedResult = service.addFuelTransactionLineToCollectionIfMissing([], fuelTransactionLine, fuelTransactionLine2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelTransactionLine);
        expect(expectedResult).toContain(fuelTransactionLine2);
      });

      it('should accept null and undefined values', () => {
        const fuelTransactionLine: IFuelTransactionLine = sampleWithRequiredData;
        expectedResult = service.addFuelTransactionLineToCollectionIfMissing([], null, fuelTransactionLine, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelTransactionLine);
      });

      it('should return initial array if no FuelTransactionLine is added', () => {
        const fuelTransactionLineCollection: IFuelTransactionLine[] = [sampleWithRequiredData];
        expectedResult = service.addFuelTransactionLineToCollectionIfMissing(fuelTransactionLineCollection, undefined, null);
        expect(expectedResult).toEqual(fuelTransactionLineCollection);
      });
    });

    describe('compareFuelTransactionLine', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelTransactionLine(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 26931 };
        const entity2 = null;

        const compareResult1 = service.compareFuelTransactionLine(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionLine(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 26931 };
        const entity2 = { id: 29745 };

        const compareResult1 = service.compareFuelTransactionLine(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionLine(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 26931 };
        const entity2 = { id: 26931 };

        const compareResult1 = service.compareFuelTransactionLine(entity1, entity2);
        const compareResult2 = service.compareFuelTransactionLine(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
