package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactions;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.service.fuelTransaction.dto.*;
import com.bitumen.bluefusion.service.fuelTransaction.impl.FuelTransactionEntityResolver;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FleetIssuanceFuelTransaction {

    private final FuelTransactionLineRepository fuelTransactionLineRepository;
    private final FuelTransactionHeaderRepository fuelTransactionHeaderRepository;
    private final FuelTransactionEntityResolver fuelTransactionEntityResolver;

    public FuelTransactionResponse createFleetIssuanceFuelTransaction(FuelTransactionRequest fuelTransactionRequest) {
        FuelTransactionEntities fuelTransactionEntities = fuelTransactionEntityResolver.resolveFuelTransactionEntities(
            fuelTransactionRequest
        );

        FuelTransactionHeader fuelTransactionHeader = FuelTransactionHeader.builder()
            .company(fuelTransactionEntities.getCompany())
            .storageUnit(fuelTransactionEntities.getStorageUnit())
            .fuelTransactionType(fuelTransactionRequest.transactionType())
            .issuanceTransactionType(fuelTransactionRequest.issuanceType())
            .fuelType(fuelTransactionRequest.fuelType())
            .build();

        fuelTransactionHeader = fuelTransactionHeaderRepository.save(fuelTransactionHeader);

        FuelTransactionLine fuelTransactionLine = FuelTransactionLine.builder()
            .fuelTransactionHeader(fuelTransactionHeader)
            .assetPlant(fuelTransactionEntities.getAssetPlant())
            .pump(fuelTransactionEntities.getFuelPump())
            .contractDivision(fuelTransactionEntities.getContractDivision())
            .litres(fuelTransactionRequest.litres())
            .meterReadingStart(fuelTransactionRequest.meterReadingStart())
            .meterReadingEnd(fuelTransactionRequest.meterReadingEnd())
            .multiplier(-1)
            .build();

        fuelTransactionLine = fuelTransactionLineRepository.save(fuelTransactionLine);

        return new FuelTransactionResponse(
            FuelTransactionHeaderResponse.fuelTransactionHeaderResponseBuilder(fuelTransactionHeader),
            List.of(FuelTransactionLineResponse.fuelTransactionLineResponseBuilder(fuelTransactionLine))
        );
    }
}
