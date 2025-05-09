package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.FuelIssuanceType;
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
 * Service Implementation for managing {@link FuelIssuanceType}.
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
    public FuelIssuanceType save(FuelIssuanceType fuelIssuanceType) {
        LOG.debug("Request to save FuelIssuanceType : {}", fuelIssuanceType);
        return fuelIssueanceTypeRepository.save(fuelIssuanceType);
    }

    @Override
    public FuelIssuanceType update(FuelIssuanceType fuelIssuanceType) {
        LOG.debug("Request to update FuelIssuanceType : {}", fuelIssuanceType);
        return fuelIssueanceTypeRepository.save(fuelIssuanceType);
    }

    @Override
    public Optional<FuelIssuanceType> partialUpdate(FuelIssuanceType fuelIssuanceType) {
        LOG.debug("Request to partially update FuelIssuanceType : {}", fuelIssuanceType);

        return fuelIssueanceTypeRepository
            .findById(fuelIssuanceType.getId())
            .map(existingFuelIssueanceType -> {
                if (fuelIssuanceType.getFuelIssueTypeId() != null) {
                    existingFuelIssueanceType.setFuelIssueTypeId(fuelIssuanceType.getFuelIssueTypeId());
                }
                if (fuelIssuanceType.getFuelIssueType() != null) {
                    existingFuelIssueanceType.setFuelIssueType(fuelIssuanceType.getFuelIssueType());
                }

                return existingFuelIssueanceType;
            })
            .map(fuelIssueanceTypeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelIssuanceType> findAll(Pageable pageable) {
        LOG.debug("Request to get all FuelIssueanceTypes");
        return fuelIssueanceTypeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelIssuanceType> findOne(Long id) {
        LOG.debug("Request to get FuelIssuanceType : {}", id);
        return fuelIssueanceTypeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FuelIssuanceType : {}", id);
        fuelIssueanceTypeRepository.deleteById(id);
    }
}
