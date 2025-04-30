import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IManufacturerModel } from '../manufacturer-model.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../manufacturer-model.test-samples';

import { ManufacturerModelService } from './manufacturer-model.service';

const requireRestSample: IManufacturerModel = {
  ...sampleWithRequiredData,
};

describe('ManufacturerModel Service', () => {
  let service: ManufacturerModelService;
  let httpMock: HttpTestingController;
  let expectedResult: IManufacturerModel | IManufacturerModel[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(ManufacturerModelService);
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

    it('should create a ManufacturerModel', () => {
      const manufacturerModel = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(manufacturerModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a ManufacturerModel', () => {
      const manufacturerModel = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(manufacturerModel).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a ManufacturerModel', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of ManufacturerModel', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a ManufacturerModel', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addManufacturerModelToCollectionIfMissing', () => {
      it('should add a ManufacturerModel to an empty array', () => {
        const manufacturerModel: IManufacturerModel = sampleWithRequiredData;
        expectedResult = service.addManufacturerModelToCollectionIfMissing([], manufacturerModel);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(manufacturerModel);
      });

      it('should not add a ManufacturerModel to an array that contains it', () => {
        const manufacturerModel: IManufacturerModel = sampleWithRequiredData;
        const manufacturerModelCollection: IManufacturerModel[] = [
          {
            ...manufacturerModel,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addManufacturerModelToCollectionIfMissing(manufacturerModelCollection, manufacturerModel);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a ManufacturerModel to an array that doesn't contain it", () => {
        const manufacturerModel: IManufacturerModel = sampleWithRequiredData;
        const manufacturerModelCollection: IManufacturerModel[] = [sampleWithPartialData];
        expectedResult = service.addManufacturerModelToCollectionIfMissing(manufacturerModelCollection, manufacturerModel);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(manufacturerModel);
      });

      it('should add only unique ManufacturerModel to an array', () => {
        const manufacturerModelArray: IManufacturerModel[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const manufacturerModelCollection: IManufacturerModel[] = [sampleWithRequiredData];
        expectedResult = service.addManufacturerModelToCollectionIfMissing(manufacturerModelCollection, ...manufacturerModelArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const manufacturerModel: IManufacturerModel = sampleWithRequiredData;
        const manufacturerModel2: IManufacturerModel = sampleWithPartialData;
        expectedResult = service.addManufacturerModelToCollectionIfMissing([], manufacturerModel, manufacturerModel2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(manufacturerModel);
        expect(expectedResult).toContain(manufacturerModel2);
      });

      it('should accept null and undefined values', () => {
        const manufacturerModel: IManufacturerModel = sampleWithRequiredData;
        expectedResult = service.addManufacturerModelToCollectionIfMissing([], null, manufacturerModel, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(manufacturerModel);
      });

      it('should return initial array if no ManufacturerModel is added', () => {
        const manufacturerModelCollection: IManufacturerModel[] = [sampleWithRequiredData];
        expectedResult = service.addManufacturerModelToCollectionIfMissing(manufacturerModelCollection, undefined, null);
        expect(expectedResult).toEqual(manufacturerModelCollection);
      });
    });

    describe('compareManufacturerModel', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareManufacturerModel(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 16163 };
        const entity2 = null;

        const compareResult1 = service.compareManufacturerModel(entity1, entity2);
        const compareResult2 = service.compareManufacturerModel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 16163 };
        const entity2 = { id: 4064 };

        const compareResult1 = service.compareManufacturerModel(entity1, entity2);
        const compareResult2 = service.compareManufacturerModel(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 16163 };
        const entity2 = { id: 16163 };

        const compareResult1 = service.compareManufacturerModel(entity1, entity2);
        const compareResult2 = service.compareManufacturerModel(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
