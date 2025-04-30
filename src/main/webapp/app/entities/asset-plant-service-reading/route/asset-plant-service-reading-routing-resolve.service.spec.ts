import { TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { ActivatedRoute, ActivatedRouteSnapshot, Router, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';

import { IAssetPlantServiceReading } from '../asset-plant-service-reading.model';
import { AssetPlantServiceReadingService } from '../service/asset-plant-service-reading.service';

import assetPlantServiceReadingResolve from './asset-plant-service-reading-routing-resolve.service';

describe('AssetPlantServiceReading routing resolve service', () => {
  let mockRouter: Router;
  let mockActivatedRouteSnapshot: ActivatedRouteSnapshot;
  let service: AssetPlantServiceReadingService;
  let resultAssetPlantServiceReading: IAssetPlantServiceReading | null | undefined;

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
    service = TestBed.inject(AssetPlantServiceReadingService);
    resultAssetPlantServiceReading = undefined;
  });

  describe('resolve', () => {
    it('should return IAssetPlantServiceReading returned by find', () => {
      // GIVEN
      service.find = jest.fn(id => of(new HttpResponse({ body: { id } })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        assetPlantServiceReadingResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAssetPlantServiceReading = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAssetPlantServiceReading).toEqual({ id: 123 });
    });

    it('should return null if id is not provided', () => {
      // GIVEN
      service.find = jest.fn();
      mockActivatedRouteSnapshot.params = {};

      // WHEN
      TestBed.runInInjectionContext(() => {
        assetPlantServiceReadingResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAssetPlantServiceReading = result;
          },
        });
      });

      // THEN
      expect(service.find).not.toHaveBeenCalled();
      expect(resultAssetPlantServiceReading).toEqual(null);
    });

    it('should route to 404 page if data not found in server', () => {
      // GIVEN
      jest.spyOn(service, 'find').mockReturnValue(of(new HttpResponse<IAssetPlantServiceReading>({ body: null })));
      mockActivatedRouteSnapshot.params = { id: 123 };

      // WHEN
      TestBed.runInInjectionContext(() => {
        assetPlantServiceReadingResolve(mockActivatedRouteSnapshot).subscribe({
          next(result) {
            resultAssetPlantServiceReading = result;
          },
        });
      });

      // THEN
      expect(service.find).toHaveBeenCalledWith(123);
      expect(resultAssetPlantServiceReading).toEqual(undefined);
      expect(mockRouter.navigate).toHaveBeenCalledWith(['404']);
    });
  });
});
