package com.bitumen.bluefusion.service.fuelTransaction.payload;

import com.bitumen.bluefusion.domain.enumeration.FuelTransactionType;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import com.bitumen.bluefusion.domain.enumeration.IssuanceTransactionType;
import java.time.LocalDate;

public record FuelTransactionRequest(
    Long companyId,
    Long storageId,
    Long pumpId,
    FuelType fuelType,
    FuelTransactionType transactionType,
    IssuanceTransactionType issuanceType,
    Long assetPlantId,
    Long contractDivisionId,
    Long smr,
    Float litres,
    LocalDate transactionDate,
    String note,
    Boolean isFillUp,
    Float meterReadingStart,
    Float meterReadingEnd
) {}
