package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.ManufacturerModel;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.ManufacturerModel}.
 */
public interface ManufacturerModelService {
    /**
     * Save a manufacturerModel.
     *
     * @param manufacturerModel the entity to save.
     * @return the persisted entity.
     */
    ManufacturerModel save(ManufacturerModel manufacturerModel);

    /**
     * Updates a manufacturerModel.
     *
     * @param manufacturerModel the entity to update.
     * @return the persisted entity.
     */
    ManufacturerModel update(ManufacturerModel manufacturerModel);

    /**
     * Partially updates a manufacturerModel.
     *
     * @param manufacturerModel the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ManufacturerModel> partialUpdate(ManufacturerModel manufacturerModel);

    /**
     * Get all the manufacturerModels.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ManufacturerModel> findAll(Pageable pageable);

    /**
     * Get the "id" manufacturerModel.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ManufacturerModel> findOne(Long id);

    /**
     * Delete the "id" manufacturerModel.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
