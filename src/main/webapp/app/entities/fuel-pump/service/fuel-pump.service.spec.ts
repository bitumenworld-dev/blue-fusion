import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IFuelPump } from '../fuel-pump.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../fuel-pump.test-samples';

import { FuelPumpService } from './fuel-pump.service';

const requireRestSample: IFuelPump = {
  ...sampleWithRequiredData,
};

describe('FuelPump Service', () => {
  let service: FuelPumpService;
  let httpMock: HttpTestingController;
  let expectedResult: IFuelPump | IFuelPump[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(FuelPumpService);
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

    it('should create a FuelPump', () => {
      const fuelPump = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(fuelPump).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a FuelPump', () => {
      const fuelPump = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(fuelPump).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a FuelPump', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of FuelPump', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a FuelPump', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addFuelPumpToCollectionIfMissing', () => {
      it('should add a FuelPump to an empty array', () => {
        const fuelPump: IFuelPump = sampleWithRequiredData;
        expectedResult = service.addFuelPumpToCollectionIfMissing([], fuelPump);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelPump);
      });

      it('should not add a FuelPump to an array that contains it', () => {
        const fuelPump: IFuelPump = sampleWithRequiredData;
        const fuelPumpCollection: IFuelPump[] = [
          {
            ...fuelPump,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addFuelPumpToCollectionIfMissing(fuelPumpCollection, fuelPump);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a FuelPump to an array that doesn't contain it", () => {
        const fuelPump: IFuelPump = sampleWithRequiredData;
        const fuelPumpCollection: IFuelPump[] = [sampleWithPartialData];
        expectedResult = service.addFuelPumpToCollectionIfMissing(fuelPumpCollection, fuelPump);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelPump);
      });

      it('should add only unique FuelPump to an array', () => {
        const fuelPumpArray: IFuelPump[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const fuelPumpCollection: IFuelPump[] = [sampleWithRequiredData];
        expectedResult = service.addFuelPumpToCollectionIfMissing(fuelPumpCollection, ...fuelPumpArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const fuelPump: IFuelPump = sampleWithRequiredData;
        const fuelPump2: IFuelPump = sampleWithPartialData;
        expectedResult = service.addFuelPumpToCollectionIfMissing([], fuelPump, fuelPump2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(fuelPump);
        expect(expectedResult).toContain(fuelPump2);
      });

      it('should accept null and undefined values', () => {
        const fuelPump: IFuelPump = sampleWithRequiredData;
        expectedResult = service.addFuelPumpToCollectionIfMissing([], null, fuelPump, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(fuelPump);
      });

      it('should return initial array if no FuelPump is added', () => {
        const fuelPumpCollection: IFuelPump[] = [sampleWithRequiredData];
        expectedResult = service.addFuelPumpToCollectionIfMissing(fuelPumpCollection, undefined, null);
        expect(expectedResult).toEqual(fuelPumpCollection);
      });
    });

    describe('compareFuelPump', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareFuelPump(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 3331 };
        const entity2 = null;

        const compareResult1 = service.compareFuelPump(entity1, entity2);
        const compareResult2 = service.compareFuelPump(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 3331 };
        const entity2 = { id: 7058 };

        const compareResult1 = service.compareFuelPump(entity1, entity2);
        const compareResult2 = service.compareFuelPump(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 3331 };
        const entity2 = { id: 3331 };

        const compareResult1 = service.compareFuelPump(entity1, entity2);
        const compareResult2 = service.compareFuelPump(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
