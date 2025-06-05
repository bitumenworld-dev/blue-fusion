package com.bitumen.bluefusion.service.fuelTransaction.payload;

import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitPump;
import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitTransaction;
import java.util.List;

public record StorageUnitTransactions(
    Float currentStorageBalance,
    List<StorageUnitPump> pumpReadings,
    List<StorageUnitTransaction> transactions
) {}
