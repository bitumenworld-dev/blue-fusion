package com.bitumen.bluefusion.service.assetPlantServiceReading.impl;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import com.bitumen.bluefusion.repository.AssetPlantServiceReadingRepository;
import com.bitumen.bluefusion.service.assetPlantServiceReading.AssetPlantServiceReadingService;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingMapper;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingRequest;
import com.bitumen.bluefusion.service.assetPlantServiceReading.dto.AssetPlantServiceReadingResponse;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class AssetPlantServiceReadingImpl implements AssetPlantServiceReadingService {

    private final AssetPlantServiceReadingRepository assetPlantServiceReadingRepository;

    @Override
    public AssetPlantServiceReadingResponse save(AssetPlantServiceReadingRequest assetPlantServiceReadingRequest) {
        AssetPlantServiceReading assetPlantServiceReading = AssetPlantServiceReading.builder()
            .assetPlantId(assetPlantServiceReadingRequest.assetPlantId())
            .nextServiceSmrReading(assetPlantServiceReadingRequest.nextServiceSmrReading())
            .estimatedUnitsPerDay(assetPlantServiceReadingRequest.estimatedUnitsPerDay())
            .latestSmrReadings(assetPlantServiceReadingRequest.latestSmrReadings())
            .serviceInterval(assetPlantServiceReadingRequest.serviceInterval())
            .lastServiceDate(assetPlantServiceReadingRequest.lastServiceDate())
            .latestSmrDate(assetPlantServiceReadingRequest.latestSmrDate())
            .lastServiceSmr(assetPlantServiceReadingRequest.lastServiceSmr())
            .serviceUnit(assetPlantServiceReadingRequest.serviceUnit())
            .build();

        assetPlantServiceReading = assetPlantServiceReadingRepository.save(assetPlantServiceReading);
        return AssetPlantServiceReadingMapper.map.apply(assetPlantServiceReading);
    }

    @Override
    public AssetPlantServiceReadingResponse update(
        Long assetPlantServiceReadingId,
        AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    ) {
        AssetPlantServiceReading assetPlantServiceReading = assetPlantServiceReadingRepository
            .findById(assetPlantServiceReadingId)
            .orElseThrow(() ->
                new RecordNotFoundException(String.format("Asset Plant Service Reading not found: %s", assetPlantServiceReadingId))
            );

        assetPlantServiceReading.setAssetPlantId(assetPlantServiceReadingRequest.assetPlantId());
        assetPlantServiceReading.setNextServiceSmrReading(assetPlantServiceReadingRequest.nextServiceSmrReading());
        assetPlantServiceReading.setEstimatedUnitsPerDay(assetPlantServiceReadingRequest.estimatedUnitsPerDay());
        assetPlantServiceReading.setLatestSmrReadings(assetPlantServiceReadingRequest.latestSmrReadings());
        assetPlantServiceReading.setServiceInterval(assetPlantServiceReadingRequest.serviceInterval());
        assetPlantServiceReading.setLastServiceDate(assetPlantServiceReadingRequest.lastServiceDate());
        assetPlantServiceReading.setLatestSmrDate(assetPlantServiceReadingRequest.latestSmrDate());
        assetPlantServiceReading.setLastServiceSmr(assetPlantServiceReadingRequest.lastServiceSmr());
        assetPlantServiceReading.setServiceUnit(assetPlantServiceReadingRequest.serviceUnit());

        assetPlantServiceReading = assetPlantServiceReadingRepository.save(assetPlantServiceReading);
        return AssetPlantServiceReadingMapper.map.apply(assetPlantServiceReading);
    }

    @Override
    public AssetPlantServiceReadingResponse partialUpdate(
        Long assetPlantServiceReadingId,
        AssetPlantServiceReadingRequest assetPlantServiceReadingRequest
    ) {
        return assetPlantServiceReadingRepository
            .findById(assetPlantServiceReadingId)
            .map(existingAssetPlantServiceReading -> {
                Optional.ofNullable(assetPlantServiceReadingRequest.assetPlantId()).ifPresent(
                    existingAssetPlantServiceReading::setAssetPlantId
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.nextServiceSmrReading()).ifPresent(
                    existingAssetPlantServiceReading::setNextServiceSmrReading
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.estimatedUnitsPerDay()).ifPresent(
                    existingAssetPlantServiceReading::setEstimatedUnitsPerDay
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.estimatedNextServiceDate()).ifPresent(
                    existingAssetPlantServiceReading::setEstimatedNextServiceDate
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.latestSmrReadings()).ifPresent(
                    existingAssetPlantServiceReading::setLatestSmrReadings
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.serviceInterval()).ifPresent(
                    existingAssetPlantServiceReading::setServiceInterval
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.lastServiceDate()).ifPresent(
                    existingAssetPlantServiceReading::setLastServiceDate
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.latestSmrDate()).ifPresent(
                    existingAssetPlantServiceReading::setLatestSmrDate
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.lastServiceSmr()).ifPresent(
                    existingAssetPlantServiceReading::setLastServiceSmr
                );
                Optional.ofNullable(assetPlantServiceReadingRequest.serviceUnit()).ifPresent(
                    existingAssetPlantServiceReading::setServiceUnit
                );
                return existingAssetPlantServiceReading;
            })
            .map(assetPlantServiceReadingRepository::save)
            .map(AssetPlantServiceReadingMapper.map)
            .orElseThrow(() ->
                new RecordNotFoundException(String.format("Asset Plant Service Reading not found: %s", assetPlantServiceReadingId))
            );
    }

    @Override
    public AssetPlantServiceReadingResponse partialUpdate(Long assetPlantServiceReadingId) {
        throw new UnsupportedOperationException("Not implemented");
    }

    @Transactional(readOnly = true)
    @Override
    public Page<AssetPlantServiceReadingResponse> findAll(Pageable pageable, Long assetPlantId, Boolean isActive, String serviceUnit) {
        Specification<AssetPlantServiceReading> specification = AssetPlantServiceReadingSpec.withAssetPlantId(assetPlantId)
            .and(AssetPlantServiceReadingSpec.withIsActive(isActive))
            .and(AssetPlantServiceReadingSpec.withServiceUnit(serviceUnit));
        return assetPlantServiceReadingRepository.findAll(specification, pageable).map(AssetPlantServiceReadingMapper.map);
    }

    @Transactional(readOnly = true)
    @Override
    public AssetPlantServiceReading findOne(Long assetPlantServiceReadingId) {
        return assetPlantServiceReadingRepository
            .findById(assetPlantServiceReadingId)
            .orElseThrow(() ->
                new RecordNotFoundException(String.format("Asset Plant Service Reading not found: %s", assetPlantServiceReadingId))
            );
    }

    @Override
    public void delete(Long assetPlantServiceReadingId) {
        AssetPlantServiceReading assetPlantServiceReading = assetPlantServiceReadingRepository
            .findById(assetPlantServiceReadingId)
            .orElseThrow(() ->
                new RecordNotFoundException(String.format("Asset Plant Service Reading not found: %s", assetPlantServiceReadingId))
            );
        assetPlantServiceReadingRepository.delete(assetPlantServiceReading);
    }
}
