package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.FuelTransactionHeader}.
 */
public interface FuelTransactionHeaderService {
    /**
     * Save a fuelTransactionHeader.
     *
     * @param fuelTransactionHeader the entity to save.
     * @return the persisted entity.
     */
    FuelTransactionHeader save(FuelTransactionHeader fuelTransactionHeader);

    /**
     * Updates a fuelTransactionHeader.
     *
     * @param fuelTransactionHeader the entity to update.
     * @return the persisted entity.
     */
    FuelTransactionHeader update(FuelTransactionHeader fuelTransactionHeader);

    /**
     * Partially updates a fuelTransactionHeader.
     *
     * @param fuelTransactionHeader the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelTransactionHeader> partialUpdate(FuelTransactionHeader fuelTransactionHeader);

    /**
     * Get all the fuelTransactionHeaders.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelTransactionHeader> findAll(Pageable pageable);

    /**
     * Get the "id" fuelTransactionHeader.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelTransactionHeader> findOne(Long id);

    /**
     * Delete the "id" fuelTransactionHeader.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
