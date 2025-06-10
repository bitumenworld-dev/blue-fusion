package com.bitumen.bluefusion.service.fuelTransaction;

import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import com.bitumen.bluefusion.service.fuelTransaction.payload.StorageUnitTransactions;
import java.time.LocalDate;

public interface FuelTransactionService {
    FuelTransactionResponse save(FuelTransactionRequest fuelTransactionRequest);

    StorageUnitTransactions findAll(Long storageUnitId, LocalDate fromDate, LocalDate toDate);
}
