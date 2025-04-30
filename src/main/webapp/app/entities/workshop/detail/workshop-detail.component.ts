import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';

import SharedModule from 'app/shared/shared.module';
import { IWorkshop } from '../workshop.model';

@Component({
  selector: 'jhi-workshop-detail',
  templateUrl: './workshop-detail.component.html',
  imports: [SharedModule, RouterModule],
})
export class WorkshopDetailComponent {
  workshop = input<IWorkshop | null>(null);

  previousState(): void {
    window.history.back();
  }
}
