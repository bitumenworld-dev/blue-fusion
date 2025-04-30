import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { ContractDivisionService } from '../service/contract-division.service';
import { IContractDivision } from '../contract-division.model';
import { ContractDivisionFormService } from './contract-division-form.service';

import { ContractDivisionUpdateComponent } from './contract-division-update.component';

describe('ContractDivision Management Update Component', () => {
  let comp: ContractDivisionUpdateComponent;
  let fixture: ComponentFixture<ContractDivisionUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let contractDivisionFormService: ContractDivisionFormService;
  let contractDivisionService: ContractDivisionService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [ContractDivisionUpdateComponent],
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
      .overrideTemplate(ContractDivisionUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(ContractDivisionUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    contractDivisionFormService = TestBed.inject(ContractDivisionFormService);
    contractDivisionService = TestBed.inject(ContractDivisionService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const contractDivision: IContractDivision = { id: 31405 };

      activatedRoute.data = of({ contractDivision });
      comp.ngOnInit();

      expect(comp.contractDivision).toEqual(contractDivision);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContractDivision>>();
      const contractDivision = { id: 18852 };
      jest.spyOn(contractDivisionFormService, 'getContractDivision').mockReturnValue(contractDivision);
      jest.spyOn(contractDivisionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contractDivision });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contractDivision }));
      saveSubject.complete();

      // THEN
      expect(contractDivisionFormService.getContractDivision).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(contractDivisionService.update).toHaveBeenCalledWith(expect.objectContaining(contractDivision));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContractDivision>>();
      const contractDivision = { id: 18852 };
      jest.spyOn(contractDivisionFormService, 'getContractDivision').mockReturnValue({ id: null });
      jest.spyOn(contractDivisionService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contractDivision: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: contractDivision }));
      saveSubject.complete();

      // THEN
      expect(contractDivisionFormService.getContractDivision).toHaveBeenCalled();
      expect(contractDivisionService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IContractDivision>>();
      const contractDivision = { id: 18852 };
      jest.spyOn(contractDivisionService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ contractDivision });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(contractDivisionService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
