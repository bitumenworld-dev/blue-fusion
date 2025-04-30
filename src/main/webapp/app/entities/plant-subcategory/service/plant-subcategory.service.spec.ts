import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPlantSubcategory } from '../plant-subcategory.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../plant-subcategory.test-samples';

import { PlantSubcategoryService } from './plant-subcategory.service';

const requireRestSample: IPlantSubcategory = {
  ...sampleWithRequiredData,
};

describe('PlantSubcategory Service', () => {
  let service: PlantSubcategoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlantSubcategory | IPlantSubcategory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PlantSubcategoryService);
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

    it('should create a PlantSubcategory', () => {
      const plantSubcategory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(plantSubcategory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlantSubcategory', () => {
      const plantSubcategory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(plantSubcategory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlantSubcategory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlantSubcategory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlantSubcategory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlantSubcategoryToCollectionIfMissing', () => {
      it('should add a PlantSubcategory to an empty array', () => {
        const plantSubcategory: IPlantSubcategory = sampleWithRequiredData;
        expectedResult = service.addPlantSubcategoryToCollectionIfMissing([], plantSubcategory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantSubcategory);
      });

      it('should not add a PlantSubcategory to an array that contains it', () => {
        const plantSubcategory: IPlantSubcategory = sampleWithRequiredData;
        const plantSubcategoryCollection: IPlantSubcategory[] = [
          {
            ...plantSubcategory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlantSubcategoryToCollectionIfMissing(plantSubcategoryCollection, plantSubcategory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlantSubcategory to an array that doesn't contain it", () => {
        const plantSubcategory: IPlantSubcategory = sampleWithRequiredData;
        const plantSubcategoryCollection: IPlantSubcategory[] = [sampleWithPartialData];
        expectedResult = service.addPlantSubcategoryToCollectionIfMissing(plantSubcategoryCollection, plantSubcategory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantSubcategory);
      });

      it('should add only unique PlantSubcategory to an array', () => {
        const plantSubcategoryArray: IPlantSubcategory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const plantSubcategoryCollection: IPlantSubcategory[] = [sampleWithRequiredData];
        expectedResult = service.addPlantSubcategoryToCollectionIfMissing(plantSubcategoryCollection, ...plantSubcategoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plantSubcategory: IPlantSubcategory = sampleWithRequiredData;
        const plantSubcategory2: IPlantSubcategory = sampleWithPartialData;
        expectedResult = service.addPlantSubcategoryToCollectionIfMissing([], plantSubcategory, plantSubcategory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantSubcategory);
        expect(expectedResult).toContain(plantSubcategory2);
      });

      it('should accept null and undefined values', () => {
        const plantSubcategory: IPlantSubcategory = sampleWithRequiredData;
        expectedResult = service.addPlantSubcategoryToCollectionIfMissing([], null, plantSubcategory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantSubcategory);
      });

      it('should return initial array if no PlantSubcategory is added', () => {
        const plantSubcategoryCollection: IPlantSubcategory[] = [sampleWithRequiredData];
        expectedResult = service.addPlantSubcategoryToCollectionIfMissing(plantSubcategoryCollection, undefined, null);
        expect(expectedResult).toEqual(plantSubcategoryCollection);
      });
    });

    describe('comparePlantSubcategory', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlantSubcategory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 10167 };
        const entity2 = null;

        const compareResult1 = service.comparePlantSubcategory(entity1, entity2);
        const compareResult2 = service.comparePlantSubcategory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 10167 };
        const entity2 = { id: 13792 };

        const compareResult1 = service.comparePlantSubcategory(entity1, entity2);
        const compareResult2 = service.comparePlantSubcategory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 10167 };
        const entity2 = { id: 10167 };

        const compareResult1 = service.comparePlantSubcategory(entity1, entity2);
        const compareResult2 = service.comparePlantSubcategory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
