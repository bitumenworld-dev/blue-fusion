package com.bitumen.bluefusion.service;

import com.bitumen.bluefusion.domain.Site;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.Site}.
 */
public interface SiteService {
    /**
     * Save a site.
     *
     * @param site the entity to save.
     * @return the persisted entity.
     */
    Site save(Site site);

    /**
     * Updates a site.
     *
     * @param site the entity to update.
     * @return the persisted entity.
     */
    Site update(Site site);

    /**
     * Partially updates a site.
     *
     * @param site the entity to update partially.
     * @return the persisted entity.
     */
    Optional<Site> partialUpdate(Site site);

    /**
     * Get all the sites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<Site> findAll(Pageable pageable);

    /**
     * Get the "id" site.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    Optional<Site> findOne(Long id);

    /**
     * Delete the "id" site.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
