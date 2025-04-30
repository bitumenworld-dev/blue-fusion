package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.FuelPump;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.FuelPump}.
 */
public interface FuelPumpService {
    /**
     * Save a fuelPump.
     *
     * @param fuelPump the entity to save.
     * @return the persisted entity.
     */
    FuelPump save(FuelPump fuelPump);

    /**
     * Updates a fuelPump.
     *
     * @param fuelPump the entity to update.
     * @return the persisted entity.
     */
    FuelPump update(FuelPump fuelPump);

    /**
     * Partially updates a fuelPump.
     *
     * @param fuelPump the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelPump> partialUpdate(FuelPump fuelPump);

    /**
     * Get all the fuelPumps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelPump> findAll(Pageable pageable);

    /**
     * Get the "id" fuelPump.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelPump> findOne(Long id);

    /**
     * Delete the "id" fuelPump.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
