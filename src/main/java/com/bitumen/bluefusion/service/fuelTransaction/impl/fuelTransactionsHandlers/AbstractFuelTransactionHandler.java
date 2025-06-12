package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionEntities;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionHeaderResponse;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionLineResponse;
import com.bitumen.bluefusion.service.fuelTransaction.impl.FuelTransactionEntityResolver;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public abstract class AbstractFuelTransactionHandler {

    @Autowired
    private FuelTransactionLineRepository fuelTransactionLineRepository;

    @Autowired
    private FuelTransactionHeaderRepository fuelTransactionHeaderRepository;

    @Autowired
    private FuelTransactionEntityResolver fuelTransactionEntityResolver;

    public abstract void validateFuelTransaction(FuelTransactionRequest fuelTransactionRequest);

    public FuelTransactionResponse handle(FuelTransactionRequest fuelTransactionRequest) {
        validateFuelTransaction(fuelTransactionRequest);

        FuelTransactionEntities fuelTransactionEntities = fuelTransactionEntityResolver.resolveFuelTransactionEntities(
            fuelTransactionRequest
        );

        FuelTransactionHeader transactionHeader = createFuelTransactionHeader(fuelTransactionRequest, fuelTransactionEntities);
        transactionHeader = fuelTransactionHeaderRepository.save(transactionHeader);
        FuelTransactionLine transactionLine = createFuelTransactionLine(transactionHeader, fuelTransactionRequest, fuelTransactionEntities);
        fuelTransactionLineRepository.save(transactionLine);

        return new FuelTransactionResponse(
            FuelTransactionHeaderResponse.fuelTransactionHeaderResponseBuilder(transactionHeader),
            List.of(FuelTransactionLineResponse.fuelTransactionLineResponseBuilder(transactionLine))
        );
    }

    private FuelTransactionHeader createFuelTransactionHeader(
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

    private FuelTransactionLine createFuelTransactionLine(
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
            .multiplier(-1)
            .build();
    }
}
