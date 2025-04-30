import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AssetPlantServiceReadingService } from '../service/asset-plant-service-reading.service';
import { IAssetPlantServiceReading } from '../asset-plant-service-reading.model';
import { AssetPlantServiceReadingFormService } from './asset-plant-service-reading-form.service';

import { AssetPlantServiceReadingUpdateComponent } from './asset-plant-service-reading-update.component';

describe('AssetPlantServiceReading Management Update Component', () => {
  let comp: AssetPlantServiceReadingUpdateComponent;
  let fixture: ComponentFixture<AssetPlantServiceReadingUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assetPlantServiceReadingFormService: AssetPlantServiceReadingFormService;
  let assetPlantServiceReadingService: AssetPlantServiceReadingService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AssetPlantServiceReadingUpdateComponent],
      providers: [
        provideHttpClient(),
        FormBuilder,
        {
          provide: ActivatedRoute,
          useValue: {
            params: from([{}]),
          },
        },
      ],
    })
      .overrideTemplate(AssetPlantServiceReadingUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssetPlantServiceReadingUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assetPlantServiceReadingFormService = TestBed.inject(AssetPlantServiceReadingFormService);
    assetPlantServiceReadingService = TestBed.inject(AssetPlantServiceReadingService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const assetPlantServiceReading: IAssetPlantServiceReading = { id: 17261 };

      activatedRoute.data = of({ assetPlantServiceReading });
      comp.ngOnInit();

      expect(comp.assetPlantServiceReading).toEqual(assetPlantServiceReading);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlantServiceReading>>();
      const assetPlantServiceReading = { id: 30847 };
      jest.spyOn(assetPlantServiceReadingFormService, 'getAssetPlantServiceReading').mockReturnValue(assetPlantServiceReading);
      jest.spyOn(assetPlantServiceReadingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlantServiceReading });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assetPlantServiceReading }));
      saveSubject.complete();

      // THEN
      expect(assetPlantServiceReadingFormService.getAssetPlantServiceReading).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assetPlantServiceReadingService.update).toHaveBeenCalledWith(expect.objectContaining(assetPlantServiceReading));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlantServiceReading>>();
      const assetPlantServiceReading = { id: 30847 };
      jest.spyOn(assetPlantServiceReadingFormService, 'getAssetPlantServiceReading').mockReturnValue({ id: null });
      jest.spyOn(assetPlantServiceReadingService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlantServiceReading: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assetPlantServiceReading }));
      saveSubject.complete();

      // THEN
      expect(assetPlantServiceReadingFormService.getAssetPlantServiceReading).toHaveBeenCalled();
      expect(assetPlantServiceReadingService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlantServiceReading>>();
      const assetPlantServiceReading = { id: 30847 };
      jest.spyOn(assetPlantServiceReadingService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlantServiceReading });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assetPlantServiceReadingService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
