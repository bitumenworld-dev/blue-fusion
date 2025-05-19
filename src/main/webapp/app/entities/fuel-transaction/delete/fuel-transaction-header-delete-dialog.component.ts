// import { Component, inject } from '@angular/core';
// import { FormsModule } from '@angular/forms';
// import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
//
// import SharedModule from 'app/shared/shared.module';
// import { ITEM_DELETED_EVENT } from 'app/config/navigation.constants';
// import { FuelTransaction } from '../fuel-transaction.model';
// import { FuelTransactionService } from '../service/fuel-transaction.service';
//
// @Component({
//   templateUrl: './fuel-transaction-header-delete-dialog.component.html',
//   imports: [SharedModule, FormsModule],
// })
// export class FuelTransactionHeaderDeleteDialogComponent {
//   fuelTransaction?: FuelTransaction;
//
//   protected fuelTransactionHeaderService = inject(FuelTransactionService);
//   protected activeModal = inject(NgbActiveModal);
//
//   cancel(): void {
//     this.activeModal.dismiss();
//   }
//
//   confirmDelete(id: number): void {
//     this.fuelTransactionHeaderService.delete(id).subscribe(() => {
//       this.activeModal.close(ITEM_DELETED_EVENT);
//     });
//   }
// }
