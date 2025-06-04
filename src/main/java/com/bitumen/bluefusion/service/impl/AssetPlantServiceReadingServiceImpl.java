package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import com.bitumen.bluefusion.repository.AssetPlantServiceReadingRepository;
import com.bitumen.bluefusion.service.AssetPlantServiceReadingService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.AssetPlantServiceReading}.
 */
@Service
@Transactional
public class AssetPlantServiceReadingServiceImpl implements AssetPlantServiceReadingService {

    private static final Logger LOG = LoggerFactory.getLogger(AssetPlantServiceReadingServiceImpl.class);

    private final AssetPlantServiceReadingRepository assetPlantServiceReadingRepository;

    public AssetPlantServiceReadingServiceImpl(AssetPlantServiceReadingRepository assetPlantServiceReadingRepository) {
        this.assetPlantServiceReadingRepository = assetPlantServiceReadingRepository;
    }

    @Override
    public AssetPlantServiceReading save(AssetPlantServiceReading assetPlantServiceReading) {
        LOG.debug("Request to save AssetPlantServiceReading : {}", assetPlantServiceReading);
        return assetPlantServiceReadingRepository.save(assetPlantServiceReading);
    }

    @Override
    public AssetPlantServiceReading update(AssetPlantServiceReading assetPlantServiceReading) {
        LOG.debug("Request to update AssetPlantServiceReading : {}", assetPlantServiceReading);
        return assetPlantServiceReadingRepository.save(assetPlantServiceReading);
    }

    @Override
    public Optional<AssetPlantServiceReading> partialUpdate(AssetPlantServiceReading assetPlantServiceReading) {
        LOG.debug("Request to partially update AssetPlantServiceReading : {}", assetPlantServiceReading);

        return assetPlantServiceReadingRepository
            .findById(assetPlantServiceReading.getAssetPlantServiceReadingId())
            .map(existingAssetPlantServiceReading -> {
                if (assetPlantServiceReading.getAssetPlantServiceReadingId() != null) {
                    existingAssetPlantServiceReading.setAssetPlantServiceReadingId(
                        assetPlantServiceReading.getAssetPlantServiceReadingId()
                    );
                }
                if (assetPlantServiceReading.getAssetPlantId() != null) {
                    existingAssetPlantServiceReading.setAssetPlantId(assetPlantServiceReading.getAssetPlantId());
                }
                if (assetPlantServiceReading.getNextServiceSmrReading() != null) {
                    existingAssetPlantServiceReading.setNextServiceSmrReading(assetPlantServiceReading.getNextServiceSmrReading());
                }
                if (assetPlantServiceReading.getEstimatedUnitsPerDay() != null) {
                    existingAssetPlantServiceReading.setEstimatedUnitsPerDay(assetPlantServiceReading.getEstimatedUnitsPerDay());
                }
                if (assetPlantServiceReading.getEstimatedNextServiceDate() != null) {
                    existingAssetPlantServiceReading.setEstimatedNextServiceDate(assetPlantServiceReading.getEstimatedNextServiceDate());
                }
                if (assetPlantServiceReading.getLatestSmrReadings() != null) {
                    existingAssetPlantServiceReading.setLatestSmrReadings(assetPlantServiceReading.getLatestSmrReadings());
                }
                if (assetPlantServiceReading.getServiceInterval() != null) {
                    existingAssetPlantServiceReading.setServiceInterval(assetPlantServiceReading.getServiceInterval());
                }
                if (assetPlantServiceReading.getLastServiceDate() != null) {
                    existingAssetPlantServiceReading.setLastServiceDate(assetPlantServiceReading.getLastServiceDate());
                }
                if (assetPlantServiceReading.getLatestSmrDate() != null) {
                    existingAssetPlantServiceReading.setLatestSmrDate(assetPlantServiceReading.getLatestSmrDate());
                }
                if (assetPlantServiceReading.getLastServiceSmr() != null) {
                    existingAssetPlantServiceReading.setLastServiceSmr(assetPlantServiceReading.getLastServiceSmr());
                }
                if (assetPlantServiceReading.getServiceUnit() != null) {
                    existingAssetPlantServiceReading.setServiceUnit(assetPlantServiceReading.getServiceUnit());
                }

                return existingAssetPlantServiceReading;
            })
            .map(assetPlantServiceReadingRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssetPlantServiceReading> findAll(Pageable pageable, Long assetPlantServiceReadingId, Long assetPlantId, Boolean isActive) {
        LOG.debug("Request to get all AssetPlantServiceReadings");
        return assetPlantServiceReadingRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<AssetPlantServiceReading> findOne(Long id) {
        LOG.debug("Request to get AssetPlantServiceReading : {}", id);
        return assetPlantServiceReadingRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete AssetPlantServiceReading : {}", id);
        assetPlantServiceReadingRepository.deleteById(id);
    }
}
