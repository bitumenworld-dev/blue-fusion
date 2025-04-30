package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.SiteContract;
import com.bitumen.bluefusion.repository.SiteContractRepository;
import com.bitumen.bluefusion.service.SiteContractService;
import com.bitumen.bluefusion.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.SiteContract}.
 */
@RestController
@RequestMapping("/api/site-contracts")
public class SiteContractResource {

    private static final Logger LOG = LoggerFactory.getLogger(SiteContractResource.class);

    private static final String ENTITY_NAME = "siteContract";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final SiteContractService siteContractService;

    private final SiteContractRepository siteContractRepository;

    public SiteContractResource(SiteContractService siteContractService, SiteContractRepository siteContractRepository) {
        this.siteContractService = siteContractService;
        this.siteContractRepository = siteContractRepository;
    }

    /**
     * {@code POST  /site-contracts} : Create a new siteContract.
     *
     * @param siteContract the siteContract to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new siteContract, or with status {@code 400 (Bad Request)} if the siteContract has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<SiteContract> createSiteContract(@RequestBody SiteContract siteContract) throws URISyntaxException {
        LOG.debug("REST request to save SiteContract : {}", siteContract);
        if (siteContract.getId() != null) {
            throw new BadRequestAlertException("A new siteContract cannot already have an ID", ENTITY_NAME, "idexists");
        }
        siteContract = siteContractService.save(siteContract);
        return ResponseEntity.created(new URI("/api/site-contracts/" + siteContract.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, siteContract.getId().toString()))
            .body(siteContract);
    }

    /**
     * {@code PUT  /site-contracts/:id} : Updates an existing siteContract.
     *
     * @param id the id of the siteContract to save.
     * @param siteContract the siteContract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteContract,
     * or with status {@code 400 (Bad Request)} if the siteContract is not valid,
     * or with status {@code 500 (Internal Server Error)} if the siteContract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<SiteContract> updateSiteContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteContract siteContract
    ) throws URISyntaxException {
        LOG.debug("REST request to update SiteContract : {}, {}", id, siteContract);
        if (siteContract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteContract.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        siteContract = siteContractService.update(siteContract);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteContract.getId().toString()))
            .body(siteContract);
    }

    /**
     * {@code PATCH  /site-contracts/:id} : Partial updates given fields of an existing siteContract, field will ignore if it is null
     *
     * @param id the id of the siteContract to save.
     * @param siteContract the siteContract to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated siteContract,
     * or with status {@code 400 (Bad Request)} if the siteContract is not valid,
     * or with status {@code 404 (Not Found)} if the siteContract is not found,
     * or with status {@code 500 (Internal Server Error)} if the siteContract couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<SiteContract> partialUpdateSiteContract(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody SiteContract siteContract
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update SiteContract partially : {}, {}", id, siteContract);
        if (siteContract.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, siteContract.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!siteContractRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<SiteContract> result = siteContractService.partialUpdate(siteContract);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, siteContract.getId().toString())
        );
    }

    /**
     * {@code GET  /site-contracts} : get all the siteContracts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of siteContracts in body.
     */
    @GetMapping("")
    public ResponseEntity<List<SiteContract>> getAllSiteContracts(@org.springdoc.core.annotations.ParameterObject Pageable pageable) {
        LOG.debug("REST request to get a page of SiteContracts");
        Page<SiteContract> page = siteContractService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /site-contracts/:id} : get the "id" siteContract.
     *
     * @param id the id of the siteContract to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the siteContract, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<SiteContract> getSiteContract(@PathVariable("id") Long id) {
        LOG.debug("REST request to get SiteContract : {}", id);
        Optional<SiteContract> siteContract = siteContractService.findOne(id);
        return ResponseUtil.wrapOrNotFound(siteContract);
    }

    /**
     * {@code DELETE  /site-contracts/:id} : delete the "id" siteContract.
     *
     * @param id the id of the siteContract to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSiteContract(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete SiteContract : {}", id);
        siteContractService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
