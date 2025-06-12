package com.bitumen.bluefusion.service.fuelTransaction.dto;

import com.bitumen.bluefusion.domain.*;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageUnitTransactionBuilder {

    private final FuelTransactionLineRepository fuelTransactionLineRepository;

    public StorageUnitTransaction mapToStorageUnitTransaction(FuelTransactionHeader header) {
        if (header == null) {
            throw new IllegalArgumentException("FuelTransactionHeader cannot be null");
        }

        return switch (header.getFuelTransactionType()) {
            case FLEET_ISSUANCE -> mapFromFleetIssuanceTransaction(header);
            case GRV -> mapFromGrvTransaction(header);
            case TRANSFER -> mapFromTransferTransaction(header);
            case CALIBRATION -> mapFromCalibrationTransaction(header);
            case THIRD_PARTY_ISSUANCE -> mapFromThirdPartyTransaction(header);
            default -> null;
            //            case null -> throw new IllegalArgumentException("FuelTransactionType cannot be null");
        };
    }

    private StorageUnitTransaction mapFromFleetIssuanceTransaction(FuelTransactionHeader header) {
        return mapTransaction(header, this::extractFleetNumberFromAssetPlant);
    }

    private StorageUnitTransaction mapFromThirdPartyTransaction(FuelTransactionHeader header) {
        return mapTransaction(header, this::extractThirdPartyInfo);
    }

    private StorageUnitTransaction mapFromTransferTransaction(FuelTransactionHeader header) {
        return mapTransaction(header, this::extractStorageUnitName);
    }

    private StorageUnitTransaction mapFromGrvTransaction(FuelTransactionHeader header) {
        // TODO: Implement GRV transaction mapping logic
        log.warn("GRV transaction mapping not yet implemented for header ID: {}", header.getFuelTransactionHeaderId());
        return createEmptyTransaction(header);
    }

    private StorageUnitTransaction mapFromCalibrationTransaction(FuelTransactionHeader header) {
        // TODO: Implement calibration transaction mapping logic
        log.warn("Calibration transaction mapping not yet implemented for header ID: {}", header.getFuelTransactionHeaderId());
        return createEmptyTransaction(header);
    }

    /**
     * Generic method to map transactions with different fleet number extraction strategies
     */
    private StorageUnitTransaction mapTransaction(FuelTransactionHeader header, FleetNumberExtractor fleetNumberExtractor) {
        try {
            List<FuelTransactionLine> transactionLines = getTransactionLines(header);

            if (transactionLines.isEmpty()) {
                log.warn("No transaction lines found for header ID: {}", header.getFuelTransactionHeaderId());
                return createEmptyTransaction(header);
            }

            FuelTransactionLine transactionLine = transactionLines.get(0);

            return StorageUnitTransaction.builder()
                .transactionDate(header.getTransactionDate())
                .fuelTransactionType(buildTransactionTypeString(header))
                .fuelType(header.getFuelType())
                .fleetNumber(fleetNumberExtractor.extract(transactionLine))
                .meterReadingStart(transactionLine.getMeterReadingStart())
                .meterReadingEnd(transactionLine.getMeterReadingEnd())
                .litres(transactionLine.getLitres())
                .contractDivision(buildContractDivisionString(transactionLine))
                .isFillUp(header.getIsFillUp())
                .fuelPump(extractPumpIdentifier(transactionLine))
                .note(header.getNote())
                .build();
        } catch (Exception e) {
            log.error("Error processing transaction header ID: {}", header.getFuelTransactionHeaderId(), e);
            return createEmptyTransaction(header);
        }
    }

    private List<FuelTransactionLine> getTransactionLines(FuelTransactionHeader header) {
        try {
            return fuelTransactionLineRepository.findByFuelTransactionHeader(header);
        } catch (Exception e) {
            log.error("Failed to retrieve transaction lines for header ID: {}", header.getFuelTransactionHeaderId(), e);
            return Collections.emptyList();
        }
    }

    private String extractFleetNumberFromAssetPlant(FuelTransactionLine transactionLine) {
        return Optional.ofNullable(transactionLine.getAssetPlant()).map(AssetPlant::getFleetNumber).orElse(null);
    }

    private String extractThirdPartyInfo(FuelTransactionLine transactionLine) {
        StringBuilder thirdPartyInfo = new StringBuilder();
        ThirdParty thirdParty = transactionLine.getThirdParty();
        if (thirdParty == null) {
            return null;
        }

        thirdPartyInfo.append(thirdParty.getThirdPartyName());

        if (thirdParty.getUsesFuelSystem()) {
            AssetPlant assetPlant = transactionLine.getAssetPlant();
            if (assetPlant != null) {
                thirdPartyInfo.append(" - ").append(assetPlant.getFleetNumber());
            }
        } else {
            thirdPartyInfo.append(" - ").append(transactionLine.getRegistrationNumber());
        }

        return thirdPartyInfo.toString();
    }

    private String extractStorageUnitName(FuelTransactionLine transactionLine) {
        return Optional.ofNullable(transactionLine.getTransferUnit()).map(Storage::getName).orElse(null);
    }

    private StorageUnitTransaction createEmptyTransaction(FuelTransactionHeader header) {
        return StorageUnitTransaction.builder()
            .transactionDate(header.getCreatedDate())
            .fuelTransactionType(buildTransactionTypeString(header))
            .fuelType(header.getFuelType())
            .isFillUp(header.getIsFillUp())
            .note(header.getNote())
            .build();
    }

    private String buildTransactionTypeString(FuelTransactionHeader header) {
        return Optional.ofNullable(header.getFuelTransactionType()).map(Enum::name).orElse("");
    }

    private String buildContractDivisionString(FuelTransactionLine line) {
        return Optional.ofNullable(line.getContractDivision())
            .map(division -> String.format("%s - %s", division.getContractDivisionNumber(), division.getContractDivisionName()))
            .orElse(null);
    }

    private String extractPumpIdentifier(FuelTransactionLine line) {
        return Optional.ofNullable(line.getPump()).map(FuelPump::getDescription).orElse(null);
    }

    /**
     * Functional interface for different fleet number extraction strategies
     */
    @FunctionalInterface
    private interface FleetNumberExtractor {
        String extract(FuelTransactionLine transactionLine);
    }
}
