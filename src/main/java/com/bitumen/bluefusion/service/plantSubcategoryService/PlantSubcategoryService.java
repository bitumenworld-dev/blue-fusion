package com.bitumen.bluefusion.service.plantSubcategoryService;

import com.bitumen.bluefusion.domain.PlantSubcategory;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategoryRequest;
import com.bitumen.bluefusion.service.plantSubcategoryService.dto.PlantSubcategoryResponse;
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
    PlantSubcategoryResponse save(PlantSubcategoryRequest plantSubcategory);

    /**
     * Updates a plantSubcategory.
     *
     * @param plantSubcategory the entity to update.
     * @return the persisted entity.
     */
    PlantSubcategoryResponse update(Long subcategoryId, PlantSubcategoryRequest plantSubcategory);

    /**
     * Partially updates a plantSubcategory.
     *
     * @param plantSubcategory the entity to update partially.
     * @return the persisted entity.
     */
    PlantSubcategoryResponse partialUpdate(Long subcategoryId, PlantSubcategoryRequest plantSubcategory);

    /**
     * Get all the plantSubcategories.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<PlantSubcategoryResponse> findAll(Pageable pageable, String plantSubcategoryCode, String planSubcategoryDescription);

    /**
     * Get the "id" plantSubcategory.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    PlantSubcategoryResponse findOne(Long id);

    /**
     * Delete the "id" plantSubcategory.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
