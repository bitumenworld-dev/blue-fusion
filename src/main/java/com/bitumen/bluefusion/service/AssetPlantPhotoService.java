package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.AssetPlantPhoto;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.AssetPlantPhoto}.
 */
public interface AssetPlantPhotoService {
    /**
     * Save a assetPlantPhoto.
     *
     * @param assetPlantPhoto the entity to save.
     * @return the persisted entity.
     */
    AssetPlantPhoto save(AssetPlantPhoto assetPlantPhoto);

    /**
     * Updates a assetPlantPhoto.
     *
     * @param assetPlantPhoto the entity to update.
     * @return the persisted entity.
     */
    AssetPlantPhoto update(AssetPlantPhoto assetPlantPhoto);

    /**
     * Partially updates a assetPlantPhoto.
     *
     * @param assetPlantPhoto the entity to update partially.
     * @return the persisted entity.
     */
    Optional<AssetPlantPhoto> partialUpdate(AssetPlantPhoto assetPlantPhoto);

    /**
     * Get all the assetPlantPhotos.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<AssetPlantPhoto> findAll(Pageable pageable);

    /**
     * Get the "id" assetPlantPhoto.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<AssetPlantPhoto> findOne(Long id);

    /**
     * Delete the "id" assetPlantPhoto.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
