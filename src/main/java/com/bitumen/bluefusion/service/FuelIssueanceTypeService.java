package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.FuelIssuanceType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link FuelIssuanceType}.
 */
public interface FuelIssueanceTypeService {
    /**
     * Save a fuelIssuanceType.
     *
     * @param fuelIssuanceType the entity to save.
     * @return the persisted entity.
     */
    FuelIssuanceType save(FuelIssuanceType fuelIssuanceType);

    /**
     * Updates a fuelIssuanceType.
     *
     * @param fuelIssuanceType the entity to update.
     * @return the persisted entity.
     */
    FuelIssuanceType update(FuelIssuanceType fuelIssuanceType);

    /**
     * Partially updates a fuelIssuanceType.
     *
     * @param fuelIssuanceType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelIssuanceType> partialUpdate(FuelIssuanceType fuelIssuanceType);

    /**
     * Get all the fuelIssueanceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelIssuanceType> findAll(Pageable pageable);

    /**
     * Get the "id" fuelIssueanceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelIssuanceType> findOne(Long id);

    /**
     * Delete the "id" fuelIssueanceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
