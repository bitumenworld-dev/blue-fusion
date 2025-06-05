package com.bitumen.bluefusion.service.fuelTransaction.payload;

import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionHeaderResponse;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionLineResponse;
import java.util.List;

public record FuelTransactionResponse(
    FuelTransactionHeaderResponse fuelTransactionHeaderResponse,
    List<FuelTransactionLineResponse> fuelTransactionLineResponseList
) {}
