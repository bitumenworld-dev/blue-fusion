package com.bitumen.bluefusion.service.fuelTransaction.dto;

import com.bitumen.bluefusion.domain.*;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuelTransactionLineResponse {

    private Long fuelTransactionLineId;
    private String assetPlant;
    private String contractDivision;
    private String registrationNumber;
    private FuelPump pump;
    private Float litres;
    private Float meterReadingStart;
    private Float meterReadingEnd;
    private Integer multiplier;

    public static FuelTransactionLineResponse fuelTransactionLineResponseBuilder(FuelTransactionLine fuelTransactionLine) {
        return FuelTransactionLineResponse.builder()
            .fuelTransactionLineId(fuelTransactionLine.getFuelTransactionLineId())
            .assetPlant(extractAssetPlantInfo(fuelTransactionLine))
            .contractDivision(extractContractDivisionInfo(fuelTransactionLine))
            .pump(fuelTransactionLine.getPump())
            .litres(fuelTransactionLine.getLitres())
            .registrationNumber(fuelTransactionLine.getRegistrationNumber())
            .meterReadingStart(fuelTransactionLine.getMeterReadingStart())
            .meterReadingEnd(fuelTransactionLine.getMeterReadingEnd())
            .build();
    }

    public static List<FuelTransactionLineResponse> fuelTransactionLinesResponseBuilder(List<FuelTransactionLine> fuelTransactionLines) {
        return fuelTransactionLines
            .stream()
            .map(fuelTransactionLine ->
                FuelTransactionLineResponse.builder()
                    .fuelTransactionLineId(fuelTransactionLine.getFuelTransactionLineId())
                    .assetPlant(extractAssetPlantInfo(fuelTransactionLine))
                    .contractDivision(extractContractDivisionInfo(fuelTransactionLine))
                    .pump(fuelTransactionLine.getPump())
                    .litres(fuelTransactionLine.getLitres())
                    .meterReadingStart(fuelTransactionLine.getMeterReadingStart())
                    .meterReadingEnd(fuelTransactionLine.getMeterReadingEnd())
                    .build()
            )
            .toList();
    }

    private static String extractAssetPlantInfo(FuelTransactionLine fuelTransactionLine) {
        return Optional.ofNullable(fuelTransactionLine.getAssetPlant()).map(AssetPlant::getFleetNumber).orElse(null);
    }

    private static String extractContractDivisionInfo(FuelTransactionLine fuelTransactionLine) {
        return Optional.ofNullable(fuelTransactionLine.getContractDivision())
            .map(division -> division.getContractDivisionNumber() + " - " + division.getContractDivisionName())
            .orElse(null);
    }
}
