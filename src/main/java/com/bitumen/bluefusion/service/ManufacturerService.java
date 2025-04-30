package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.Manufacturer;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.Manufacturer}.
 */
public interface ManufacturerService {
    /**
     * Save a manufacturer.
     *
     * @param manufacturer the entity to save.
     * @return the persisted entity.
     */
    Manufacturer save(Manufacturer manufacturer);

    /**
     * Updates a manufacturer.
     *
     * @param manufacturer the entity to update.
     * @return the persisted entity.
     */
    Manufacturer update(Manufacturer manufacturer);

    /**
     * Partially updates a manufacturer.
     *
     * @param manufacturer the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Manufacturer> partialUpdate(Manufacturer manufacturer);

    /**
     * Get all the manufacturers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Manufacturer> findAll(Pageable pageable);

    /**
     * Get the "id" manufacturer.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Manufacturer> findOne(Long id);

    /**
     * Delete the "id" manufacturer.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
