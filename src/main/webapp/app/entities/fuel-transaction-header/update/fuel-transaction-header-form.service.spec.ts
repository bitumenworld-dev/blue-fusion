import { TestBed } from '@angular/core/testing';

import { sampleWithNewData, sampleWithRequiredData } from '../fuel-transaction-header.test-samples';

import { FuelTransactionHeaderFormService } from './fuel-transaction-header-form.service';

describe('FuelTransactionHeader Form Service', () => {
  let service: FuelTransactionHeaderFormService;

  beforeEach(() => {
    TestBed.configureTestingModule({});
    service = TestBed.inject(FuelTransactionHeaderFormService);
  });

  describe('Service methods', () => {
    describe('createFuelTransactionHeaderFormGroup', () => {
      it('should create a new form with FormControl', () => {
        const formGroup = service.createFuelTransactionHeaderFormGroup();

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelTransactionHeaderId: expect.any(Object),
            companyId: expect.any(Object),
            supplierId: expect.any(Object),
            transactionTypeId: expect.any(Object),
            fuelType: expect.any(Object),
            orderNumber: expect.any(Object),
            deliveryNote: expect.any(Object),
            grvNumber: expect.any(Object),
            invoiceNumber: expect.any(Object),
            pricePerLitre: expect.any(Object),
            note: expect.any(Object),
            registrationNumber: expect.any(Object),
            attendeeId: expect.any(Object),
            operatorId: expect.any(Object),
          }),
        );
      });

      it('passing IFuelTransactionHeader should create a new form with FormGroup', () => {
        const formGroup = service.createFuelTransactionHeaderFormGroup(sampleWithRequiredData);

        expect(formGroup.controls).toEqual(
          expect.objectContaining({
            id: expect.any(Object),
            fuelTransactionHeaderId: expect.any(Object),
            companyId: expect.any(Object),
            supplierId: expect.any(Object),
            transactionTypeId: expect.any(Object),
            fuelType: expect.any(Object),
            orderNumber: expect.any(Object),
            deliveryNote: expect.any(Object),
            grvNumber: expect.any(Object),
            invoiceNumber: expect.any(Object),
            pricePerLitre: expect.any(Object),
            note: expect.any(Object),
            registrationNumber: expect.any(Object),
            attendeeId: expect.any(Object),
            operatorId: expect.any(Object),
          }),
        );
      });
    });

    describe('getFuelTransactionHeader', () => {
      it('should return NewFuelTransactionHeader for default FuelTransactionHeader initial value', () => {
        const formGroup = service.createFuelTransactionHeaderFormGroup(sampleWithNewData);

        const fuelTransactionHeader = service.getFuelTransactionHeader(formGroup) as any;

        expect(fuelTransactionHeader).toMatchObject(sampleWithNewData);
      });

      it('should return NewFuelTransactionHeader for empty FuelTransactionHeader initial value', () => {
        const formGroup = service.createFuelTransactionHeaderFormGroup();

        const fuelTransactionHeader = service.getFuelTransactionHeader(formGroup) as any;

        expect(fuelTransactionHeader).toMatchObject({});
      });

      it('should return IFuelTransactionHeader', () => {
        const formGroup = service.createFuelTransactionHeaderFormGroup(sampleWithRequiredData);

        const fuelTransactionHeader = service.getFuelTransactionHeader(formGroup) as any;

        expect(fuelTransactionHeader).toMatchObject(sampleWithRequiredData);
      });
    });

    describe('resetForm', () => {
      it('passing IFuelTransactionHeader should not enable id FormControl', () => {
        const formGroup = service.createFuelTransactionHeaderFormGroup();
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, sampleWithRequiredData);

        expect(formGroup.controls.id.disabled).toBe(true);
      });

      it('passing NewFuelTransactionHeader should disable id FormControl', () => {
        const formGroup = service.createFuelTransactionHeaderFormGroup(sampleWithRequiredData);
        expect(formGroup.controls.id.disabled).toBe(true);

        service.resetForm(formGroup, { id: null });

        expect(formGroup.controls.id.disabled).toBe(true);
      });
    });
  });
});
