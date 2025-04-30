package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.PlantSubcategory;
import com.bitumen.bluefusion.repository.PlantSubcategoryRepository;
import com.bitumen.bluefusion.service.PlantSubcategoryService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.PlantSubcategory}.
 */
@Service
@Transactional
public class PlantSubcategoryServiceImpl implements PlantSubcategoryService {

    private static final Logger LOG = LoggerFactory.getLogger(PlantSubcategoryServiceImpl.class);

    private final PlantSubcategoryRepository plantSubcategoryRepository;

    public PlantSubcategoryServiceImpl(PlantSubcategoryRepository plantSubcategoryRepository) {
        this.plantSubcategoryRepository = plantSubcategoryRepository;
    }

    @Override
    public PlantSubcategory save(PlantSubcategory plantSubcategory) {
        LOG.debug("Request to save PlantSubcategory : {}", plantSubcategory);
        return plantSubcategoryRepository.save(plantSubcategory);
    }

    @Override
    public PlantSubcategory update(PlantSubcategory plantSubcategory) {
        LOG.debug("Request to update PlantSubcategory : {}", plantSubcategory);
        return plantSubcategoryRepository.save(plantSubcategory);
    }

    @Override
    public Optional<PlantSubcategory> partialUpdate(PlantSubcategory plantSubcategory) {
        LOG.debug("Request to partially update PlantSubcategory : {}", plantSubcategory);

        return plantSubcategoryRepository
            .findById(plantSubcategory.getId())
            .map(existingPlantSubcategory -> {
                if (plantSubcategory.getPlantSubcategoryId() != null) {
                    existingPlantSubcategory.setPlantSubcategoryId(plantSubcategory.getPlantSubcategoryId());
                }
                if (plantSubcategory.getPlantSubcategoryCode() != null) {
                    existingPlantSubcategory.setPlantSubcategoryCode(plantSubcategory.getPlantSubcategoryCode());
                }
                if (plantSubcategory.getPlantSubcategoryName() != null) {
                    existingPlantSubcategory.setPlantSubcategoryName(plantSubcategory.getPlantSubcategoryName());
                }
                if (plantSubcategory.getPlantSubcategoryDescription() != null) {
                    existingPlantSubcategory.setPlantSubcategoryDescription(plantSubcategory.getPlantSubcategoryDescription());
                }
                if (plantSubcategory.getIsAudited() != null) {
                    existingPlantSubcategory.setIsAudited(plantSubcategory.getIsAudited());
                }

                return existingPlantSubcategory;
            })
            .map(plantSubcategoryRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<PlantSubcategory> findAll(Pageable pageable) {
        LOG.debug("Request to get all PlantSubcategories");
        return plantSubcategoryRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlantSubcategory> findOne(Long id) {
        LOG.debug("Request to get PlantSubcategory : {}", id);
        return plantSubcategoryRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete PlantSubcategory : {}", id);
        plantSubcategoryRepository.deleteById(id);
    }
}
