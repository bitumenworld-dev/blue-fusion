package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers;

import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class InterStoreTransferFuelTransactionHandler extends AbstractFuelTransactionHandler {

    private final FuelTransactionValidator fuelTransactionValidator;

    @Override
    public void validateFuelTransaction(FuelTransactionRequest fuelTransactionRequest) {
        fuelTransactionValidator.validateTransfer(fuelTransactionRequest);
    }
}
