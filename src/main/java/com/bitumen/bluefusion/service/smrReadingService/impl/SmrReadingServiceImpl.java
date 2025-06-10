package com.bitumen.bluefusion.service.smrReadingService.impl;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.domain.SmrReading;
import com.bitumen.bluefusion.repository.AssetPlantRepository;
import com.bitumen.bluefusion.repository.SmrReadingRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.smrReadingService.SmrReadingService;
import com.bitumen.bluefusion.service.smrReadingService.dto.SmrReadingMapper;
import com.bitumen.bluefusion.service.smrReadingService.dto.SmrReadingRequest;
import com.bitumen.bluefusion.service.smrReadingService.dto.SmrReadingResponse;
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
public class SmrReadingServiceImpl implements SmrReadingService {

    private final SmrReadingRepository smrReadingRepository;
    private final AssetPlantRepository assetPlantRepository;

    @Override
    public SmrReadingResponse save(SmrReadingRequest smrReadingRequest) {
        AssetPlant assetPlant = assetPlantRepository
            .findById(smrReadingRequest.assetPlantId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Asset Plant not found: %s", smrReadingRequest.assetPlantId())));
        SmrReading smrReading = SmrReading.builder()
            .assetPlant(assetPlant)
            .readingDateTime(smrReadingRequest.readingDateTime())
            .unit(smrReadingRequest.unit())
            .fuelTransactionHeaderId(Math.toIntExact(smrReadingRequest.fuelTransactionHeaderId()))
            .whatsappNumber(smrReadingRequest.whatsappNumber())
            .build();
        smrReading = smrReadingRepository.save(smrReading);
        return SmrReadingMapper.map.apply(smrReading);
    }

    @Override
    public SmrReadingResponse update(Long smrReadingId, SmrReadingRequest smrReadingRequest) {
        SmrReading smrReading = smrReadingRepository
            .findById(smrReadingId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("SMR Reading not found: %s", smrReadingId)));
        AssetPlant assetPlant = assetPlantRepository
            .findById(smrReadingRequest.assetPlantId())
            .orElseThrow(() -> new RecordNotFoundException(String.format("Asset Plant not found: %s", smrReadingRequest.assetPlantId())));
        smrReading.setAssetPlant(assetPlant);
        smrReading.setSmrReadingValue(smrReadingRequest.smrReadingValue().floatValue());
        smrReading.setReadingDateTime(smrReadingRequest.readingDateTime());
        smrReading.setUnit(smrReadingRequest.unit());
        smrReading.setFuelTransactionHeaderId(Math.toIntExact(smrReadingRequest.fuelTransactionHeaderId()));
        smrReading.setWhatsappNumber(smrReadingRequest.whatsappNumber());
        smrReading = smrReadingRepository.save(smrReading);
        return SmrReadingMapper.map.apply(smrReading);
    }

    @Override
    public SmrReadingResponse partialUpdate(Long smrReadingId, SmrReadingRequest smrReadingRequest) {
        return smrReadingRepository
            .findById(smrReadingId)
            .map(existingSmrReading -> {
                Optional.ofNullable(smrReadingRequest.assetPlantId()).ifPresent(assetPlantId -> {
                    AssetPlant assetPlant = assetPlantRepository
                        .findById(assetPlantId)
                        .orElseThrow(() -> new RecordNotFoundException(String.format("Asset Plant not found: %s", assetPlantId)));
                    existingSmrReading.setAssetPlant(assetPlant);
                });
                Optional.ofNullable(smrReadingRequest.smrReadingValue()).ifPresent(val ->
                    existingSmrReading.setSmrReadingValue(val.floatValue())
                );
                Optional.ofNullable(smrReadingRequest.readingDateTime()).ifPresent(existingSmrReading::setReadingDateTime);
                Optional.ofNullable(smrReadingRequest.unit()).ifPresent(existingSmrReading::setUnit);
                Optional.ofNullable(smrReadingRequest.fuelTransactionHeaderId()).ifPresent(val ->
                    existingSmrReading.setFuelTransactionHeaderId(Math.toIntExact(val))
                );
                Optional.ofNullable(smrReadingRequest.whatsappNumber()).ifPresent(existingSmrReading::setWhatsappNumber);
                return existingSmrReading;
            })
            .map(smrReadingRepository::save)
            .map(SmrReadingMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("SMR Reading not found: %s", smrReadingId)));
    }

    @Transactional(readOnly = true)
    @Override
    public Page<SmrReadingResponse> findAll(Pageable pageable, Long assetPlantId, String unit, Float smrReadingValue) {
        Specification<SmrReading> specification = SmrReadingSpec.withAssetPlantId(assetPlantId)
            .and(SmrReadingSpec.withUnit(unit))
            .and(SmrReadingSpec.withSmrReading(Double.valueOf(smrReadingValue)));

        return smrReadingRepository.findAll(specification, pageable).map(SmrReadingMapper.map);
    }

    @Transactional(readOnly = true)
    @Override
    public SmrReadingResponse findOne(Long smrReadingId, SmrReadingRequest smrReadingRequest) {
        SmrReading smrReading = smrReadingRepository
            .findById(smrReadingId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("SMR Reading not found: %s", smrReadingId)));
        return SmrReadingMapper.map.apply(smrReading);
    }

    @Override
    public void delete(Long smrReadingId) {
        if (!smrReadingRepository.existsById(smrReadingId)) {
            throw new RecordNotFoundException(String.format("SMR Reading not found: %s", smrReadingId));
        }
        smrReadingRepository.deleteById(smrReadingId);
    }
}
