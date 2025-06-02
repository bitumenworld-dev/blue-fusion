package com.bitumen.bluefusion.service.plantCategoryService;

import com.bitumen.bluefusion.service.plantCategoryService.dto.PlantCategoryRequest;
import com.bitumen.bluefusion.service.plantCategoryService.dto.PlantCategoryResponse;
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
    PlantCategoryResponse save(PlantCategoryRequest plantCategory);

    /**
     * Updates a plantCategory.
     *
     * @param plantCategory the entity to update.
     * @return the persisted entity.
     */
    PlantCategoryResponse update(Long id, PlantCategoryRequest plantCategory);

    /**
     * Partially updates a plantCategory.
     *
     * @param plantCategory the entity to update partially.
     * @return the persisted entity.
     */
    PlantCategoryResponse partialUpdate(Long id, PlantCategoryRequest plantCategory);

    /**
     * Get all the plantCategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlantCategoryResponse> findAll(Pageable pageable, String code, String description);

    /**
     * Get the "id" plantCategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    PlantCategoryResponse findOne(Long id);

    /**
     * Delete the "id" plantCategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
