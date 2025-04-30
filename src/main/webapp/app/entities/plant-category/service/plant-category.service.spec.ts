import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IPlantCategory } from '../plant-category.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../plant-category.test-samples';

import { PlantCategoryService } from './plant-category.service';

const requireRestSample: IPlantCategory = {
  ...sampleWithRequiredData,
};

describe('PlantCategory Service', () => {
  let service: PlantCategoryService;
  let httpMock: HttpTestingController;
  let expectedResult: IPlantCategory | IPlantCategory[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(PlantCategoryService);
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

    it('should create a PlantCategory', () => {
      const plantCategory = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(plantCategory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a PlantCategory', () => {
      const plantCategory = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(plantCategory).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a PlantCategory', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of PlantCategory', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a PlantCategory', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addPlantCategoryToCollectionIfMissing', () => {
      it('should add a PlantCategory to an empty array', () => {
        const plantCategory: IPlantCategory = sampleWithRequiredData;
        expectedResult = service.addPlantCategoryToCollectionIfMissing([], plantCategory);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantCategory);
      });

      it('should not add a PlantCategory to an array that contains it', () => {
        const plantCategory: IPlantCategory = sampleWithRequiredData;
        const plantCategoryCollection: IPlantCategory[] = [
          {
            ...plantCategory,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addPlantCategoryToCollectionIfMissing(plantCategoryCollection, plantCategory);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a PlantCategory to an array that doesn't contain it", () => {
        const plantCategory: IPlantCategory = sampleWithRequiredData;
        const plantCategoryCollection: IPlantCategory[] = [sampleWithPartialData];
        expectedResult = service.addPlantCategoryToCollectionIfMissing(plantCategoryCollection, plantCategory);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantCategory);
      });

      it('should add only unique PlantCategory to an array', () => {
        const plantCategoryArray: IPlantCategory[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const plantCategoryCollection: IPlantCategory[] = [sampleWithRequiredData];
        expectedResult = service.addPlantCategoryToCollectionIfMissing(plantCategoryCollection, ...plantCategoryArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const plantCategory: IPlantCategory = sampleWithRequiredData;
        const plantCategory2: IPlantCategory = sampleWithPartialData;
        expectedResult = service.addPlantCategoryToCollectionIfMissing([], plantCategory, plantCategory2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(plantCategory);
        expect(expectedResult).toContain(plantCategory2);
      });

      it('should accept null and undefined values', () => {
        const plantCategory: IPlantCategory = sampleWithRequiredData;
        expectedResult = service.addPlantCategoryToCollectionIfMissing([], null, plantCategory, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(plantCategory);
      });

      it('should return initial array if no PlantCategory is added', () => {
        const plantCategoryCollection: IPlantCategory[] = [sampleWithRequiredData];
        expectedResult = service.addPlantCategoryToCollectionIfMissing(plantCategoryCollection, undefined, null);
        expect(expectedResult).toEqual(plantCategoryCollection);
      });
    });

    describe('comparePlantCategory', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.comparePlantCategory(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 7148 };
        const entity2 = null;

        const compareResult1 = service.comparePlantCategory(entity1, entity2);
        const compareResult2 = service.comparePlantCategory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 7148 };
        const entity2 = { id: 13748 };

        const compareResult1 = service.comparePlantCategory(entity1, entity2);
        const compareResult2 = service.comparePlantCategory(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 7148 };
        const entity2 = { id: 7148 };

        const compareResult1 = service.comparePlantCategory(entity1, entity2);
        const compareResult2 = service.comparePlantCategory(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
