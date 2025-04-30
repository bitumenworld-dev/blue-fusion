package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.PlantCategory;
import com.bitumen.bluefusion.repository.PlantCategoryRepository;
import com.bitumen.bluefusion.service.PlantCategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.PlantCategory}.
 */
@Service
@Transactional
public class PlantCategoryServiceImpl implements PlantCategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(PlantCategoryServiceImpl.class);

    private final PlantCategoryRepository plantCategoryRepository;

    public PlantCategoryServiceImpl(PlantCategoryRepository plantCategoryRepository) {
        this.plantCategoryRepository = plantCategoryRepository;
    }

    @Override
    public PlantCategory save(PlantCategory plantCategory) {
        LOG.debug("Request to save PlantCategory : {}", plantCategory);
        return plantCategoryRepository.save(plantCategory);
    }

    @Override
    public PlantCategory update(PlantCategory plantCategory) {
        LOG.debug("Request to update PlantCategory : {}", plantCategory);
        return plantCategoryRepository.save(plantCategory);
    }

    @Override
    public Optional<PlantCategory> partialUpdate(PlantCategory plantCategory) {
        LOG.debug("Request to partially update PlantCategory : {}", plantCategory);

        return plantCategoryRepository
            .findById(plantCategory.getId())
            .map(existingPlantCategory -> {
                if (plantCategory.getPlantCategoryId() != null) {
                    existingPlantCategory.setPlantCategoryId(plantCategory.getPlantCategoryId());
                }
                if (plantCategory.getPlantCategoryCode() != null) {
                    existingPlantCategory.setPlantCategoryCode(plantCategory.getPlantCategoryCode());
                }
                if (plantCategory.getPlantCategoryName() != null) {
                    existingPlantCategory.setPlantCategoryName(plantCategory.getPlantCategoryName());
                }
                if (plantCategory.getPlantCategoryDescription() != null) {
                    existingPlantCategory.setPlantCategoryDescription(plantCategory.getPlantCategoryDescription());
                }
                if (plantCategory.getIsAudited() != null) {
                    existingPlantCategory.setIsAudited(plantCategory.getIsAudited());
                }

                return existingPlantCategory;
            })
            .map(plantCategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantCategory> findAll(Pageable pageable) {
        LOG.debug("Request to get all PlantCategories");
        return plantCategoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlantCategory> findOne(Long id) {
        LOG.debug("Request to get PlantCategory : {}", id);
        return plantCategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PlantCategory : {}", id);
        plantCategoryRepository.deleteById(id);
    }
}
