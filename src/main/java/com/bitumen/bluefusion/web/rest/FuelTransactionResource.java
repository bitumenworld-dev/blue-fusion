package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.fuelTransaction.FuelTransactionService;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import com.bitumen.bluefusion.service.fuelTransaction.payload.StorageUnitTransactions;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/fuel-transaction")
public class FuelTransactionResource {

    private final FuelTransactionService fuelTransactionService;

    @PostMapping("")
    public ResponseEntity<FuelTransactionResponse> createFuelTransaction(@RequestBody FuelTransactionRequest fuelTransactionRequest) {
        return new ResponseEntity<>(fuelTransactionService.save(fuelTransactionRequest), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<StorageUnitTransactions> getStorageUnitTransactions(
        @RequestParam("storageUnitId") Long storageUnitId,
        @RequestParam("startDate") LocalDate startDate,
        @RequestParam("endDate") LocalDate endDate
    ) {
        StorageUnitTransactions storageUnitTransactions = fuelTransactionService.findAll(storageUnitId, startDate, endDate);
        return new ResponseEntity<>(storageUnitTransactions, HttpStatus.OK);
    }
}
