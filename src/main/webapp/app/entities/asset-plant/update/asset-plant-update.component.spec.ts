import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { AssetPlantService } from '../service/asset-plant.service';
import { IAssetPlant } from '../asset-plant.model';
import { AssetPlantFormService } from './asset-plant-form.service';

import { AssetPlantUpdateComponent } from './asset-plant-update.component';

describe('AssetPlant Management Update Component', () => {
  let comp: AssetPlantUpdateComponent;
  let fixture: ComponentFixture<AssetPlantUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let assetPlantFormService: AssetPlantFormService;
  let assetPlantService: AssetPlantService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AssetPlantUpdateComponent],
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
      .overrideTemplate(AssetPlantUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(AssetPlantUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    assetPlantFormService = TestBed.inject(AssetPlantFormService);
    assetPlantService = TestBed.inject(AssetPlantService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const assetPlant: IAssetPlant = { id: 2183 };

      activatedRoute.data = of({ assetPlant });
      comp.ngOnInit();

      expect(comp.assetPlant).toEqual(assetPlant);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlant>>();
      const assetPlant = { id: 18137 };
      jest.spyOn(assetPlantFormService, 'getAssetPlant').mockReturnValue(assetPlant);
      jest.spyOn(assetPlantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assetPlant }));
      saveSubject.complete();

      // THEN
      expect(assetPlantFormService.getAssetPlant).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(assetPlantService.update).toHaveBeenCalledWith(expect.objectContaining(assetPlant));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlant>>();
      const assetPlant = { id: 18137 };
      jest.spyOn(assetPlantFormService, 'getAssetPlant').mockReturnValue({ id: null });
      jest.spyOn(assetPlantService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlant: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: assetPlant }));
      saveSubject.complete();

      // THEN
      expect(assetPlantFormService.getAssetPlant).toHaveBeenCalled();
      expect(assetPlantService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IAssetPlant>>();
      const assetPlant = { id: 18137 };
      jest.spyOn(assetPlantService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ assetPlant });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(assetPlantService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
