package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.Manufacturer;
import com.bitumen.bluefusion.repository.ManufacturerRepository;
import com.bitumen.bluefusion.service.ManufacturerService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.Manufacturer}.
 */
@Service
@Transactional
public class ManufacturerServiceImpl implements ManufacturerService {

    private static final Logger LOG = LoggerFactory.getLogger(ManufacturerServiceImpl.class);

    private final ManufacturerRepository manufacturerRepository;

    public ManufacturerServiceImpl(ManufacturerRepository manufacturerRepository) {
        this.manufacturerRepository = manufacturerRepository;
    }

    @Override
    public Manufacturer save(Manufacturer manufacturer) {
        LOG.debug("Request to save Manufacturer : {}", manufacturer);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Manufacturer update(Manufacturer manufacturer) {
        LOG.debug("Request to update Manufacturer : {}", manufacturer);
        return manufacturerRepository.save(manufacturer);
    }

    @Override
    public Optional<Manufacturer> partialUpdate(Manufacturer manufacturer) {
        LOG.debug("Request to partially update Manufacturer : {}", manufacturer);

        return manufacturerRepository
            .findById(manufacturer.getId())
            .map(existingManufacturer -> {
                if (manufacturer.getManufacturerId() != null) {
                    existingManufacturer.setManufacturerId(manufacturer.getManufacturerId());
                }
                if (manufacturer.getManufacturerName() != null) {
                    existingManufacturer.setManufacturerName(manufacturer.getManufacturerName());
                }

                return existingManufacturer;
            })
            .map(manufacturerRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Manufacturer> findAll(Pageable pageable) {
        LOG.debug("Request to get all Manufacturers");
        return manufacturerRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Manufacturer> findOne(Long id) {
        LOG.debug("Request to get Manufacturer : {}", id);
        return manufacturerRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Manufacturer : {}", id);
        manufacturerRepository.deleteById(id);
    }
}
