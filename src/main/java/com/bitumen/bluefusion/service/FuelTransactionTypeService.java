package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.FuelTransactionType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.FuelTransactionType}.
 */
public interface FuelTransactionTypeService {
    /**
     * Save a fuelTransactionType.
     *
     * @param fuelTransactionType the entity to save.
     * @return the persisted entity.
     */
    FuelTransactionType save(FuelTransactionType fuelTransactionType);

    /**
     * Updates a fuelTransactionType.
     *
     * @param fuelTransactionType the entity to update.
     * @return the persisted entity.
     */
    FuelTransactionType update(FuelTransactionType fuelTransactionType);

    /**
     * Partially updates a fuelTransactionType.
     *
     * @param fuelTransactionType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelTransactionType> partialUpdate(FuelTransactionType fuelTransactionType);

    /**
     * Get all the fuelTransactionTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelTransactionType> findAll(Pageable pageable);

    /**
     * Get the "id" fuelTransactionType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelTransactionType> findOne(Long id);

    /**
     * Delete the "id" fuelTransactionType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
