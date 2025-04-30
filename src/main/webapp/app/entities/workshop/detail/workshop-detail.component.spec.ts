import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { WorkshopDetailComponent } from './workshop-detail.component';

describe('Workshop Management Detail Component', () => {
  let comp: WorkshopDetailComponent;
  let fixture: ComponentFixture<WorkshopDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [WorkshopDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./workshop-detail.component').then(m => m.WorkshopDetailComponent),
              resolve: { workshop: () => of({ id: 13709 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(WorkshopDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(WorkshopDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load workshop on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', WorkshopDetailComponent);

      // THEN
      expect(instance.workshop()).toEqual(expect.objectContaining({ id: 13709 }));
    });
  });

  describe('PreviousState', () => {
    it('should navigate to previous state', () => {
      jest.spyOn(window.history, 'back');
      comp.previousState();
      expect(window.history.back).toHaveBeenCalled();
    });
  });
});
