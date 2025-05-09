package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
import com.bitumen.bluefusion.service.FuelTransactionLineService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.FuelTransactionLine}.
 */
@RestController
@RequestMapping("/api/fuel-transaction-lines")
public class FuelTransactionLineResource {

    private static final Logger LOG = LoggerFactory.getLogger(FuelTransactionLineResource.class);

    private static final String ENTITY_NAME = "fuelTransactionLine";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FuelTransactionLineService fuelTransactionLineService;

    private final FuelTransactionLineRepository fuelTransactionLineRepository;

    public FuelTransactionLineResource(
        FuelTransactionLineService fuelTransactionLineService,
        FuelTransactionLineRepository fuelTransactionLineRepository
    ) {
        this.fuelTransactionLineService = fuelTransactionLineService;
        this.fuelTransactionLineRepository = fuelTransactionLineRepository;
    }

    /**
     * {@code POST  /fuel-transaction-lines} : Create a new fuelTransactionLine.
     *
     * @param fuelTransactionLine the fuelTransactionLine to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fuelTransactionLine, or with status {@code 400 (Bad Request)} if the fuelTransactionLine has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<FuelTransactionLine> createFuelTransactionLine(@RequestBody FuelTransactionLine fuelTransactionLine)
        throws URISyntaxException {
        LOG.debug("REST request to save FuelTransactionLine : {}", fuelTransactionLine);
        if (fuelTransactionLine.getFuelTransactionLineId() != null) {
            throw new BadRequestAlertException("A new fuelTransactionLine cannot already have an ID", ENTITY_NAME, "idexists");
        }
        fuelTransactionLine = fuelTransactionLineService.save(fuelTransactionLine);
        return ResponseEntity.created(new URI("/api/fuel-transaction-lines/" + fuelTransactionLine.getFuelTransactionLineId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    fuelTransactionLine.getFuelTransactionLineId().toString()
                )
            )
            .body(fuelTransactionLine);
    }

    /**
     * {@code PUT  /fuel-transaction-lines/:id} : Updates an existing fuelTransactionLine.
     *
     * @param id the id of the fuelTransactionLine to save.
     * @param fuelTransactionLine the fuelTransactionLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTransactionLine,
     * or with status {@code 400 (Bad Request)} if the fuelTransactionLine is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fuelTransactionLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<FuelTransactionLine> updateFuelTransactionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTransactionLine fuelTransactionLine
    ) throws URISyntaxException {
        LOG.debug("REST request to update FuelTransactionLine : {}, {}", id, fuelTransactionLine);
        if (fuelTransactionLine.getFuelTransactionLineId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTransactionLine.getFuelTransactionLineId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTransactionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        fuelTransactionLine = fuelTransactionLineService.update(fuelTransactionLine);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    fuelTransactionLine.getFuelTransactionLineId().toString()
                )
            )
            .body(fuelTransactionLine);
    }

    /**
     * {@code PATCH  /fuel-transaction-lines/:id} : Partial updates given fields of an existing fuelTransactionLine, field will ignore if it is null
     *
     * @param id the id of the fuelTransactionLine to save.
     * @param fuelTransactionLine the fuelTransactionLine to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fuelTransactionLine,
     * or with status {@code 400 (Bad Request)} if the fuelTransactionLine is not valid,
     * or with status {@code 404 (Not Found)} if the fuelTransactionLine is not found,
     * or with status {@code 500 (Internal Server Error)} if the fuelTransactionLine couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<FuelTransactionLine> partialUpdateFuelTransactionLine(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody FuelTransactionLine fuelTransactionLine
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update FuelTransactionLine partially : {}, {}", id, fuelTransactionLine);
        if (fuelTransactionLine.getFuelTransactionLineId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, fuelTransactionLine.getFuelTransactionLineId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!fuelTransactionLineRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<FuelTransactionLine> result = fuelTransactionLineService.partialUpdate(fuelTransactionLine);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(
                applicationName,
                true,
                ENTITY_NAME,
                fuelTransactionLine.getFuelTransactionLineId().toString()
            )
        );
    }

    /**
     * {@code GET  /fuel-transaction-lines} : get all the fuelTransactionLines.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fuelTransactionLines in body.
     */
    @GetMapping("")
    public ResponseEntity<List<FuelTransactionLine>> getAllFuelTransactionLines(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of FuelTransactionLines");
        Page<FuelTransactionLine> page = fuelTransactionLineService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /fuel-transaction-lines/:id} : get the "id" fuelTransactionLine.
     *
     * @param id the id of the fuelTransactionLine to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fuelTransactionLine, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<FuelTransactionLine> getFuelTransactionLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to get FuelTransactionLine : {}", id);
        Optional<FuelTransactionLine> fuelTransactionLine = fuelTransactionLineService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fuelTransactionLine);
    }

    /**
     * {@code DELETE  /fuel-transaction-lines/:id} : delete the "id" fuelTransactionLine.
     *
     * @param id the id of the fuelTransactionLine to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFuelTransactionLine(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete FuelTransactionLine : {}", id);
        fuelTransactionLineService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
