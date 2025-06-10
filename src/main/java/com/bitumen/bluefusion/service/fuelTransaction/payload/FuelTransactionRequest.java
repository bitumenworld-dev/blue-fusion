package com.bitumen.bluefusion.service.fuelTransaction.payload;

import com.bitumen.bluefusion.domain.enumeration.FuelTransactionType;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import java.time.LocalDate;

public record FuelTransactionRequest(
    Long companyId,
    Long storageId,
    Long pumpId,
    FuelType fuelType,
    FuelTransactionType transactionType,
    Long assetPlantId,
    Long contractDivisionId,
    Long smr,
    Float litres,
    LocalDate transactionDate,
    String note,
    Boolean isFillUp,
    Float meterReadingStart,
    Float meterReadingEnd,
    Long transferUnitId,
    Long thirdPartyId,
    String registrationNumber
) {}
