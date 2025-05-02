package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.site.SiteService;
import com.bitumen.bluefusion.service.site.dto.SiteRequest;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bitumen.bluefusion.domain.Site}.
 */
@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sites")
public class SiteResource {

    private static final String ENTITY_NAME = "site";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteService siteService;

    /**
     * {@code POST  /sites} : Create a new site.
     *
     * @param siteRequest the site to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new site, or with status {@code 400 (Bad Request)} if the site has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SiteResponse> createSite(@RequestBody SiteRequest siteRequest) throws URISyntaxException {
        SiteResponse siteResponse = siteService.save(siteRequest);
        return ResponseEntity.created(new URI("/api/sites/" + siteResponse.siteId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, siteResponse.siteId().toString()))
            .body(siteResponse);
    }

    /**
     * {@code PUT  /sites/:id} : Updates an existing site.
     *
     * @param id the id of the site to save.
     * @param siteRequest the site to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated site,
     * or with status {@code 400 (Bad Request)} if the site is not valid,
     * or with status {@code 500 (Internal Server Error)} if the site couldn't be updated.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SiteResponse> updateSite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteRequest siteRequest
    ) {
        SiteResponse siteResponse = siteService.update(id, siteRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteResponse.siteId().toString()))
            .body(siteResponse);
    }

    /**
     * {@code PATCH  /sites/:id} : Partial updates given fields of an existing site, field will ignore if it is null
     *
     * @param id the id of the site to save.
     * @param siteRequest the site to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated site,
     * or with status {@code 400 (Bad Request)} if the site is not valid,
     * or with status {@code 404 (Not Found)} if the site is not found,
     * or with status {@code 500 (Internal Server Error)} if the site couldn't be updated.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SiteResponse> partialUpdateSite(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteRequest siteRequest
    ) {
        SiteResponse result = siteService.partialUpdate(id, siteRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, result.siteId().toString())
        );
    }

    /**
     * {@code GET  /sites} : get all the sites.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of sites in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SiteResponse>> getAllSites(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        Page<SiteResponse> page = siteService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /sites/:id} : get the "id" site.
     *
     * @param id the id of the site to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the site, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SiteResponse> getSite(@PathVariable("id") Long id) {
        SiteResponse site = siteService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.of(site));
    }

    /**
     * {@code DELETE  /sites/:id} : delete the "id" site.
     *
     * @param id the id of the site to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSite(@PathVariable("id") Long id) {
        siteService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
