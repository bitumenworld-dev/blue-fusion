package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.FuelIssueanceType;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.FuelIssueanceType}.
 */
public interface FuelIssueanceTypeService {
    /**
     * Save a fuelIssueanceType.
     *
     * @param fuelIssueanceType the entity to save.
     * @return the persisted entity.
     */
    FuelIssueanceType save(FuelIssueanceType fuelIssueanceType);

    /**
     * Updates a fuelIssueanceType.
     *
     * @param fuelIssueanceType the entity to update.
     * @return the persisted entity.
     */
    FuelIssueanceType update(FuelIssueanceType fuelIssueanceType);

    /**
     * Partially updates a fuelIssueanceType.
     *
     * @param fuelIssueanceType the entity to update partially.
     * @return the persisted entity.
     */
    Optional<FuelIssueanceType> partialUpdate(FuelIssueanceType fuelIssueanceType);

    /**
     * Get all the fuelIssueanceTypes.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<FuelIssueanceType> findAll(Pageable pageable);

    /**
     * Get the "id" fuelIssueanceType.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<FuelIssueanceType> findOne(Long id);

    /**
     * Delete the "id" fuelIssueanceType.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
