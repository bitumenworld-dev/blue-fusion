import { ComponentFixture, TestBed } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { FormBuilder } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Subject, from, of } from 'rxjs';

import { WorkshopService } from '../service/workshop.service';
import { IWorkshop } from '../workshop.model';
import { WorkshopFormService } from './workshop-form.service';

import { WorkshopUpdateComponent } from './workshop-update.component';

describe('Workshop Management Update Component', () => {
  let comp: WorkshopUpdateComponent;
  let fixture: ComponentFixture<WorkshopUpdateComponent>;
  let activatedRoute: ActivatedRoute;
  let workshopFormService: WorkshopFormService;
  let workshopService: WorkshopService;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [WorkshopUpdateComponent],
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
      .overrideTemplate(WorkshopUpdateComponent, '')
      .compileComponents();

    fixture = TestBed.createComponent(WorkshopUpdateComponent);
    activatedRoute = TestBed.inject(ActivatedRoute);
    workshopFormService = TestBed.inject(WorkshopFormService);
    workshopService = TestBed.inject(WorkshopService);

    comp = fixture.componentInstance;
  });

  describe('ngOnInit', () => {
    it('should update editForm', () => {
      const workshop: IWorkshop = { id: 28378 };

      activatedRoute.data = of({ workshop });
      comp.ngOnInit();

      expect(comp.workshop).toEqual(workshop);
    });
  });

  describe('save', () => {
    it('should call update service on save for existing entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkshop>>();
      const workshop = { id: 13709 };
      jest.spyOn(workshopFormService, 'getWorkshop').mockReturnValue(workshop);
      jest.spyOn(workshopService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workshop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workshop }));
      saveSubject.complete();

      // THEN
      expect(workshopFormService.getWorkshop).toHaveBeenCalled();
      expect(comp.previousState).toHaveBeenCalled();
      expect(workshopService.update).toHaveBeenCalledWith(expect.objectContaining(workshop));
      expect(comp.isSaving).toEqual(false);
    });

    it('should call create service on save for new entity', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkshop>>();
      const workshop = { id: 13709 };
      jest.spyOn(workshopFormService, 'getWorkshop').mockReturnValue({ id: null });
      jest.spyOn(workshopService, 'create').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workshop: null });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.next(new HttpResponse({ body: workshop }));
      saveSubject.complete();

      // THEN
      expect(workshopFormService.getWorkshop).toHaveBeenCalled();
      expect(workshopService.create).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).toHaveBeenCalled();
    });

    it('should set isSaving to false on error', () => {
      // GIVEN
      const saveSubject = new Subject<HttpResponse<IWorkshop>>();
      const workshop = { id: 13709 };
      jest.spyOn(workshopService, 'update').mockReturnValue(saveSubject);
      jest.spyOn(comp, 'previousState');
      activatedRoute.data = of({ workshop });
      comp.ngOnInit();

      // WHEN
      comp.save();
      expect(comp.isSaving).toEqual(true);
      saveSubject.error('This is an error!');

      // THEN
      expect(workshopService.update).toHaveBeenCalled();
      expect(comp.isSaving).toEqual(false);
      expect(comp.previousState).not.toHaveBeenCalled();
    });
  });
});
