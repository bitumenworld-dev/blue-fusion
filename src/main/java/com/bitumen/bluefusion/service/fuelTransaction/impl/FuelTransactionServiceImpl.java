package com.bitumen.bluefusion.service.fuelTransaction.impl;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.repository.StorageRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.fuelTransaction.FuelTransactionService;
import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitPump;
import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitTransaction;
import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitTransactionBuilder;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.FleetIssuanceFuelTransactionHandler;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.InterStoreTransferFuelTransactionHandler;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers.ThirdPartyIssuanceFuelTransactionHandler;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import com.bitumen.bluefusion.service.fuelTransaction.payload.StorageUnitTransactions;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class FuelTransactionServiceImpl implements FuelTransactionService {

    private final FleetIssuanceFuelTransactionHandler fleetIssuanceFuelTransactionHandler;
    private final StorageRepository storageRepository;
    private final FuelTransactionHeaderRepository fuelTransactionHeaderRepository;
    private final FuelTransactionLineRepository fuelTransactionLineRepository;
    private final InterStoreTransferFuelTransactionHandler interStoreTransferFuelTransactionHandler;
    private final ThirdPartyIssuanceFuelTransactionHandler thirdPartyIssuanceFuelTransactionHandler;
    private final StorageUnitTransactionBuilder storageUnitTransactionBuilder;

    @Override
    public FuelTransactionResponse save(FuelTransactionRequest fuelTransactionRequest) {
        switch (fuelTransactionRequest.transactionType()) {
            case FLEET_ISSUANCE:
                return fleetIssuanceFuelTransactionHandler.handle(fuelTransactionRequest);
            case GRV:
                // execute GRV code
                break;
            case TRANSFER:
                return interStoreTransferFuelTransactionHandler.handle(fuelTransactionRequest);
            case CALIBRATION:
                // execute calibration code
                break;
            case THIRD_PARTY_ISSUANCE:
                return thirdPartyIssuanceFuelTransactionHandler.handle(fuelTransactionRequest);
            default:
            // throw exception
        }

        return null;
    }

    @Override
    public StorageUnitTransactions findAll(@NonNull Long storageUnitId, @NonNull LocalDate fromDate, @NonNull LocalDate toDate) {
        Storage storage = storageRepository
            .findById(storageUnitId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Storage unit with id %d not found", storageUnitId)));

        Specification<FuelTransactionHeader> specification = FuelTransactionSpec.withStorage(storage).and(
            FuelTransactionSpec.withCreatedDateBetween(fromDate, toDate)
        );

        List<FuelTransactionHeader> transactionHeaders = fuelTransactionHeaderRepository.findAll(specification);

        List<StorageUnitTransaction> storageTransactions = transactionHeaders
            .parallelStream()
            .map(storageUnitTransactionBuilder::mapToStorageUnitTransaction)
            .collect(Collectors.toList());

        BigDecimal totalFuelByStorageUnitId = fuelTransactionLineRepository.getTotalFuelByStorageUnitId(storageUnitId);

        List<StorageUnitPump> highestMeterReadingByPump = getHighestMeterReadingByPump(storageTransactions);
        LocalDate latestTransactionDate = getLatestTransactionDateFromHeaders(transactionHeaders);

        return new StorageUnitTransactions(totalFuelByStorageUnitId, highestMeterReadingByPump, storageTransactions, latestTransactionDate);
    }

    private LocalDate getLatestTransactionDateFromHeaders(List<FuelTransactionHeader> transactions) {
        return transactions
            .stream()
            .map(FuelTransactionHeader::getTransactionDate)
            .filter(Objects::nonNull)
            .max(LocalDate::compareTo)
            .orElse(null);
    }

    private List<StorageUnitPump> getHighestMeterReadingByPump(List<StorageUnitTransaction> storageTransactions) {
        return storageTransactions
            .stream()
            .filter(line -> line.getFuelPump() != null && line.getMeterReadingEnd() != null)
            .collect(
                Collectors.groupingBy(
                    StorageUnitTransaction::getFuelPump,
                    Collectors.mapping(StorageUnitTransaction::getMeterReadingEnd, Collectors.maxBy(Float::compareTo))
                )
            )
            .entrySet()
            .stream()
            .filter(entry -> entry.getValue().isPresent())
            .map(entry -> new StorageUnitPump(entry.getKey(), entry.getValue().get()))
            .collect(Collectors.toList());
    }
}
