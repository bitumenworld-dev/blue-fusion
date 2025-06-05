package com.bitumen.bluefusion.service.fuelTransaction;

import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionHeaderResponse;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import com.bitumen.bluefusion.service.fuelTransaction.payload.StorageUnitTransactions;
import java.time.LocalDate;
import java.util.List;

public interface FuelTransactionService {
    FuelTransactionResponse save(FuelTransactionRequest fuelTransactionRequest);

    StorageUnitTransactions findAll(Long storageUnitId, LocalDate fromDate, LocalDate toDate);
}
