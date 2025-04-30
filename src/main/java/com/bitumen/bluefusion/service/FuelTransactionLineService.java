package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.FuelTransactionLine;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.FuelTransactionLine}.
 */
public interface FuelTransactionLineService {
    /**
     * Save a fuelTransactionLine.
     *
     * @param fuelTransactionLine the entity to save.
     * @return the persisted entity.
     */
    FuelTransactionLine save(FuelTransactionLine fuelTransactionLine);

    /**
     * Updates a fuelTransactionLine.
     *
     * @param fuelTransactionLine the entity to update.
     * @return the persisted entity.
     */
    FuelTransactionLine update(FuelTransactionLine fuelTransactionLine);

    /**
     * Partially updates a fuelTransactionLine.
     *
     * @param fuelTransactionLine the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelTransactionLine> partialUpdate(FuelTransactionLine fuelTransactionLine);

    /**
     * Get all the fuelTransactionLines.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelTransactionLine> findAll(Pageable pageable);

    /**
     * Get the "id" fuelTransactionLine.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelTransactionLine> findOne(Long id);

    /**
     * Delete the "id" fuelTransactionLine.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
