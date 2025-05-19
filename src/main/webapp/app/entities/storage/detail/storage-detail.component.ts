import { Component, input } from '@angular/core';
import { RouterModule } from '@angular/router';
import { FontAwesomeModule, FaIconLibrary } from '@fortawesome/angular-fontawesome';
import { faPlus, faBuilding, faMapMarkerAlt } from '@fortawesome/free-solid-svg-icons';

import SharedModule from 'app/shared/shared.module';
import { Storage } from '../storage.model';
import { FormsModule } from '@angular/forms';

@Component({
  templateUrl: './storage-detail.component.html',
  imports: [SharedModule, RouterModule, FormsModule, FontAwesomeModule],
})
export class StorageDetailComponent {
  storage = input<Storage | null>(null);

  constructor(library: FaIconLibrary) {
    // Add both icons you want to use
    library.addIcons(faPlus, faBuilding, faMapMarkerAlt);
  }

  previousState(): void {
    window.history.back();
  }
}
