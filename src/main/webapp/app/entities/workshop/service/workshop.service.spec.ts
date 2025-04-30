import { TestBed } from '@angular/core/testing';
import { HttpTestingController, provideHttpClientTesting } from '@angular/common/http/testing';
import { provideHttpClient } from '@angular/common/http';

import { IWorkshop } from '../workshop.model';
import { sampleWithFullData, sampleWithNewData, sampleWithPartialData, sampleWithRequiredData } from '../workshop.test-samples';

import { WorkshopService } from './workshop.service';

const requireRestSample: IWorkshop = {
  ...sampleWithRequiredData,
};

describe('Workshop Service', () => {
  let service: WorkshopService;
  let httpMock: HttpTestingController;
  let expectedResult: IWorkshop | IWorkshop[] | boolean | null;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [provideHttpClient(), provideHttpClientTesting()],
    });
    expectedResult = null;
    service = TestBed.inject(WorkshopService);
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

    it('should create a Workshop', () => {
      const workshop = { ...sampleWithNewData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.create(workshop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'POST' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should update a Workshop', () => {
      const workshop = { ...sampleWithRequiredData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.update(workshop).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PUT' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should partial update a Workshop', () => {
      const patchObject = { ...sampleWithPartialData };
      const returnedFromService = { ...requireRestSample };
      const expected = { ...sampleWithRequiredData };

      service.partialUpdate(patchObject).subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'PATCH' });
      req.flush(returnedFromService);
      expect(expectedResult).toMatchObject(expected);
    });

    it('should return a list of Workshop', () => {
      const returnedFromService = { ...requireRestSample };

      const expected = { ...sampleWithRequiredData };

      service.query().subscribe(resp => (expectedResult = resp.body));

      const req = httpMock.expectOne({ method: 'GET' });
      req.flush([returnedFromService]);
      httpMock.verify();
      expect(expectedResult).toMatchObject([expected]);
    });

    it('should delete a Workshop', () => {
      const expected = true;

      service.delete(123).subscribe(resp => (expectedResult = resp.ok));

      const req = httpMock.expectOne({ method: 'DELETE' });
      req.flush({ status: 200 });
      expect(expectedResult).toBe(expected);
    });

    describe('addWorkshopToCollectionIfMissing', () => {
      it('should add a Workshop to an empty array', () => {
        const workshop: IWorkshop = sampleWithRequiredData;
        expectedResult = service.addWorkshopToCollectionIfMissing([], workshop);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workshop);
      });

      it('should not add a Workshop to an array that contains it', () => {
        const workshop: IWorkshop = sampleWithRequiredData;
        const workshopCollection: IWorkshop[] = [
          {
            ...workshop,
          },
          sampleWithPartialData,
        ];
        expectedResult = service.addWorkshopToCollectionIfMissing(workshopCollection, workshop);
        expect(expectedResult).toHaveLength(2);
      });

      it("should add a Workshop to an array that doesn't contain it", () => {
        const workshop: IWorkshop = sampleWithRequiredData;
        const workshopCollection: IWorkshop[] = [sampleWithPartialData];
        expectedResult = service.addWorkshopToCollectionIfMissing(workshopCollection, workshop);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workshop);
      });

      it('should add only unique Workshop to an array', () => {
        const workshopArray: IWorkshop[] = [sampleWithRequiredData, sampleWithPartialData, sampleWithFullData];
        const workshopCollection: IWorkshop[] = [sampleWithRequiredData];
        expectedResult = service.addWorkshopToCollectionIfMissing(workshopCollection, ...workshopArray);
        expect(expectedResult).toHaveLength(3);
      });

      it('should accept varargs', () => {
        const workshop: IWorkshop = sampleWithRequiredData;
        const workshop2: IWorkshop = sampleWithPartialData;
        expectedResult = service.addWorkshopToCollectionIfMissing([], workshop, workshop2);
        expect(expectedResult).toHaveLength(2);
        expect(expectedResult).toContain(workshop);
        expect(expectedResult).toContain(workshop2);
      });

      it('should accept null and undefined values', () => {
        const workshop: IWorkshop = sampleWithRequiredData;
        expectedResult = service.addWorkshopToCollectionIfMissing([], null, workshop, undefined);
        expect(expectedResult).toHaveLength(1);
        expect(expectedResult).toContain(workshop);
      });

      it('should return initial array if no Workshop is added', () => {
        const workshopCollection: IWorkshop[] = [sampleWithRequiredData];
        expectedResult = service.addWorkshopToCollectionIfMissing(workshopCollection, undefined, null);
        expect(expectedResult).toEqual(workshopCollection);
      });
    });

    describe('compareWorkshop', () => {
      it('should return true if both entities are null', () => {
        const entity1 = null;
        const entity2 = null;

        const compareResult = service.compareWorkshop(entity1, entity2);

        expect(compareResult).toEqual(true);
      });

      it('should return false if one entity is null', () => {
        const entity1 = { id: 13709 };
        const entity2 = null;

        const compareResult1 = service.compareWorkshop(entity1, entity2);
        const compareResult2 = service.compareWorkshop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey differs', () => {
        const entity1 = { id: 13709 };
        const entity2 = { id: 28378 };

        const compareResult1 = service.compareWorkshop(entity1, entity2);
        const compareResult2 = service.compareWorkshop(entity2, entity1);

        expect(compareResult1).toEqual(false);
        expect(compareResult2).toEqual(false);
      });

      it('should return false if primaryKey matches', () => {
        const entity1 = { id: 13709 };
        const entity2 = { id: 13709 };

        const compareResult1 = service.compareWorkshop(entity1, entity2);
        const compareResult2 = service.compareWorkshop(entity2, entity1);

        expect(compareResult1).toEqual(true);
        expect(compareResult2).toEqual(true);
      });
    });
  });

  afterEach(() => {
    httpMock.verify();
  });
});
