package com.bitumen.bluefusion.service.fuelTransaction;

import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;

public interface FuelTransactionHandler {
    FuelTransactionResponse handle(FuelTransactionRequest request);
}
