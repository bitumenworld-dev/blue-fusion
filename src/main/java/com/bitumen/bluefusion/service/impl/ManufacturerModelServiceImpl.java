package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.ManufacturerModel;
import com.bitumen.bluefusion.repository.ManufacturerModelRepository;
import com.bitumen.bluefusion.service.ManufacturerModelService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.ManufacturerModel}.
 */
@Service
@Transactional
public class ManufacturerModelServiceImpl implements ManufacturerModelService {

    private static final Logger LOG = LoggerFactory.getLogger(ManufacturerModelServiceImpl.class);

    private final ManufacturerModelRepository manufacturerModelRepository;

    public ManufacturerModelServiceImpl(ManufacturerModelRepository manufacturerModelRepository) {
        this.manufacturerModelRepository = manufacturerModelRepository;
    }

    @Override
    public ManufacturerModel save(ManufacturerModel manufacturerModel) {
        LOG.debug("Request to save ManufacturerModel : {}", manufacturerModel);
        return manufacturerModelRepository.save(manufacturerModel);
    }

    @Override
    public ManufacturerModel update(ManufacturerModel manufacturerModel) {
        LOG.debug("Request to update ManufacturerModel : {}", manufacturerModel);
        return manufacturerModelRepository.save(manufacturerModel);
    }

    @Override
    public Optional<ManufacturerModel> partialUpdate(ManufacturerModel manufacturerModel) {
        LOG.debug("Request to partially update ManufacturerModel : {}", manufacturerModel);

        return manufacturerModelRepository
            .findById(manufacturerModel.getId())
            .map(existingManufacturerModel -> {
                if (manufacturerModel.getModelId() != null) {
                    existingManufacturerModel.setModelId(manufacturerModel.getModelId());
                }
                if (manufacturerModel.getModelName() != null) {
                    existingManufacturerModel.setModelName(manufacturerModel.getModelName());
                }

                return existingManufacturerModel;
            })
            .map(manufacturerModelRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ManufacturerModel> findAll(Pageable pageable) {
        LOG.debug("Request to get all ManufacturerModels");
        return manufacturerModelRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ManufacturerModel> findOne(Long id) {
        LOG.debug("Request to get ManufacturerModel : {}", id);
        return manufacturerModelRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete ManufacturerModel : {}", id);
        manufacturerModelRepository.deleteById(id);
    }
}
