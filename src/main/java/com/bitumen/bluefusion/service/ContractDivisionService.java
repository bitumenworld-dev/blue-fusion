package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.ContractDivision;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.ContractDivision}.
 */
public interface ContractDivisionService {
    /**
     * Save a contractDivision.
     *
     * @param contractDivision the entity to save.
     * @return the persisted entity.
     */
    ContractDivision save(ContractDivision contractDivision);

    /**
     * Updates a contractDivision.
     *
     * @param contractDivision the entity to update.
     * @return the persisted entity.
     */
    ContractDivision update(ContractDivision contractDivision);

    /**
     * Partially updates a contractDivision.
     *
     * @param contractDivision the entity to update partially.
     * @return the persisted entity.
     */
    Optional<ContractDivision> partialUpdate(ContractDivision contractDivision);

    /**
     * Get all the contractDivisions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<ContractDivision> findAll(Pageable pageable);

    /**
     * Get the "id" contractDivision.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<ContractDivision> findOne(Long id);

    /**
     * Delete the "id" contractDivision.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
