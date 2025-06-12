package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers;

import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ThirdPartyIssuanceFuelTransactionHandler extends AbstractFuelTransactionHandler {

    private final FuelTransactionValidator fuelTransactionValidator;

    @Override
    public void validateFuelTransaction(FuelTransactionRequest fuelTransactionRequest) {
        fuelTransactionValidator.validateThirdPartyIssuance(fuelTransactionRequest);
    }
}
