import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { SiteContractService } from '../service/site-contract.service';
import { ISiteContract } from '../site-contract.model';
import { SiteContractFormService } from './site-contract-form.service';

import { SiteContractUpdateComponent } from './site-contract-update.component';

describe('SiteContract Management Update Component', () => {
  let comp: SiteContractUpdateComponent;
  let fixture: ComponentFixture<SiteContractUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let siteContractFormService: SiteContractFormService;
  let siteContractService: SiteContractService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [SiteContractUpdateComponent],
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
      .overrideTemplate(SiteContractUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(SiteContractUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    siteContractFormService = TestBed.inject(SiteContractFormService);
    siteContractService = TestBed.inject(SiteContractService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const siteContract: ISiteContract = { id: 17978 };

      activatedRoute.data = of({ siteContract });
      comp.ngOnInit();

      expect(comp.siteContract).toEqual(siteContract);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISiteContract>>();
      const siteContract = { id: 7582 };
      jest.spyOn(siteContractFormService, 'getSiteContract').mockReturnValue(siteContract);
      jest.spyOn(siteContractService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteContract });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteContract }));
      saveSubject.complete();

      // THEN
      expect(siteContractFormService.getSiteContract).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(siteContractService.update).toHaveBeenCalledWith(expect.objectContaining(siteContract));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISiteContract>>();
      const siteContract = { id: 7582 };
      jest.spyOn(siteContractFormService, 'getSiteContract').mockReturnValue({ id: null });
      jest.spyOn(siteContractService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteContract: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: siteContract }));
      saveSubject.complete();

      // THEN
      expect(siteContractFormService.getSiteContract).toHaveBeenCalled();
      expect(siteContractService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<ISiteContract>>();
      const siteContract = { id: 7582 };
      jest.spyOn(siteContractService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ siteContract });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(siteContractService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
