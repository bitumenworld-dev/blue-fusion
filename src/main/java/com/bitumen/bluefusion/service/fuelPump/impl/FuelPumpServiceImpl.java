package com.bitumen.bluefusion.service.fuelPump.impl;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.repository.FuelPumpRepository;
import com.bitumen.bluefusion.repository.StorageRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.fuelPump.FuelPumpService;
import com.bitumen.bluefusion.service.fuelPump.dto.FuelPumpRequest;
import com.bitumen.bluefusion.service.fuelPump.dto.FuelPumpResponse;
import com.bitumen.bluefusion.service.fuelPump.dto.FuelPumpResponseMapper;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.FuelPump}.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class FuelPumpServiceImpl implements FuelPumpService {

    private final FuelPumpRepository fuelPumpRepository;
    private final StorageRepository storageRepository;

    @Override
    public FuelPumpResponse save(FuelPumpRequest fuelPumpRequest) {
        Storage storage = storageRepository
            .findById(fuelPumpRequest.storageId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Storage unit not found: %s", fuelPumpRequest.storageId())));
        FuelPump fuelPump = FuelPump.builder()
            .fuelPumpCode(fuelPumpRequest.fuelPumpCode())
            .currentStorageUnit(storage)
            .description(fuelPumpRequest.description())
            .isActive(fuelPumpRequest.isActive())
            .validFrom(fuelPumpRequest.validFrom())
            .build();
        fuelPump = fuelPumpRepository.save(fuelPump);
        return FuelPumpResponseMapper.map.apply(fuelPump);
    }

    @Override
    public FuelPumpResponse update(Long fuelPumpId, FuelPumpRequest fuelPumpRequest) {
        Storage storage = storageRepository
            .findById(fuelPumpRequest.storageId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Storage unit not found: %s", fuelPumpRequest.storageId())));
        FuelPump fuelPump = fuelPumpRepository
            .findById(fuelPumpId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("FuelPump unit not found: %s", fuelPumpId)));
        fuelPump.setCurrentStorageUnit(storage);
        fuelPump.setDescription(fuelPumpRequest.description());
        fuelPump.setFuelPumpCode(fuelPumpRequest.fuelPumpCode());
        fuelPump.setIsActive(fuelPumpRequest.isActive());
        fuelPump.setValidFrom(fuelPumpRequest.validFrom());
        fuelPump = fuelPumpRepository.save(fuelPump);

        fuelPump = fuelPumpRepository.save(fuelPump);
        return FuelPumpResponseMapper.map.apply(fuelPump);
    }

    @Override
    public FuelPumpResponse partialUpdate(Long fuelPumpId, FuelPumpRequest fuelPumpRequest) {
        return fuelPumpRepository
            .findById(fuelPumpId)
            .map(existingFuelPump -> {
                Optional.ofNullable(fuelPumpRequest.fuelPumpCode()).ifPresent(existingFuelPump::setFuelPumpCode);
                Optional.ofNullable(fuelPumpRequest.description()).ifPresent(existingFuelPump::setDescription);
                Optional.ofNullable(fuelPumpRequest.isActive()).ifPresent(existingFuelPump::setIsActive);
                Optional.ofNullable(fuelPumpRequest.validFrom()).ifPresent(existingFuelPump::setValidFrom);
                Optional.ofNullable(fuelPumpRequest.storageId())
                    .flatMap(storageRepository::findById)
                    .ifPresent(existingFuelPump::setCurrentStorageUnit);
                return existingFuelPump;
            })
            .map(fuelPumpRepository::save)
            .map(FuelPumpResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("FuelPump with id %s not found", fuelPumpId)));
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelPumpResponse> findAll(Pageable pageable, Long storageUnitId, Boolean isActive, String fuelPumpCode) {
        Storage storage = null;
        if (!Objects.isNull(storageUnitId) && storageUnitId > 0) {
            storage = storageRepository.findById(storageUnitId).get();
        }

        Specification<FuelPump> specification = FuelPumpSpec.withFuelPumpCode(fuelPumpCode)
            .and(FuelPumpSpec.isActive(isActive))
            .and(FuelPumpSpec.withFuelPumpCode(fuelPumpCode))
            .and(FuelPumpSpec.withCurrentStorageUnit(storage));

        return fuelPumpRepository.findAll(specification, pageable).map(FuelPumpResponseMapper.map);
    }

    @Override
    @Transactional(readOnly = true)
    public FuelPumpResponse findOne(Long id) {
        return fuelPumpRepository
            .findById(id)
            .map(FuelPumpResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("FuelPump with id %s not found", id)));
    }

    @Override
    public void delete(Long id) {
        fuelPumpRepository
            .findById(id)
            .ifPresentOrElse(fuelPumpRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("FuelPump with id %s not found", id));
            });
    }
}
