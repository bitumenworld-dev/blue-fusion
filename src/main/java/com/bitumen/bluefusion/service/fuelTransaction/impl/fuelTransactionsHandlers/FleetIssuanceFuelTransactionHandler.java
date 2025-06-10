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
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FleetIssuanceFuelTransactionHandler extends AbstractFuelTransactionHandler {

    private final FuelTransactionLineRepository fuelTransactionLineRepository;
    private final FuelTransactionHeaderRepository fuelTransactionHeaderRepository;
    private final FuelTransactionEntityResolver fuelTransactionEntityResolver;
    private final FuelTransactionValidator fuelTransactionValidator;

    @Override
    public FuelTransactionResponse handle(FuelTransactionRequest fuelTransactionRequest) {
        FuelTransactionEntities fuelTransactionEntities = fuelTransactionEntityResolver.resolveFuelTransactionEntities(
            fuelTransactionRequest
        );

        fuelTransactionValidator.validateFleetIssuance(fuelTransactionRequest);

        FuelTransactionHeader transactionHeader = createFuelTransactionHeader(fuelTransactionRequest, fuelTransactionEntities);
        transactionHeader = fuelTransactionHeaderRepository.save(transactionHeader);
        FuelTransactionLine transactionLine = createFuelTransactionLine(transactionHeader, fuelTransactionRequest, fuelTransactionEntities);
        fuelTransactionLineRepository.save(transactionLine);

        return new FuelTransactionResponse(
            FuelTransactionHeaderResponse.fuelTransactionHeaderResponseBuilder(transactionHeader),
            List.of(FuelTransactionLineResponse.fuelTransactionLineResponseBuilder(transactionLine))
        );
    }
}
