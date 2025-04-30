import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AssetPlantPhotoService } from '../service/asset-plant-photo.service';
import { IAssetPlantPhoto } from '../asset-plant-photo.model';
import { AssetPlantPhotoFormService } from './asset-plant-photo-form.service';

import { AssetPlantPhotoUpdateComponent } from './asset-plant-photo-update.component';

describe('AssetPlantPhoto Management Update Component', () => {
  let comp: AssetPlantPhotoUpdateComponent;
  let fixture: ComponentFixture<AssetPlantPhotoUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assetPlantPhotoFormService: AssetPlantPhotoFormService;
  let assetPlantPhotoService: AssetPlantPhotoService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AssetPlantPhotoUpdateComponent],
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
      .overrideTemplate(AssetPlantPhotoUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssetPlantPhotoUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assetPlantPhotoFormService = TestBed.inject(AssetPlantPhotoFormService);
    assetPlantPhotoService = TestBed.inject(AssetPlantPhotoService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const assetPlantPhoto: IAssetPlantPhoto = { id: 1006 };

      activatedRoute.data = of({ assetPlantPhoto });
      comp.ngOnInit();

      expect(comp.assetPlantPhoto).toEqual(assetPlantPhoto);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlantPhoto>>();
      const assetPlantPhoto = { id: 17146 };
      jest.spyOn(assetPlantPhotoFormService, 'getAssetPlantPhoto').mockReturnValue(assetPlantPhoto);
      jest.spyOn(assetPlantPhotoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlantPhoto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assetPlantPhoto }));
      saveSubject.complete();

      // THEN
      expect(assetPlantPhotoFormService.getAssetPlantPhoto).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assetPlantPhotoService.update).toHaveBeenCalledWith(expect.objectContaining(assetPlantPhoto));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlantPhoto>>();
      const assetPlantPhoto = { id: 17146 };
      jest.spyOn(assetPlantPhotoFormService, 'getAssetPlantPhoto').mockReturnValue({ id: null });
      jest.spyOn(assetPlantPhotoService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlantPhoto: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assetPlantPhoto }));
      saveSubject.complete();

      // THEN
      expect(assetPlantPhotoFormService.getAssetPlantPhoto).toHaveBeenCalled();
      expect(assetPlantPhotoService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlantPhoto>>();
      const assetPlantPhoto = { id: 17146 };
      jest.spyOn(assetPlantPhotoService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlantPhoto });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assetPlantPhotoService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
