package com.bitumen.bluefusion.service.fuelPump;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.service.fuelPump.dto.FuelPumpRequest;
import com.bitumen.bluefusion.service.fuelPump.dto.FuelPumpResponse;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FuelPump}.
 */
public interface FuelPumpService {
    /**
     * Save a fuelPump.
     *
     * @param fuelPumpRequest the entity to save.
     * @return the persisted entity.
     */
    FuelPumpResponse save(FuelPumpRequest fuelPumpRequest);

    /**
     * Updates a fuelPump.
     *
     * @param fuelPumpRequest the entity to update.
     * @return the persisted entity.
     */
    FuelPumpResponse update(Long id, FuelPumpRequest fuelPumpRequest);

    /**
     * Partially updates a fuelPump.
     *
     * @param fuelPumpRequest the entity to update partially.
     * @return the persisted entity.
     */
    FuelPumpResponse partialUpdate(Long id, FuelPumpRequest fuelPumpRequest);

    /**
     * Get all the fuelPumps.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelPumpResponse> findAll(Pageable pageable, Long storageUnitId, Boolean isActive, String fuelPumpCode);

    /**
     * Get the "id" fuelPump.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    FuelPumpResponse findOne(Long id);

    /**
     * Delete the "id" fuelPump.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
