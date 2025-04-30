import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IAssetPlantPhoto } from '../asset-plant-photo.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../asset-plant-photo.test-samples';

import { AssetPlantPhotoService } from './asset-plant-photo.service';

const requireRestSample: IAssetPlantPhoto = {
  ...sampleWithRequiredData,
};

describe('AssetPlantPhoto Service', () => {
  let service: AssetPlantPhotoService;
  let httpMock: HttpTestingController;
  let expectedResult: IAssetPlantPhoto | IAssetPlantPhoto[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(AssetPlantPhotoService);
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

    it('should create a AssetPlantPhoto', () => {
      const assetPlantPhoto = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(assetPlantPhoto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a AssetPlantPhoto', () => {
      const assetPlantPhoto = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(assetPlantPhoto).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a AssetPlantPhoto', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of AssetPlantPhoto', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a AssetPlantPhoto', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addAssetPlantPhotoToCollectionIfMissing', () => {
      it('should add a AssetPlantPhoto to an empty array', () => {
        const assetPlantPhoto: IAssetPlantPhoto = sampleWithRequiredData;
        expectedResult = service.addAssetPlantPhotoToCollectionIfMissing([], assetPlantPhoto);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assetPlantPhoto);
      });

      it('should not add a AssetPlantPhoto to an array that contains it', () => {
        const assetPlantPhoto: IAssetPlantPhoto = sampleWithRequiredData;
        const assetPlantPhotoCollection: IAssetPlantPhoto[] = [
          {
            ...assetPlantPhoto,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addAssetPlantPhotoToCollectionIfMissing(assetPlantPhotoCollection, assetPlantPhoto);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a AssetPlantPhoto to an array that doesn't contain it", () => {
        const assetPlantPhoto: IAssetPlantPhoto = sampleWithRequiredData;
        const assetPlantPhotoCollection: IAssetPlantPhoto[] = [sampleWithPartialData];
        expectedResult = service.addAssetPlantPhotoToCollectionIfMissing(assetPlantPhotoCollection, assetPlantPhoto);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assetPlantPhoto);
      });

      it('should add only unique AssetPlantPhoto to an array', () => {
        const assetPlantPhotoArray: IAssetPlantPhoto[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const assetPlantPhotoCollection: IAssetPlantPhoto[] = [sampleWithRequiredData];
        expectedResult = service.addAssetPlantPhotoToCollectionIfMissing(assetPlantPhotoCollection, ...assetPlantPhotoArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const assetPlantPhoto: IAssetPlantPhoto = sampleWithRequiredData;
        const assetPlantPhoto2: IAssetPlantPhoto = sampleWithPartialData;
        expectedResult = service.addAssetPlantPhotoToCollectionIfMissing([], assetPlantPhoto, assetPlantPhoto2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(assetPlantPhoto);
        expect(expectedResult).toContain(assetPlantPhoto2);
      });

      it('should accept null and undefined values', () => {
        const assetPlantPhoto: IAssetPlantPhoto = sampleWithRequiredData;
        expectedResult = service.addAssetPlantPhotoToCollectionIfMissing([], null, assetPlantPhoto, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(assetPlantPhoto);
      });

      it('should return initial array if no AssetPlantPhoto is added', () => {
        const assetPlantPhotoCollection: IAssetPlantPhoto[] = [sampleWithRequiredData];
        expectedResult = service.addAssetPlantPhotoToCollectionIfMissing(assetPlantPhotoCollection, undefined, null);
        expect(expectedResult).toEqual(assetPlantPhotoCollection);
      });
    });

    describe('compareAssetPlantPhoto', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareAssetPlantPhoto(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 17146 };
        const entity2 = null;

        const compareResult1 = service.compareAssetPlantPhoto(entity1, entity2);
        const compareResult2 = service.compareAssetPlantPhoto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 17146 };
        const entity2 = { id: 1006 };

        const compareResult1 = service.compareAssetPlantPhoto(entity1, entity2);
        const compareResult2 = service.compareAssetPlantPhoto(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 17146 };
        const entity2 = { id: 17146 };

        const compareResult1 = service.compareAssetPlantPhoto(entity1, entity2);
        const compareResult2 = service.compareAssetPlantPhoto(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
