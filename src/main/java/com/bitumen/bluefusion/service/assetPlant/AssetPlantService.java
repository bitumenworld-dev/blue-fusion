package com.bitumen.bluefusion.service.assetPlant;

import com.bitumen.bluefusion.domain.AssetPlant;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.AssetPlant}.
 */
public interface AssetPlantService {
    /**
     * Save a assetPlant.
     *
     * @param assetPlant the entity to save.
     * @return the persisted entity.
     */
    AssetPlant save(AssetPlant assetPlant);

    /**
     * Updates a assetPlant.
     *
     * @param assetPlant the entity to update.
     * @return the persisted entity.
     */
    AssetPlant update(AssetPlant assetPlant);

    /**
     * Partially updates a assetPlant.
     *
     * @param assetPlant the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssetPlant> partialUpdate(AssetPlant assetPlant);

    /**
     * Get all the assetPlants.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetPlant> findAll(Pageable pageable);

    /**
     * Get the "id" assetPlant.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetPlant> findOne(Long id);

    /**
     * Delete the "id" assetPlant.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
