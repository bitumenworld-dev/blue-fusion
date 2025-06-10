package com.bitumen.bluefusion.service.fuelTransaction.payload;

import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitPump;
import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitTransaction;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public record StorageUnitTransactions(
    BigDecimal currentStorageBalance,
    List<StorageUnitPump> pumpReadings,
    List<StorageUnitTransaction> transactions,
    LocalDate latestTransactionDate
) {}
