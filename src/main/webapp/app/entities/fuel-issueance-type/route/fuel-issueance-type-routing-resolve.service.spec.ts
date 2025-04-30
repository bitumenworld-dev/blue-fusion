import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IFuelIssueanceType } from '../fuel-issueance-type.model';
import { FuelIssueanceTypeService } from '../service/fuel-issueance-type.service';

import fuelIssueanceTypeResolve from './fuel-issueance-type-routing-resolve.service';

describe('FuelIssueanceType routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: FuelIssueanceTypeService;
  let resultFuelIssueanceType: IFuelIssueanceType | null | undefined;

  beforeEach(() => {
    TestBed.configureTestingModule({
      providers: [
        provideHttpClient(),
        {
          provide: ActivatedRoute,
          useValue: {
            snapshot: {
              paramMap: convertToParamMap({}),
            },
          },
        },
      ],
    });
    mockRouter = TestBed.inject(Router);
    jest.spyOn(mockRouter, 'navigate').mockImplementation(() => Promise.resolve(true));
    mockActivatedRouteSnapshot = TestBed.inject(ActivatedRoute).snapshot;
    service = TestBed.inject(FuelIssueanceTypeService);
    resultFuelIssueanceType = undefined;
  });

  describe('resolve', () => {
    it('should return IFuelIssueanceType returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        fuelIssueanceTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFuelIssueanceType = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFuelIssueanceType).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        fuelIssueanceTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFuelIssueanceType = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultFuelIssueanceType).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IFuelIssueanceType>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        fuelIssueanceTypeResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultFuelIssueanceType = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultFuelIssueanceType).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
