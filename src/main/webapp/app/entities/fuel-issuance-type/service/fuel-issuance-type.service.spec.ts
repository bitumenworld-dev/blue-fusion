import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuelIssuanceType } from '../fuel-issuance-type.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../fuel-issuance-type.test-samples';

import { FuelIssuanceTypeService } from './fuel-issuance-type.service';

const requireRestSample: IFuelIssuanceType = {
  ...sampleWithRequiredData,
};

describe('FuelIssuanceType Service', () => {
  let service: FuelIssuanceTypeService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelIssuanceType | IFuelIssuanceType[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuelIssuanceTypeService);
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

    it('should create a FuelIssuanceType', () => {
      const fuelIssuanceType = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelIssuanceType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelIssuanceType', () => {
      const fuelIssuanceType = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelIssuanceType).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelIssuanceType', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelIssuanceType', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelIssuanceType', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelIssuanceTypeToCollectionIfMissing', () => {
      it('should add a FuelIssuanceType to an empty array', () => {
        const fuelIssuanceType: IFuelIssuanceType = sampleWithRequiredData;
        expectedResult = service.addFuelIssuanceTypeToCollectionIfMissing([], fuelIssuanceType);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelIssuanceType);
      });

      it('should not add a FuelIssuanceType to an array that contains it', () => {
        const fuelIssuanceType: IFuelIssuanceType = sampleWithRequiredData;
        const fuelIssuanceTypeCollection: IFuelIssuanceType[] = [
          {
            ...fuelIssuanceType,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelIssuanceTypeToCollectionIfMissing(fuelIssuanceTypeCollection, fuelIssuanceType);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelIssuanceType to an array that doesn't contain it", () => {
        const fuelIssuanceType: IFuelIssuanceType = sampleWithRequiredData;
        const fuelIssuanceTypeCollection: IFuelIssuanceType[] = [sampleWithPartialData];
        expectedResult = service.addFuelIssuanceTypeToCollectionIfMissing(fuelIssuanceTypeCollection, fuelIssuanceType);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelIssuanceType);
      });

      it('should add only unique FuelIssuanceType to an array', () => {
        const fuelIssuanceTypeArray: IFuelIssuanceType[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelIssuanceTypeCollection: IFuelIssuanceType[] = [sampleWithRequiredData];
        expectedResult = service.addFuelIssuanceTypeToCollectionIfMissing(fuelIssuanceTypeCollection, ...fuelIssuanceTypeArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelIssuanceType: IFuelIssuanceType = sampleWithRequiredData;
        const fuelIssuanceType2: IFuelIssuanceType = sampleWithPartialData;
        expectedResult = service.addFuelIssuanceTypeToCollectionIfMissing([], fuelIssuanceType, fuelIssuanceType2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelIssuanceType);
        expect(expectedResult).toContain(fuelIssuanceType2);
      });

      it('should accept null and undefined values', () => {
        const fuelIssuanceType: IFuelIssuanceType = sampleWithRequiredData;
        expectedResult = service.addFuelIssuanceTypeToCollectionIfMissing([], null, fuelIssuanceType, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelIssuanceType);
      });

      it('should return initial array if no FuelIssuanceType is added', () => {
        const fuelIssuanceTypeCollection: IFuelIssuanceType[] = [sampleWithRequiredData];
        expectedResult = service.addFuelIssuanceTypeToCollectionIfMissing(fuelIssuanceTypeCollection, undefined, null);
        expect(expectedResult).toEqual(fuelIssuanceTypeCollection);
      });
    });

    describe('compareFuelIssuanceType', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelIssuanceType(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 17477 };
        const entity2 = null;

        const compareResult1 = service.compareFuelIssuanceType(entity1, entity2);
        const compareResult2 = service.compareFuelIssuanceType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 17477 };
        const entity2 = { id: 12535 };

        const compareResult1 = service.compareFuelIssuanceType(entity1, entity2);
        const compareResult2 = service.compareFuelIssuanceType(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 17477 };
        const entity2 = { id: 17477 };

        const compareResult1 = service.compareFuelIssuanceType(entity1, entity2);
        const compareResult2 = service.compareFuelIssuanceType(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
