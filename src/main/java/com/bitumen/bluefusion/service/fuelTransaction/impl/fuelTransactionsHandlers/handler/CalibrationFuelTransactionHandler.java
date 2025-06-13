package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.handler;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionEntities;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionHeaderResponse;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionLineResponse;
import com.bitumen.bluefusion.service.fuelTransaction.impl.FuelTransactionEntityResolver;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.AbstractFuelTransactionHandler;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.FuelTransactionValidator;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CalibrationFuelTransactionHandler extends AbstractFuelTransactionHandler {

    private final FuelTransactionValidator fuelTransactionValidator;
    private final FuelTransactionEntityResolver fuelTransactionEntityResolver;
    private final FuelTransactionLineRepository fuelTransactionLineRepository;
    private final FuelTransactionHeaderRepository fuelTransactionHeaderRepository;

    @Override
    public void validateFuelTransaction(FuelTransactionRequest fuelTransactionRequest) {
        fuelTransactionValidator.validateDrumIssuance(fuelTransactionRequest);
    }

    @Override
    public FuelTransactionResponse handle(FuelTransactionRequest fuelTransactionRequest) {
        validateFuelTransaction(fuelTransactionRequest);

        FuelTransactionEntities entities = fuelTransactionEntityResolver.resolveFuelTransactionEntities(fuelTransactionRequest);

        // Create and persist header
        FuelTransactionHeader header = createFuelTransactionHeader(fuelTransactionRequest, entities);
        header = fuelTransactionHeaderRepository.save(header);

        // Create transaction lines with explicit purposes
        FuelTransactionLine outboundLine = createOutboundTransactionLine(header, fuelTransactionRequest, entities);
        FuelTransactionLine inboundLine = createInboundTransactionLine(header, fuelTransactionRequest, entities);

        // Batch save lines for better performance
        List<FuelTransactionLine> savedLines = fuelTransactionLineRepository.saveAll(List.of(outboundLine, inboundLine));

        return buildTransactionResponse(header, savedLines);
    }

    private FuelTransactionLine createOutboundTransactionLine(
        FuelTransactionHeader header,
        FuelTransactionRequest request,
        FuelTransactionEntities entities
    ) {
        FuelTransactionLine line = createFuelTransactionLine(header, request, entities);
        line.setMultiplier(-1); // Explicit outbound multiplier
        return line;
    }

    private FuelTransactionLine createInboundTransactionLine(
        FuelTransactionHeader header,
        FuelTransactionRequest request,
        FuelTransactionEntities entities
    ) {
        FuelTransactionLine line = createFuelTransactionLine(header, request, entities);
        line.setMultiplier(1); // Explicit inbound multiplier
        return line;
    }

    private FuelTransactionResponse buildTransactionResponse(FuelTransactionHeader header, List<FuelTransactionLine> lines) {
        FuelTransactionHeaderResponse headerResponse = FuelTransactionHeaderResponse.fuelTransactionHeaderResponseBuilder(header);

        List<FuelTransactionLineResponse> lineResponses = lines
            .stream()
            .map(FuelTransactionLineResponse::fuelTransactionLineResponseBuilder)
            .toList();

        return new FuelTransactionResponse(headerResponse, lineResponses);
    }
}
