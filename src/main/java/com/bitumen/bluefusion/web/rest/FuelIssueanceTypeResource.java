package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.FuelIssueanceType;
import com.bitumen.bluefusion.repository.FuelIssueanceTypeRepository;
import com.bitumen.bluefusion.service.FuelIssueanceTypeService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.FuelIssueanceType}.
 */
@RestController
@RequestMapping("/api/fuel-issueance-types")
public class FuelIssueanceTypeResource {

    private static final Logger LOG = LoggerFactory.getLogger(FuelIssueanceTypeResource.class);

    private static final String ENTITY_NAME = "fuelIssueanceType";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelIssueanceTypeService fuelIssueanceTypeService;

    private final FuelIssueanceTypeRepository fuelIssueanceTypeRepository;

    public FuelIssueanceTypeResource(
        FuelIssueanceTypeService fuelIssueanceTypeService,
        FuelIssueanceTypeRepository fuelIssueanceTypeRepository
    ) {
        this.fuelIssueanceTypeService = fuelIssueanceTypeService;
        this.fuelIssueanceTypeRepository = fuelIssueanceTypeRepository;
    }

    /**
     * {@code POST  /fuel-issueance-types} : Create a new fuelIssueanceType.
     *
     * @param fuelIssueanceType the fuelIssueanceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelIssueanceType, or with status {@code 400 (Bad Request)} if the fuelIssueanceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelIssueanceType> createFuelIssueanceType(@RequestBody FuelIssueanceType fuelIssueanceType)
        throws URISyntaxException {
        LOG.debug("REST request to save FuelIssueanceType : {}", fuelIssueanceType);
        if (fuelIssueanceType.getId() != null) {
            throw new BadRequestAlertException("A new fuelIssueanceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelIssueanceType = fuelIssueanceTypeService.save(fuelIssueanceType);
        return ResponseEntity.created(new URI("/api/fuel-issueance-types/" + fuelIssueanceType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fuelIssueanceType.getId().toString()))
            .body(fuelIssueanceType);
    }

    /**
     * {@code PUT  /fuel-issueance-types/:id} : Updates an existing fuelIssueanceType.
     *
     * @param id the id of the fuelIssueanceType to save.
     * @param fuelIssueanceType the fuelIssueanceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelIssueanceType,
     * or with status {@code 400 (Bad Request)} if the fuelIssueanceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelIssueanceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelIssueanceType> updateFuelIssueanceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelIssueanceType fuelIssueanceType
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelIssueanceType : {}, {}", id, fuelIssueanceType);
        if (fuelIssueanceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelIssueanceType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelIssueanceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fuelIssueanceType = fuelIssueanceTypeService.update(fuelIssueanceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelIssueanceType.getId().toString()))
            .body(fuelIssueanceType);
    }

    /**
     * {@code PATCH  /fuel-issueance-types/:id} : Partial updates given fields of an existing fuelIssueanceType, field will ignore if it is null
     *
     * @param id the id of the fuelIssueanceType to save.
     * @param fuelIssueanceType the fuelIssueanceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelIssueanceType,
     * or with status {@code 400 (Bad Request)} if the fuelIssueanceType is not valid,
     * or with status {@code 404 (Not Found)} if the fuelIssueanceType is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelIssueanceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelIssueanceType> partialUpdateFuelIssueanceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelIssueanceType fuelIssueanceType
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelIssueanceType partially : {}, {}", id, fuelIssueanceType);
        if (fuelIssueanceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelIssueanceType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelIssueanceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelIssueanceType> result = fuelIssueanceTypeService.partialUpdate(fuelIssueanceType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelIssueanceType.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-issueance-types} : get all the fuelIssueanceTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelIssueanceTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelIssueanceType>> getAllFuelIssueanceTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FuelIssueanceTypes");
        Page<FuelIssueanceType> page = fuelIssueanceTypeService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-issueance-types/:id} : get the "id" fuelIssueanceType.
     *
     * @param id the id of the fuelIssueanceType to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelIssueanceType, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuelIssueanceType> getFuelIssueanceType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelIssueanceType : {}", id);
        Optional<FuelIssueanceType> fuelIssueanceType = fuelIssueanceTypeService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelIssueanceType);
    }

    /**
     * {@code DELETE  /fuel-issueance-types/:id} : delete the "id" fuelIssueanceType.
     *
     * @param id the id of the fuelIssueanceType to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelIssueanceType(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FuelIssueanceType : {}", id);
        fuelIssueanceTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
