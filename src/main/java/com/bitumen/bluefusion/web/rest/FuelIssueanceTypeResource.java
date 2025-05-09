package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.FuelIssuanceType;
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
 * REST controller for managing {@link FuelIssuanceType}.
 */
@RestController
@RequestMapping("/api/fuel-issuance-types")
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
     * {@code POST  /fuel-issueance-types} : Create a new fuelIssuanceType.
     *
     * @param fuelIssuanceType the fuelIssuanceType to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelIssuanceType, or with status {@code 400 (Bad Request)} if the fuelIssuanceType has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelIssuanceType> createFuelIssueanceType(@RequestBody FuelIssuanceType fuelIssuanceType)
        throws URISyntaxException {
        LOG.debug("REST request to save FuelIssuanceType : {}", fuelIssuanceType);
        if (fuelIssuanceType.getId() != null) {
            throw new BadRequestAlertException("A new fuelIssuanceType cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelIssuanceType = fuelIssueanceTypeService.save(fuelIssuanceType);
        return ResponseEntity.created(new URI("/api/fuel-issueance-types/" + fuelIssuanceType.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, fuelIssuanceType.getId().toString()))
            .body(fuelIssuanceType);
    }

    /**
     * {@code PUT  /fuel-issueance-types/:id} : Updates an existing fuelIssuanceType.
     *
     * @param id the id of the fuelIssuanceType to save.
     * @param fuelIssuanceType the fuelIssuanceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelIssuanceType,
     * or with status {@code 400 (Bad Request)} if the fuelIssuanceType is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelIssuanceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelIssuanceType> updateFuelIssueanceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelIssuanceType fuelIssuanceType
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelIssuanceType : {}, {}", id, fuelIssuanceType);
        if (fuelIssuanceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelIssuanceType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelIssueanceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fuelIssuanceType = fuelIssueanceTypeService.update(fuelIssuanceType);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelIssuanceType.getId().toString()))
            .body(fuelIssuanceType);
    }

    /**
     * {@code PATCH  /fuel-issueance-types/:id} : Partial updates given fields of an existing fuelIssuanceType, field will ignore if it is null
     *
     * @param id the id of the fuelIssuanceType to save.
     * @param fuelIssuanceType the fuelIssuanceType to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelIssuanceType,
     * or with status {@code 400 (Bad Request)} if the fuelIssuanceType is not valid,
     * or with status {@code 404 (Not Found)} if the fuelIssuanceType is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelIssuanceType couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelIssuanceType> partialUpdateFuelIssueanceType(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelIssuanceType fuelIssuanceType
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelIssuanceType partially : {}, {}", id, fuelIssuanceType);
        if (fuelIssuanceType.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelIssuanceType.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelIssueanceTypeRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelIssuanceType> result = fuelIssueanceTypeService.partialUpdate(fuelIssuanceType);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fuelIssuanceType.getId().toString())
        );
    }

    /**
     * {@code GET  /fuel-issueance-types} : get all the fuelIssueanceTypes.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelIssueanceTypes in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelIssuanceType>> getAllFuelIssueanceTypes(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FuelIssueanceTypes");
        Page<FuelIssuanceType> page = fuelIssueanceTypeService.findAll(pageable);
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
    public ResponseEntity<FuelIssuanceType> getFuelIssueanceType(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelIssuanceType : {}", id);
        Optional<FuelIssuanceType> fuelIssueanceType = fuelIssueanceTypeService.findOne(id);
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
        LOG.debug("REST request to delete FuelIssuanceType : {}", id);
        fuelIssueanceTypeService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
