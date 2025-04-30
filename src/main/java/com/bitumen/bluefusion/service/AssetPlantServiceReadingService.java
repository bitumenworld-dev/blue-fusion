package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.AssetPlantServiceReading}.
 */
public interface AssetPlantServiceReadingService {
    /**
     * Save a assetPlantServiceReading.
     *
     * @param assetPlantServiceReading the entity to save.
     * @return the persisted entity.
     */
    AssetPlantServiceReading save(AssetPlantServiceReading assetPlantServiceReading);

    /**
     * Updates a assetPlantServiceReading.
     *
     * @param assetPlantServiceReading the entity to update.
     * @return the persisted entity.
     */
    AssetPlantServiceReading update(AssetPlantServiceReading assetPlantServiceReading);

    /**
     * Partially updates a assetPlantServiceReading.
     *
     * @param assetPlantServiceReading the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssetPlantServiceReading> partialUpdate(AssetPlantServiceReading assetPlantServiceReading);

    /**
     * Get all the assetPlantServiceReadings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetPlantServiceReading> findAll(Pageable pageable);

    /**
     * Get the "id" assetPlantServiceReading.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetPlantServiceReading> findOne(Long id);

    /**
     * Delete the "id" assetPlantServiceReading.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
