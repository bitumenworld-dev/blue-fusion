import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faPlus, faBuilding, faMapMarkerAlt } from '@fortawesome/free-solid-svg-icons';

import SharedModule from 'app/shared/shared.module';
import { ISite } from '../site.model';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'jhi-site-detail',
  templateUrl: './site-detail.component.html',
  imports: [SharedModule, RouterModule, FormsModule, FontAwesomeModule],
})
export class SiteDetailComponent {
  site = input<ISite | null>(null);

  constructor(library: FaIconLibrary) {
    // Add both icons you want to use
    library.addIcons(faPlus, faBuilding, faMapMarkerAlt);
  }

  previousState(): void {
    window.history.back();
  }
}
