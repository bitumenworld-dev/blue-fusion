package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.FuelTransactionType;
import com.bitumen.bluefusion.repository.FuelTransactionTypeRepository;
import com.bitumen.bluefusion.service.FuelTransactionTypeService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.FuelTransactionType}.
 */
@RestController
@RequestMapping("/api/fuel-transaction-types")
public class FuelTransactionTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTransactionTypeResource.class);

    private static final String ENTITY_NAME = "fuelTransactionType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTransactionTypeService fuelTransactionTypeService;

    private final FuelTransactionTypeRepository fuelTransactionTypeRepository;

    public FuelTransactionTypeResource(
        FuelTransactionTypeService fuelTransactionTypeService,
        FuelTransactionTypeRepository fuelTransactionTypeRepository
    ) {
        this.fuelTransactionTypeService = fuelTransactionTypeService;
        this.fuelTransactionTypeRepository = fuelTransactionTypeRepository;
    }

    /**
     * {@code POST  /fuel-transaction-types} : Create a new fuelTransactionType.
     *
     * @param fuelTransactionType the fuelTransactionType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelTransactionType, or with status {@code 400 (Bad Request)} if the fuelTransactionType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelTransactionType> createFuelTransactionType(@RequestBody FuelTransactionType fuelTransactionType)
        throws URISyntaxException {
        LOG.debug("REST request to save FuelTransactionType : {}", fuelTransactionType);
        if (fuelTransactionType.getId() != null) {
            throw new BadRequestAlertException("A new fuelTransactionType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelTransactionType = fuelTransactionTypeService.save(fuelTransactionType);
        return ResponseEntity.created(new URI("/api/fuel-transaction-types/" + fuelTransactionType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fuelTransactionType.getId().toString()))
            .body(fuelTransactionType);
    }

    /**
     * {@code PUT  /fuel-transaction-types/:id} : Updates an existing fuelTransactionType.
     *
     * @param id the id of the fuelTransactionType to save.
     * @param fuelTransactionType the fuelTransactionType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTransactionType,
     * or with status {@code 400 (Bad Request)} if the fuelTransactionType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelTransactionType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelTransactionType> updateFuelTransactionType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTransactionType fuelTransactionType
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelTransactionType : {}, {}", id, fuelTransactionType);
        if (fuelTransactionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTransactionType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTransactionTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fuelTransactionType = fuelTransactionTypeService.update(fuelTransactionType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelTransactionType.getId().toString()))
            .body(fuelTransactionType);
    }

    /**
     * {@code PATCH  /fuel-transaction-types/:id} : Partial updates given fields of an existing fuelTransactionType, field will ignore if it is null
     *
     * @param id the id of the fuelTransactionType to save.
     * @param fuelTransactionType the fuelTransactionType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTransactionType,
     * or with status {@code 400 (Bad Request)} if the fuelTransactionType is not valid,
     * or with status {@code 404 (Not Found)} if the fuelTransactionType is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelTransactionType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelTransactionType> partialUpdateFuelTransactionType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTransactionType fuelTransactionType
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelTransactionType partially : {}, {}", id, fuelTransactionType);
        if (fuelTransactionType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTransactionType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTransactionTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelTransactionType> result = fuelTransactionTypeService.partialUpdate(fuelTransactionType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelTransactionType.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-transaction-types} : get all the fuelTransactionTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTransactionTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelTransactionType>> getAllFuelTransactionTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FuelTransactionTypes");
        Page<FuelTransactionType> page = fuelTransactionTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-transaction-types/:id} : get the "id" fuelTransactionType.
     *
     * @param id the id of the fuelTransactionType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelTransactionType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuelTransactionType> getFuelTransactionType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelTransactionType : {}", id);
        Optional<FuelTransactionType> fuelTransactionType = fuelTransactionTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelTransactionType);
    }

    /**
     * {@code DELETE  /fuel-transaction-types/:id} : delete the "id" fuelTransactionType.
     *
     * @param id the id of the fuelTransactionType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelTransactionType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FuelTransactionType : {}", id);
        fuelTransactionTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
