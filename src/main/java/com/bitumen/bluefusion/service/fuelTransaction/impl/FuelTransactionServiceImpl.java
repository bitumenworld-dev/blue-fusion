package com.bitumen.bluefusion.service.fuelTransaction.impl;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.domain.enumeration.FuelTransactionType;
import com.bitumen.bluefusion.domain.enumeration.IssuanceTransactionType;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.repository.StorageRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.fuelTransaction.FuelTransactionService;
import com.bitumen.bluefusion.service.fuelTransaction.dto.StorageUnitTransaction;
import com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactions.FleetIssuanceFuelTransaction;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionResponse;
import com.bitumen.bluefusion.service.fuelTransaction.payload.StorageUnitTransactions;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class FuelTransactionServiceImpl implements FuelTransactionService {

    private final FleetIssuanceFuelTransaction fleetIssuanceFuelTransaction;
    private final StorageRepository storageRepository;
    private final FuelTransactionHeaderRepository fuelTransactionHeaderRepository;
    private final FuelTransactionLineRepository fuelTransactionLineRepository;

    @Override
    public FuelTransactionResponse save(FuelTransactionRequest fuelTransactionRequest) {
        switch (fuelTransactionRequest.transactionType()) {
            case ISSUANCE:
                return fleetIssuanceFuelTransaction.createFleetIssuanceFuelTransaction(fuelTransactionRequest);
            case GRV:
                // execute GRV code
                break;
            case TRANSFER:
                // execute transfer code
                break;
            case CALIBRATION:
                // execute calibration code
                break;
            default:
            // throw exception
        }

        return null;
    }

    @Override
    public StorageUnitTransactions findAll(Long storageUnitId, LocalDate fromDate, LocalDate toDate) {
        Storage storage = storageRepository
            .findById(storageUnitId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Storage unit with id %d not found", storageUnitId)));

        Specification<FuelTransactionHeader> specification = FuelTransactionSpec.withStorage(storage).and(
            FuelTransactionSpec.withCreatedDateBetween(fromDate, toDate)
        );

        List<StorageUnitTransaction> storageTransactions = fuelTransactionHeaderRepository
            .findAll(specification)
            .stream()
            .map(fuelTransactionHeader -> {
                List<FuelTransactionLine> byFuelTransactionHeader = fuelTransactionLineRepository.findByFuelTransactionHeader(
                    fuelTransactionHeader
                );
                FuelTransactionLine fuelTransactionLine = byFuelTransactionHeader.get(0);

                return StorageUnitTransaction.builder()
                    .transactionDate(fuelTransactionHeader.getCreatedDate())
                    .fuelTransactionType(
                        extractTransactionType(
                            fuelTransactionHeader.getFuelTransactionType(),
                            fuelTransactionHeader.getIssuanceTransactionType()
                        )
                    )
                    .fuelType(fuelTransactionHeader.getFuelType())
                    .fleetNumber(extractFleetNumber(fuelTransactionLine.getAssetPlant()))
                    .meterReadingStart(extractMeterReadingStart(fuelTransactionLine))
                    .meterReadingEnd(extractMeterReadingEnd(fuelTransactionLine))
                    .litres(extractLitres(fuelTransactionLine))
                    .contractDivision(extractContractDivision(fuelTransactionLine))
                    .isFillUp(fuelTransactionHeader.getIsFillUp())
                    .fuelPump(extractPump(fuelTransactionLine))
                    .note(fuelTransactionHeader.getNote())
                    .build();
            })
            .toList();

        return new StorageUnitTransactions(null, null, storageTransactions);
    }

    private String extractTransactionType(FuelTransactionType fuelTransactionType, IssuanceTransactionType issuanceTransactionType) {
        StringBuilder transactionType = new StringBuilder();
        Optional.ofNullable(fuelTransactionType).ifPresent(fuelTransaction -> transactionType.append(fuelTransactionType.name()));
        Optional.ofNullable(issuanceTransactionType).ifPresent(issuanceTransaction ->
            transactionType.append(" - ").append(issuanceTransactionType.name())
        );
        return transactionType.toString();
    }

    private String extractFleetNumber(AssetPlant assetPlant) {
        return Objects.isNull(assetPlant) ? null : assetPlant.getFleetNumber();
    }

    private Float extractMeterReadingStart(FuelTransactionLine fuelTransactionLine) {
        return fuelTransactionLine.getMeterReadingStart();
    }

    private Float extractMeterReadingEnd(FuelTransactionLine fuelTransactionLine) {
        return fuelTransactionLine.getMeterReadingEnd();
    }

    private Float extractLitres(FuelTransactionLine fuelTransactionLine) {
        return fuelTransactionLine.getLitres();
    }

    private String extractContractDivision(FuelTransactionLine fuelTransactionLine) {
        StringBuilder contractDivision = new StringBuilder();
        if (Objects.isNull(fuelTransactionLine.getContractDivision())) return null;
        else return contractDivision
            .append(fuelTransactionLine.getContractDivision().getContractDivisionNumber())
            .append(" - ")
            .append(fuelTransactionLine.getContractDivision().getContractDivisionName())
            .toString();
    }

    private String extractPump(FuelTransactionLine fuelTransactionLine) {
        if (Objects.isNull(fuelTransactionLine.getPump())) return null;
        else {
            return !StringUtils.hasText(fuelTransactionLine.getPump().getFuelPumpCode())
                ? fuelTransactionLine.getPump().getDescription()
                : fuelTransactionLine.getPump().getFuelPumpCode();
        }
    }
}
