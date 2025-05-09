package com.bitumen.bluefusion.service.site;

import com.bitumen.bluefusion.service.site.dto.SiteRequest;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
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
    SiteResponse save(SiteRequest site);

    /**
     * Updates a site.
     *
     * @param site the entity to update.
     * @return the persisted entity.
     */
    SiteResponse update(Long siteId, SiteRequest site);

    /**
     * Partially updates a site.
     *
     * @param site the entity to update partially.
     * @return the persisted entity.
     */
    SiteResponse partialUpdate(Long siteId, SiteRequest site);

    /**
     * Get all the sites.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteResponse> findAll(Pageable pageable);

    /**
     * Get the "id" site.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    SiteResponse findOne(Long id);

    /**
     * Delete the "id" site.
     *
     * @param id the id of the entity.
     */
    void delete(Long id);
}
