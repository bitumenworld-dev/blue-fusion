package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionEntities;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;

public abstract class AbstractFuelTransactionHandler {

    public abstract FuelTransactionResponse handle(FuelTransactionRequest fuelTransactionRequest);

    protected FuelTransactionHeader createFuelTransactionHeader(
        FuelTransactionRequest fuelTransactionRequest,
        FuelTransactionEntities fuelTransactionEntities
    ) {
        return FuelTransactionHeader.builder()
            .company(fuelTransactionEntities.getCompany())
            .storageUnit(fuelTransactionEntities.getStorageUnit())
            .fuelTransactionType(fuelTransactionRequest.transactionType())
            .fuelType(fuelTransactionRequest.fuelType())
            .isFillUp(fuelTransactionRequest.isFillUp())
            .transactionDate(fuelTransactionRequest.transactionDate())
            .build();
    }

    protected FuelTransactionLine createFuelTransactionLine(
        FuelTransactionHeader fuelTransactionHeader,
        FuelTransactionRequest fuelTransactionRequest,
        FuelTransactionEntities fuelTransactionEntities
    ) {
        return FuelTransactionLine.builder()
            .fuelTransactionHeader(fuelTransactionHeader)
            .assetPlant(fuelTransactionEntities.getAssetPlant())
            .pump(fuelTransactionEntities.getFuelPump())
            .contractDivision(fuelTransactionEntities.getContractDivision())
            .litres(fuelTransactionRequest.litres())
            .transferUnit(fuelTransactionEntities.getTransferUnit())
            .meterReadingStart(fuelTransactionRequest.meterReadingStart())
            .meterReadingEnd(fuelTransactionRequest.meterReadingEnd())
            .registrationNumber(fuelTransactionRequest.registrationNumber())
            .multiplier(-1)
            .build();
    }
}
