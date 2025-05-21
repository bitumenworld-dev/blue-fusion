package com.bitumen.bluefusion.service.site;

import com.bitumen.bluefusion.service.site.dto.SiteRequest;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import java.io.IOException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

/**
 * Service Interface for managing {@link com.bitumen.bluefusion.domain.Site}.
 */
public interface SiteService {
    /**
     * Save a site
     * @param site the entity to save.
     * @return the persisted entity.
     */
    SiteResponse save(SiteRequest site) throws IOException;

    /**
     *
     * @param siteId id of the entity
     * @param file site image file
     * @return  the persisted entity.
     * @throws IOException thrown exception when failing to convert file to byte array
     */
    SiteResponse uploadSiteImage(Long siteId, MultipartFile file) throws IOException;

    /**
     * Updates a site.
     * @param site the entity to update.
     * @return the persisted entity.
     */
    SiteResponse update(Long siteId, SiteRequest site) throws IOException;

    /**
     * Partially updates a site.
     * @param site the entity to update partially.
     * @return the persisted entity.
     */
    SiteResponse partialUpdate(Long siteId, SiteRequest site);

    /**
     * Get all the sites.
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    Page<SiteResponse> findAll(Pageable pageable);

    /**
     * Get the "id" site.
     * @param id the id of the entity.
     * @return the entity.
     */
    SiteResponse findOne(Long id);

    /**
     * Delete the "id" site.
     * @param id the id of the entity.
     */
    void delete(Long id);
}
