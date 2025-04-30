import { ComponentFixture, TestBed } from '@angular/core/testing';
import { provideRouter, withComponentInputBinding } from '@angular/router';
import { RouterTestingHarness } from '@angular/router/testing';
import { of } from 'rxjs';

import { SiteContractDetailComponent } from './site-contract-detail.component';

describe('SiteContract Management Detail Component', () => {
  let comp: SiteContractDetailComponent;
  let fixture: ComponentFixture<SiteContractDetailComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [SiteContractDetailComponent],
      providers: [
        provideRouter(
          [
            {
              path: '**',
              loadComponent: () => import('./site-contract-detail.component').then(m => m.SiteContractDetailComponent),
              resolve: { siteContract: () => of({ id: 7582 }) },
            },
          ],
          withComponentInputBinding(),
        ),
      ],
    })
      .overrideTemplate(SiteContractDetailComponent, '')
      .compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(SiteContractDetailComponent);
    comp = fixture.componentInstance;
  });

  describe('OnInit', () => {
    it('should load siteContract on init', async () => {
      const harness = await RouterTestingHarness.create();
      const instance = await harness.navigateByUrl('/', SiteContractDetailComponent);

      // THEN
      expect(instance.siteContract()).toEqual(expect.objectContaining({ id: 7582 }));
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
