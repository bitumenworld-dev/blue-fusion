import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAssetPlant } from '../asset-plant.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../asset-plant.test-samples';

import { AssetPlantService } from './asset-plant.service';

const requireRestSample: IAssetPlant = {
  ...sampleWithRequiredData,
};

describe('AssetPlant Service', () => {
  let service: AssetPlantService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssetPlant | IAssetPlant[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AssetPlantService);
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

    it('should create a AssetPlant', () => {
      const assetPlant = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(assetPlant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssetPlant', () => {
      const assetPlant = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(assetPlant).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AssetPlant', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AssetPlant', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AssetPlant', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssetPlantToCollectionIfMissing', () => {
      it('should add a AssetPlant to an empty array', () => {
        const assetPlant: IAssetPlant = sampleWithRequiredData;
        expectedResult = service.addAssetPlantToCollectionIfMissing([], assetPlant);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assetPlant);
      });

      it('should not add a AssetPlant to an array that contains it', () => {
        const assetPlant: IAssetPlant = sampleWithRequiredData;
        const assetPlantCollection: IAssetPlant[] = [
          {
            ...assetPlant,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssetPlantToCollectionIfMissing(assetPlantCollection, assetPlant);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssetPlant to an array that doesn't contain it", () => {
        const assetPlant: IAssetPlant = sampleWithRequiredData;
        const assetPlantCollection: IAssetPlant[] = [sampleWithPartialData];
        expectedResult = service.addAssetPlantToCollectionIfMissing(assetPlantCollection, assetPlant);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assetPlant);
      });

      it('should add only unique AssetPlant to an array', () => {
        const assetPlantArray: IAssetPlant[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const assetPlantCollection: IAssetPlant[] = [sampleWithRequiredData];
        expectedResult = service.addAssetPlantToCollectionIfMissing(assetPlantCollection, ...assetPlantArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assetPlant: IAssetPlant = sampleWithRequiredData;
        const assetPlant2: IAssetPlant = sampleWithPartialData;
        expectedResult = service.addAssetPlantToCollectionIfMissing([], assetPlant, assetPlant2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assetPlant);
        expect(expectedResult).toContain(assetPlant2);
      });

      it('should accept null and undefined values', () => {
        const assetPlant: IAssetPlant = sampleWithRequiredData;
        expectedResult = service.addAssetPlantToCollectionIfMissing([], null, assetPlant, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assetPlant);
      });

      it('should return initial array if no AssetPlant is added', () => {
        const assetPlantCollection: IAssetPlant[] = [sampleWithRequiredData];
        expectedResult = service.addAssetPlantToCollectionIfMissing(assetPlantCollection, undefined, null);
        expect(expectedResult).toEqual(assetPlantCollection);
      });
    });

    describe('compareAssetPlant', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssetPlant(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 18137 };
        const entity2 = null;

        const compareResult1 = service.compareAssetPlant(entity1, entity2);
        const compareResult2 = service.compareAssetPlant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 18137 };
        const entity2 = { id: 2183 };

        const compareResult1 = service.compareAssetPlant(entity1, entity2);
        const compareResult2 = service.compareAssetPlant(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 18137 };
        const entity2 = { id: 18137 };

        const compareResult1 = service.compareAssetPlant(entity1, entity2);
        const compareResult2 = service.compareAssetPlant(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
