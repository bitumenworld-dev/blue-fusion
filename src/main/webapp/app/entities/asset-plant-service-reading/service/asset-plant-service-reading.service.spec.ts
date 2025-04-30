import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { DATE_FORMAT } from 'app/config/input.constants';
import { IAssetPlantServiceReading } from '../asset-plant-service-reading.model';
import {
  sampleWithFullData,
  sampleWithNewData,
  sampleWithPartialData,
  sampleWithRequiredData,
} from '../asset-plant-service-reading.test-samples';

import { AssetPlantServiceReadingService, RestAssetPlantServiceReading } from './asset-plant-service-reading.service';

const requireRestSample: RestAssetPlantServiceReading = {
  ...sampleWithRequiredData,
  estimatedNextServiceDate: sampleWithRequiredData.estimatedNextServiceDate?.format(DATE_FORMAT),
  lastServiceDate: sampleWithRequiredData.lastServiceDate?.format(DATE_FORMAT),
  latestSmrDate: sampleWithRequiredData.latestSmrDate?.format(DATE_FORMAT),
};

describe('AssetPlantServiceReading Service', () => {
  let service: AssetPlantServiceReadingService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssetPlantServiceReading | IAssetPlantServiceReading[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AssetPlantServiceReadingService);
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

    it('should create a AssetPlantServiceReading', () => {
      const assetPlantServiceReading = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(assetPlantServiceReading).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssetPlantServiceReading', () => {
      const assetPlantServiceReading = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(assetPlantServiceReading).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AssetPlantServiceReading', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AssetPlantServiceReading', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AssetPlantServiceReading', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssetPlantServiceReadingToCollectionIfMissing', () => {
      it('should add a AssetPlantServiceReading to an empty array', () => {
        const assetPlantServiceReading: IAssetPlantServiceReading = sampleWithRequiredData;
        expectedResult = service.addAssetPlantServiceReadingToCollectionIfMissing([], assetPlantServiceReading);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assetPlantServiceReading);
      });

      it('should not add a AssetPlantServiceReading to an array that contains it', () => {
        const assetPlantServiceReading: IAssetPlantServiceReading = sampleWithRequiredData;
        const assetPlantServiceReadingCollection: IAssetPlantServiceReading[] = [
          {
            ...assetPlantServiceReading,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssetPlantServiceReadingToCollectionIfMissing(
          assetPlantServiceReadingCollection,
          assetPlantServiceReading,
        );
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssetPlantServiceReading to an array that doesn't contain it", () => {
        const assetPlantServiceReading: IAssetPlantServiceReading = sampleWithRequiredData;
        const assetPlantServiceReadingCollection: IAssetPlantServiceReading[] = [sampleWithPartialData];
        expectedResult = service.addAssetPlantServiceReadingToCollectionIfMissing(
          assetPlantServiceReadingCollection,
          assetPlantServiceReading,
        );
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assetPlantServiceReading);
      });

      it('should add only unique AssetPlantServiceReading to an array', () => {
        const assetPlantServiceReadingArray: IAssetPlantServiceReading[] = [
          sampleWithRequiredData,
          sampleWithPartialData,
          sampleWithFullData,
        ];
        const assetPlantServiceReadingCollection: IAssetPlantServiceReading[] = [sampleWithRequiredData];
        expectedResult = service.addAssetPlantServiceReadingToCollectionIfMissing(
          assetPlantServiceReadingCollection,
          ...assetPlantServiceReadingArray,
        );
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assetPlantServiceReading: IAssetPlantServiceReading = sampleWithRequiredData;
        const assetPlantServiceReading2: IAssetPlantServiceReading = sampleWithPartialData;
        expectedResult = service.addAssetPlantServiceReadingToCollectionIfMissing([], assetPlantServiceReading, assetPlantServiceReading2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assetPlantServiceReading);
        expect(expectedResult).toContain(assetPlantServiceReading2);
      });

      it('should accept null and undefined values', () => {
        const assetPlantServiceReading: IAssetPlantServiceReading = sampleWithRequiredData;
        expectedResult = service.addAssetPlantServiceReadingToCollectionIfMissing([], null, assetPlantServiceReading, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assetPlantServiceReading);
      });

      it('should return initial array if no AssetPlantServiceReading is added', () => {
        const assetPlantServiceReadingCollection: IAssetPlantServiceReading[] = [sampleWithRequiredData];
        expectedResult = service.addAssetPlantServiceReadingToCollectionIfMissing(assetPlantServiceReadingCollection, undefined, null);
        expect(expectedResult).toEqual(assetPlantServiceReadingCollection);
      });
    });

    describe('compareAssetPlantServiceReading', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssetPlantServiceReading(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 30847 };
        const entity2 = null;

        const compareResult1 = service.compareAssetPlantServiceReading(entity1, entity2);
        const compareResult2 = service.compareAssetPlantServiceReading(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 30847 };
        const entity2 = { id: 17261 };

        const compareResult1 = service.compareAssetPlantServiceReading(entity1, entity2);
        const compareResult2 = service.compareAssetPlantServiceReading(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 30847 };
        const entity2 = { id: 30847 };

        const compareResult1 = service.compareAssetPlantServiceReading(entity1, entity2);
        const compareResult2 = service.compareAssetPlantServiceReading(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
