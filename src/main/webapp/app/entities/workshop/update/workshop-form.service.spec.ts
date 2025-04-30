import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../workshop.test-samples';

import { WorkshopFormService } from './workshop-form.service';

describe('Workshop Form Service', () => {
  let service: WorkshopFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(WorkshopFormService);
  });

  describe('Service methods', () => {
    describe('createWorkshopFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createWorkshopFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            workshopId: expect.any(Object),
            siteId: expect.any(Object),
            workshopName: expect.any(Object),
          }),
        );
      });

      it('passing IWorkshop should create a new form with FormGroup', () => {
        const formGroup = service.createWorkshopFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            workshopId: expect.any(Object),
            siteId: expect.any(Object),
            workshopName: expect.any(Object),
          }),
        );
      });
    });

    describe('getWorkshop', () => {
      it('should return NewWorkshop for default Workshop initial value', () => {
        const formGroup = service.createWorkshopFormGroup(sampleWithNewData);

        const workshop = service.getWorkshop(formGroup) as any;

        expect(workshop).toMatchObject(sampleWithNewData);
      });

      it('should return NewWorkshop for empty Workshop initial value', () => {
        const formGroup = service.createWorkshopFormGroup();

        const workshop = service.getWorkshop(formGroup) as any;

        expect(workshop).toMatchObject({});
      });

      it('should return IWorkshop', () => {
        const formGroup = service.createWorkshopFormGroup(sampleWithRequiredData);

        const workshop = service.getWorkshop(formGroup) as any;

        expect(workshop).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IWorkshop should not enable id FormControl', () => {
        const formGroup = service.createWorkshopFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewWorkshop should disable id FormControl', () => {
        const formGroup = service.createWorkshopFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
