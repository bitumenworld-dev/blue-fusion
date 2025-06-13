package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.handler;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionEntities;
import com.bitumen.bluefusion.service.fuelTransaction.impl.FuelTransactionEntityResolver;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.AbstractFuelTransactionHandler;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.FuelTransactionValidator;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GrvFuelTransactionHandler extends AbstractFuelTransactionHandler {

    private final FuelTransactionValidator fuelTransactionValidator;

    @Override
    public void validateFuelTransaction(FuelTransactionRequest fuelTransactionRequest) {
        fuelTransactionValidator.validateGRV(fuelTransactionRequest);
    }

    @Override
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
            .thirdParty(fuelTransactionEntities.getThirdParty())
            .registrationNumber(fuelTransactionRequest.registrationNumber())
            .workshop(fuelTransactionEntities.getWorkshop())
            .multiplier(1)
            .build();
    }
}
