package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.SiteContract;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.SiteContract}.
 */
public interface SiteContractService {
    /**
     * Save a siteContract.
     *
     * @param siteContract the entity to save.
     * @return the persisted entity.
     */
    SiteContract save(SiteContract siteContract);

    /**
     * Updates a siteContract.
     *
     * @param siteContract the entity to update.
     * @return the persisted entity.
     */
    SiteContract update(SiteContract siteContract);

    /**
     * Partially updates a siteContract.
     *
     * @param siteContract the entity to update partially.
     * @return the persisted entity.
     */
    Optional<SiteContract> partialUpdate(SiteContract siteContract);

    /**
     * Get all the siteContracts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteContract> findAll(Pageable pageable);

    /**
     * Get the "id" siteContract.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<SiteContract> findOne(Long id);

    /**
     * Delete the "id" siteContract.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
