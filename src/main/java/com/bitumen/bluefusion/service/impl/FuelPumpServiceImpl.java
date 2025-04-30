package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.repository.FuelPumpRepository;
import com.bitumen.bluefusion.service.FuelPumpService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.FuelPump}.
 */
@Service
@Transactional
public class FuelPumpServiceImpl implements FuelPumpService {

    private static final Logger LOG = LoggerFactory.getLogger(FuelPumpServiceImpl.class);

    private final FuelPumpRepository fuelPumpRepository;

    public FuelPumpServiceImpl(FuelPumpRepository fuelPumpRepository) {
        this.fuelPumpRepository = fuelPumpRepository;
    }

    @Override
    public FuelPump save(FuelPump fuelPump) {
        LOG.debug("Request to save FuelPump : {}", fuelPump);
        return fuelPumpRepository.save(fuelPump);
    }

    @Override
    public FuelPump update(FuelPump fuelPump) {
        LOG.debug("Request to update FuelPump : {}", fuelPump);
        return fuelPumpRepository.save(fuelPump);
    }

    @Override
    public Optional<FuelPump> partialUpdate(FuelPump fuelPump) {
        LOG.debug("Request to partially update FuelPump : {}", fuelPump);

        return fuelPumpRepository
            .findById(fuelPump.getId())
            .map(existingFuelPump -> {
                if (fuelPump.getFuelPumpId() != null) {
                    existingFuelPump.setFuelPumpId(fuelPump.getFuelPumpId());
                }
                if (fuelPump.getFuelPumpNumber() != null) {
                    existingFuelPump.setFuelPumpNumber(fuelPump.getFuelPumpNumber());
                }
                if (fuelPump.getCurrentStorageUnitId() != null) {
                    existingFuelPump.setCurrentStorageUnitId(fuelPump.getCurrentStorageUnitId());
                }

                return existingFuelPump;
            })
            .map(fuelPumpRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<FuelPump> findAll(Pageable pageable) {
        LOG.debug("Request to get all FuelPumps");
        return fuelPumpRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<FuelPump> findOne(Long id) {
        LOG.debug("Request to get FuelPump : {}", id);
        return fuelPumpRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete FuelPump : {}", id);
        fuelPumpRepository.deleteById(id);
    }
}
