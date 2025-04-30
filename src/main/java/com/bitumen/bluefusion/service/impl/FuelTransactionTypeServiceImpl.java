package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.FuelTransactionType;
import com.bitumen.bluefusion.repository.FuelTransactionTypeRepository;
import com.bitumen.bluefusion.service.FuelTransactionTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.FuelTransactionType}.
 */
@Service
@Transactional
public class FuelTransactionTypeServiceImpl implements FuelTransactionTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTransactionTypeServiceImpl.class);

    private final FuelTransactionTypeRepository fuelTransactionTypeRepository;

    public FuelTransactionTypeServiceImpl(FuelTransactionTypeRepository fuelTransactionTypeRepository) {
        this.fuelTransactionTypeRepository = fuelTransactionTypeRepository;
    }

    @Override
    public FuelTransactionType save(FuelTransactionType fuelTransactionType) {
        LOG.debug("Request to save FuelTransactionType : {}", fuelTransactionType);
        return fuelTransactionTypeRepository.save(fuelTransactionType);
    }

    @Override
    public FuelTransactionType update(FuelTransactionType fuelTransactionType) {
        LOG.debug("Request to update FuelTransactionType : {}", fuelTransactionType);
        return fuelTransactionTypeRepository.save(fuelTransactionType);
    }

    @Override
    public Optional<FuelTransactionType> partialUpdate(FuelTransactionType fuelTransactionType) {
        LOG.debug("Request to partially update FuelTransactionType : {}", fuelTransactionType);

        return fuelTransactionTypeRepository
            .findById(fuelTransactionType.getId())
            .map(existingFuelTransactionType -> {
                if (fuelTransactionType.getFuelTransactionTypeId() != null) {
                    existingFuelTransactionType.setFuelTransactionTypeId(fuelTransactionType.getFuelTransactionTypeId());
                }
                if (fuelTransactionType.getFuelTransactionType() != null) {
                    existingFuelTransactionType.setFuelTransactionType(fuelTransactionType.getFuelTransactionType());
                }
                if (fuelTransactionType.getIsActive() != null) {
                    existingFuelTransactionType.setIsActive(fuelTransactionType.getIsActive());
                }

                return existingFuelTransactionType;
            })
            .map(fuelTransactionTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelTransactionType> findAll(Pageable pageable) {
        LOG.debug("Request to get all FuelTransactionTypes");
        return fuelTransactionTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelTransactionType> findOne(Long id) {
        LOG.debug("Request to get FuelTransactionType : {}", id);
        return fuelTransactionTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FuelTransactionType : {}", id);
        fuelTransactionTypeRepository.deleteById(id);
    }
}
