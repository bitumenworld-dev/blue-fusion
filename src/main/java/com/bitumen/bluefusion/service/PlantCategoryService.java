package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.PlantCategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.PlantCategory}.
 */
public interface PlantCategoryService {
    /**
     * Save a plantCategory.
     *
     * @param plantCategory the entity to save.
     * @return the persisted entity.
     */
    PlantCategory save(PlantCategory plantCategory);

    /**
     * Updates a plantCategory.
     *
     * @param plantCategory the entity to update.
     * @return the persisted entity.
     */
    PlantCategory update(PlantCategory plantCategory);

    /**
     * Partially updates a plantCategory.
     *
     * @param plantCategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlantCategory> partialUpdate(PlantCategory plantCategory);

    /**
     * Get all the plantCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlantCategory> findAll(Pageable pageable);

    /**
     * Get the "id" plantCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlantCategory> findOne(Long id);

    /**
     * Delete the "id" plantCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
