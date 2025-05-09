package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
import com.bitumen.bluefusion.service.FuelTransactionHeaderService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.FuelTransactionHeader}.
 */
@RestController
@RequestMapping("/api/fuel-transaction-headers")
public class FuelTransactionHeaderResource {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTransactionHeaderResource.class);

    private static final String ENTITY_NAME = "fuelTransactionHeader";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTransactionHeaderService fuelTransactionHeaderService;

    private final FuelTransactionHeaderRepository fuelTransactionHeaderRepository;

    public FuelTransactionHeaderResource(
        FuelTransactionHeaderService fuelTransactionHeaderService,
        FuelTransactionHeaderRepository fuelTransactionHeaderRepository
    ) {
        this.fuelTransactionHeaderService = fuelTransactionHeaderService;
        this.fuelTransactionHeaderRepository = fuelTransactionHeaderRepository;
    }

    /**
     * {@code POST  /fuel-transaction-headers} : Create a new fuelTransactionHeader.
     *
     * @param fuelTransactionHeader the fuelTransactionHeader to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelTransactionHeader, or with status {@code 400 (Bad Request)} if the fuelTransactionHeader has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelTransactionHeader> createFuelTransactionHeader(@RequestBody FuelTransactionHeader fuelTransactionHeader)
        throws URISyntaxException {
        LOG.debug("REST request to save FuelTransactionHeader : {}", fuelTransactionHeader);
        if (fuelTransactionHeader.getFuelTransactionHeaderId() != null) {
            throw new BadRequestAlertException("A new fuelTransactionHeader cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelTransactionHeader = fuelTransactionHeaderService.save(fuelTransactionHeader);
        return ResponseEntity.created(new URI("/api/fuel-transaction-headers/" + fuelTransactionHeader.getFuelTransactionHeaderId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    fuelTransactionHeader.getFuelTransactionHeaderId().toString()
                )
            )
            .body(fuelTransactionHeader);
    }

    /**
     * {@code PUT  /fuel-transaction-headers/:id} : Updates an existing fuelTransactionHeader.
     *
     * @param id the id of the fuelTransactionHeader to save.
     * @param fuelTransactionHeader the fuelTransactionHeader to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTransactionHeader,
     * or with status {@code 400 (Bad Request)} if the fuelTransactionHeader is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelTransactionHeader couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelTransactionHeader> updateFuelTransactionHeader(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTransactionHeader fuelTransactionHeader
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelTransactionHeader : {}, {}", id, fuelTransactionHeader);
        if (fuelTransactionHeader.getFuelTransactionHeaderId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTransactionHeader.getFuelTransactionHeaderId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTransactionHeaderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "id not found");
        }

        fuelTransactionHeader = fuelTransactionHeaderService.update(fuelTransactionHeader);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    fuelTransactionHeader.getFuelTransactionHeaderId().toString()
                )
            )
            .body(fuelTransactionHeader);
    }

    /**
     * {@code PATCH  /fuel-transaction-headers/:id} : Partial updates given fields of an existing fuelTransactionHeader, field will ignore if it is null
     *
     * @param id the id of the fuelTransactionHeader to save.
     * @param fuelTransactionHeader the fuelTransactionHeader to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTransactionHeader,
     * or with status {@code 400 (Bad Request)} if the fuelTransactionHeader is not valid,
     * or with status {@code 404 (Not Found)} if the fuelTransactionHeader is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelTransactionHeader couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelTransactionHeader> partialUpdateFuelTransactionHeader(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTransactionHeader fuelTransactionHeader
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelTransactionHeader partially : {}, {}", id, fuelTransactionHeader);
        if (fuelTransactionHeader.getFuelTransactionHeaderId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTransactionHeader.getFuelTransactionHeaderId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTransactionHeaderRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelTransactionHeader> result = fuelTransactionHeaderService.partialUpdate(fuelTransactionHeader);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                fuelTransactionHeader.getFuelTransactionHeaderId().toString()
            )
        );
    }

    /**
     * {@code GET  /fuel-transaction-headers} : get all the fuelTransactionHeaders.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTransactionHeaders in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelTransactionHeader>> getAllFuelTransactionHeaders(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FuelTransactionHeaders");
        Page<FuelTransactionHeader> page = fuelTransactionHeaderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-transaction-headers/:id} : get the "id" fuelTransactionHeader.
     *
     * @param id the id of the fuelTransactionHeader to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelTransactionHeader, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuelTransactionHeader> getFuelTransactionHeader(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelTransactionHeader : {}", id);
        Optional<FuelTransactionHeader> fuelTransactionHeader = fuelTransactionHeaderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelTransactionHeader);
    }

    /**
     * {@code DELETE  /fuel-transaction-headers/:id} : delete the "id" fuelTransactionHeader.
     *
     * @param id the id of the fuelTransactionHeader to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelTransactionHeader(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FuelTransactionHeader : {}", id);
        fuelTransactionHeaderService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
