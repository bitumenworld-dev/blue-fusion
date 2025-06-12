package com.bitumen.bluefusion.service.fuelTransaction.impl.fuelTransactionsHandlers;

import com.bitumen.bluefusion.repository.*;
import com.bitumen.bluefusion.service.exceptions.FuelTransactionValidationException;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import io.micrometer.common.util.StringUtils;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class FuelTransactionValidator {

    private final CompanyRepository companyRepository;
    private final StorageRepository storageRepository;
    private final AssetPlantRepository assetPlantRepository;
    private final FuelPumpRepository fuelPumpRepository;
    private final ContractDivisionRepository contractDivisionRepository;
    private final ThirdPartyRepository thirdPartyRepository;

    public void validateThirdPartyIssuance(FuelTransactionRequest request) {
        log.info("Validating third party issuance: {}", request);
        ValidationResult result = new ValidationResult();

        validateThirdPartyEntity(request.thirdPartyId(), result);
        validateCompany(request.companyId(), result);
        validateStorage(request.storageId(), result, "storage");
        validateFuelPump(request.pumpId(), result);

        if (StringUtils.isBlank(request.registrationNumber()) && !assetPlantRepository.existsByAssetPlantId(request.assetPlantId())) {
            result.addError("Either registration number or a valid asset plant is required");
        }

        result.throwIfHasErrors();
    }

    public void validateFleetIssuance(FuelTransactionRequest request) {
        log.info("Validating Fleet Issuance: {}", request);
        ValidationResult result = new ValidationResult();

        validateCompany(request.companyId(), result);
        validateStorage(request.storageId(), result, "storage");
        validateFuelPump(request.pumpId(), result);
        validateAssetPlant(request.assetPlantId(), result);

        result.throwIfHasErrors();
    }

    public void validateTransfer(FuelTransactionRequest request) {
        log.info("Validating transfer of fuel transaction: {}", request);
        ValidationResult result = new ValidationResult();

        validateCompany(request.companyId(), result);
        validateStorage(request.transferUnitId(), result, "destination storage");
        validateStorage(request.storageId(), result, "transfer unit storage");
        validateFuelPump(request.pumpId(), result);

        result.throwIfHasErrors();
    }

    private void validateThirdPartyEntity(Long thirdPartyId, ValidationResult result) {
        if (thirdPartyId == null) {
            result.addError("Third Party ID is required");
            return;
        }

        if (!thirdPartyRepository.existsByThirdPartyId(thirdPartyId)) {
            result.addError(String.format("Third Party ID %d does not exist", thirdPartyId));
        }
    }

    private void validateCompany(Long companyId, ValidationResult result) {
        if (companyId == null) {
            result.addError("Company ID is required");
            return;
        }

        if (!companyRepository.existsByCompanyId(companyId)) {
            result.addError(String.format("Company ID %d does not exist", companyId));
        }
    }

    private void validateStorage(Long storageId, ValidationResult result, String storageType) {
        if (storageId == null) {
            result.addError(String.format("%s ID is required", storageType.substring(0, 1).toUpperCase() + storageType.substring(1)));
            return;
        }

        if (!storageRepository.existsByStorageId(storageId)) {
            result.addError(String.format("The %s ID %d does not exist", storageType, storageId));
        }
    }

    private void validateFuelPump(Long pumpId, ValidationResult result) {
        if (pumpId == null) {
            result.addError("Pump ID is required");
            return;
        }

        if (!fuelPumpRepository.existsByFuelPumpId(pumpId)) {
            result.addError(String.format("The pump ID %d does not exist", pumpId));
        }
    }

    private void validateAssetPlant(Long assetPlantId, ValidationResult result) {
        if (assetPlantId == null) {
            result.addError("Asset plant ID is required");
            return;
        }

        if (!assetPlantRepository.existsByAssetPlantId(assetPlantId)) {
            result.addError(String.format("Asset plant ID %d does not exist", assetPlantId));
        }
    }

    // Inner class to handle validation results
    private static class ValidationResult {

        private final List<String> errors = new ArrayList<>();

        public void addError(String error) {
            errors.add(error);
        }

        public boolean hasErrors() {
            return !errors.isEmpty();
        }

        public void throwIfHasErrors() {
            if (hasErrors()) {
                throw new FuelTransactionValidationException("Validation failed: " + String.join("; ", errors));
            }
        }
    }
}
