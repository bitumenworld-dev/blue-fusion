package com.bitumen.bluefusion.service.fuelTransaction.impl;

import com.bitumen.bluefusion.repository.*;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.fuelTransaction.dto.FuelTransactionEntities;
import com.bitumen.bluefusion.service.fuelTransaction.payload.FuelTransactionRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class FuelTransactionEntityResolver {

    private final CompanyRepository companyRepository;
    private final AssetPlantRepository assetPlantRepository;
    private final FuelPumpRepository fuelPumpRepository;
    private final ContractDivisionRepository contractDivisionRepository;
    private final StorageRepository storageRepository;
    private final ThirdPartyRepository thirdPartyRepository;

    public FuelTransactionEntities resolveFuelTransactionEntities(final FuelTransactionRequest fuelTransactionRequest) {
        return FuelTransactionEntities.builder()
            .company(resolveEntityWithValidation(fuelTransactionRequest.companyId(), companyRepository, "Company"))
            .storageUnit(resolveEntityWithValidation(fuelTransactionRequest.storageId(), storageRepository, "Storage"))
            .fuelPump(resolveEntityOptional(fuelTransactionRequest.pumpId(), fuelPumpRepository))
            .contractDivision(resolveEntityOptional(fuelTransactionRequest.contractDivisionId(), contractDivisionRepository))
            .assetPlant(resolveEntityOptional(fuelTransactionRequest.assetPlantId(), assetPlantRepository))
            .transferUnit(resolveEntityOptional(fuelTransactionRequest.storageId(), storageRepository))
            .thirdParty(resolveEntityOptional(fuelTransactionRequest.thirdPartyId(), thirdPartyRepository))
            .build();
    }

    private <T> T resolveEntityWithValidation(Long id, JpaRepository<T, Long> repository, String entityName) {
        if (id == null) {
            return null;
        }
        return repository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("%s with id %d not found", entityName, id)));
    }

    private <T> T resolveEntityOptional(Long id, JpaRepository<T, Long> repository) {
        return id != null ? repository.findById(id).orElse(null) : null;
    }
}
