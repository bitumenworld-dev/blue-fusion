jest.mock('@ng-bootstrap/ng-bootstrap');

import { ComponentFixture, TestBed, fakeAsync, inject, tick } from '@angular/core/testing';
import { HttpResponse, provideHttpClient } from '@angular/common/http';
import { of } from 'rxjs';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';

import { AssetPlantServiceReadingService } from '../service/asset-plant-service-reading.service';

import { AssetPlantServiceReadingDeleteDialogComponent } from './asset-plant-service-reading-delete-dialog.component';

describe('AssetPlantServiceReading Management Delete Component', () => {
  let comp: AssetPlantServiceReadingDeleteDialogComponent;
  let fixture: ComponentFixture<AssetPlantServiceReadingDeleteDialogComponent>;
  let service: AssetPlantServiceReadingService;
  let mockActiveModal: NgbActiveModal;

  beforeEach(() => {
    TestBed.configureTestingModule({
      imports: [AssetPlantServiceReadingDeleteDialogComponent],
      providers: [provideHttpClient(), NgbActiveModal],
    })
      .overrideTemplate(AssetPlantServiceReadingDeleteDialogComponent, '')
      .compileComponents();
    fixture = TestBed.createComponent(AssetPlantServiceReadingDeleteDialogComponent);
    comp = fixture.componentInstance;
    service = TestBed.inject(AssetPlantServiceReadingService);
    mockActiveModal = TestBed.inject(NgbActiveModal);
  });

  describe('confirmDelete', () => {
    it('should call delete service on confirmDelete', inject(
      [],
      fakeAsync(() => {
        // GIVEN
        jest.spyOn(service, 'delete').mockReturnValue(of(new HttpResponse({ body: {} })));

        // WHEN
        comp.confirmDelete(123);
        tick();

        // THEN
        expect(service.delete).toHaveBeenCalledWith(123);
        expect(mockActiveModal.close).toHaveBeenCalledWith('deleted');
      }),
    ));

    it('should not call delete service on clear', () => {
      // GIVEN
      jest.spyOn(service, 'delete');

      // WHEN
      comp.cancel();

      // THEN
      expect(service.delete).not.toHaveBeenCalled();
      expect(mockActiveModal.close).not.toHaveBeenCalled();
      expect(mockActiveModal.dismiss).toHaveBeenCalled();
    });
  });
});
