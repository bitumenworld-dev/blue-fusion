package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.PlantSubcategory;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.PlantSubcategory}.
 */
public interface PlantSubcategoryService {
    /**
     * Save a plantSubcategory.
     *
     * @param plantSubcategory the entity to save.
     * @return the persisted entity.
     */
    PlantSubcategory save(PlantSubcategory plantSubcategory);

    /**
     * Updates a plantSubcategory.
     *
     * @param plantSubcategory the entity to update.
     * @return the persisted entity.
     */
    PlantSubcategory update(PlantSubcategory plantSubcategory);

    /**
     * Partially updates a plantSubcategory.
     *
     * @param plantSubcategory the entity to update partially.
     * @return the persisted entity.
     */
    Optional<PlantSubcategory> partialUpdate(PlantSubcategory plantSubcategory);

    /**
     * Get all the plantSubcategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlantSubcategory> findAll(Pageable pageable);

    /**
     * Get the "id" plantSubcategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<PlantSubcategory> findOne(Long id);

    /**
     * Delete the "id" plantSubcategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
