package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.FuelIssueanceType;
import com.bitumen.bluefusion.repository.FuelIssueanceTypeRepository;
import com.bitumen.bluefusion.service.FuelIssueanceTypeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.FuelIssueanceType}.
 */
@Service
@Transactional
public class FuelIssueanceTypeServiceImpl implements FuelIssueanceTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(FuelIssueanceTypeServiceImpl.class);

    private final FuelIssueanceTypeRepository fuelIssueanceTypeRepository;

    public FuelIssueanceTypeServiceImpl(FuelIssueanceTypeRepository fuelIssueanceTypeRepository) {
        this.fuelIssueanceTypeRepository = fuelIssueanceTypeRepository;
    }

    @Override
    public FuelIssueanceType save(FuelIssueanceType fuelIssueanceType) {
        LOG.debug("Request to save FuelIssueanceType : {}", fuelIssueanceType);
        return fuelIssueanceTypeRepository.save(fuelIssueanceType);
    }

    @Override
    public FuelIssueanceType update(FuelIssueanceType fuelIssueanceType) {
        LOG.debug("Request to update FuelIssueanceType : {}", fuelIssueanceType);
        return fuelIssueanceTypeRepository.save(fuelIssueanceType);
    }

    @Override
    public Optional<FuelIssueanceType> partialUpdate(FuelIssueanceType fuelIssueanceType) {
        LOG.debug("Request to partially update FuelIssueanceType : {}", fuelIssueanceType);

        return fuelIssueanceTypeRepository
            .findById(fuelIssueanceType.getId())
            .map(existingFuelIssueanceType -> {
                if (fuelIssueanceType.getFuelIssueTypeId() != null) {
                    existingFuelIssueanceType.setFuelIssueTypeId(fuelIssueanceType.getFuelIssueTypeId());
                }
                if (fuelIssueanceType.getFuelIssueType() != null) {
                    existingFuelIssueanceType.setFuelIssueType(fuelIssueanceType.getFuelIssueType());
                }

                return existingFuelIssueanceType;
            })
            .map(fuelIssueanceTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelIssueanceType> findAll(Pageable pageable) {
        LOG.debug("Request to get all FuelIssueanceTypes");
        return fuelIssueanceTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelIssueanceType> findOne(Long id) {
        LOG.debug("Request to get FuelIssueanceType : {}", id);
        return fuelIssueanceTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FuelIssueanceType : {}", id);
        fuelIssueanceTypeRepository.deleteById(id);
    }
}
