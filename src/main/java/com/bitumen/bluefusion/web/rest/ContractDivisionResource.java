package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.domain.ContractDivision;
import com.bitumen.bluefusion.repository.ContractDivisionRepository;
import com.bitumen.bluefusion.service.ContractDivisionService;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.ContractDivision}.
 */
@RestController
@RequestMapping("/api/contract-divisions")
public class ContractDivisionResource {

    private static final Logger LOG = LoggerFactory.getLogger(ContractDivisionResource.class);

    private static final String ENTITY_NAME = "contractDivision";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractDivisionService contractDivisionService;

    private final ContractDivisionRepository contractDivisionRepository;

    public ContractDivisionResource(
        ContractDivisionService contractDivisionService,
        ContractDivisionRepository contractDivisionRepository
    ) {
        this.contractDivisionService = contractDivisionService;
        this.contractDivisionRepository = contractDivisionRepository;
    }

    /**
     * {@code POST  /contract-divisions} : Create a new contractDivision.
     *
     * @param contractDivision the contractDivision to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contractDivision, or with status {@code 400 (Bad Request)} if the contractDivision has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<ContractDivision> createContractDivision(@RequestBody ContractDivision contractDivision)
        throws URISyntaxException {
        LOG.debug("REST request to save ContractDivision : {}", contractDivision);
        if (contractDivision.getId() != null) {
            throw new BadRequestAlertException("A new contractDivision cannot already have an ID", ENTITY_NAME, "idexists");
        }
        contractDivision = contractDivisionService.save(contractDivision);
        return ResponseEntity.created(new URI("/api/contract-divisions/" + contractDivision.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, contractDivision.getId().toString()))
            .body(contractDivision);
    }

    /**
     * {@code PUT  /contract-divisions/:id} : Updates an existing contractDivision.
     *
     * @param id the id of the contractDivision to save.
     * @param contractDivision the contractDivision to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractDivision,
     * or with status {@code 400 (Bad Request)} if the contractDivision is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contractDivision couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ContractDivision> updateContractDivision(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractDivision contractDivision
    ) throws URISyntaxException {
        LOG.debug("REST request to update ContractDivision : {}, {}", id, contractDivision);
        if (contractDivision.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractDivision.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractDivisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        contractDivision = contractDivisionService.update(contractDivision);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractDivision.getId().toString()))
            .body(contractDivision);
    }

    /**
     * {@code PATCH  /contract-divisions/:id} : Partial updates given fields of an existing contractDivision, field will ignore if it is null
     *
     * @param id the id of the contractDivision to save.
     * @param contractDivision the contractDivision to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contractDivision,
     * or with status {@code 400 (Bad Request)} if the contractDivision is not valid,
     * or with status {@code 404 (Not Found)} if the contractDivision is not found,
     * or with status {@code 500 (Internal Server Error)} if the contractDivision couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContractDivision> partialUpdateContractDivision(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractDivision contractDivision
    ) throws URISyntaxException {
        LOG.debug("REST request to partial update ContractDivision partially : {}, {}", id, contractDivision);
        if (contractDivision.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        if (!Objects.equals(id, contractDivision.getId())) {
            throw new BadRequestAlertException("Invalid ID", ENTITY_NAME, "idinvalid");
        }

        if (!contractDivisionRepository.existsById(id)) {
            throw new BadRequestAlertException("Entity not found", ENTITY_NAME, "idnotfound");
        }

        Optional<ContractDivision> result = contractDivisionService.partialUpdate(contractDivision);

        return ResponseUtil.wrapOrNotFound(
            result,
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contractDivision.getId().toString())
        );
    }

    /**
     * {@code GET  /contract-divisions} : get all the contractDivisions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contractDivisions in body.
     */
    @GetMapping("")
    public ResponseEntity<List<ContractDivision>> getAllContractDivisions(
        @org.springdoc.core.annotations.ParameterObject Pageable pageable
    ) {
        LOG.debug("REST request to get a page of ContractDivisions");
        Page<ContractDivision> page = contractDivisionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /contract-divisions/:id} : get the "id" contractDivision.
     *
     * @param id the id of the contractDivision to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contractDivision, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ContractDivision> getContractDivision(@PathVariable("id") Long id) {
        LOG.debug("REST request to get ContractDivision : {}", id);
        Optional<ContractDivision> contractDivision = contractDivisionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contractDivision);
    }

    /**
     * {@code DELETE  /contract-divisions/:id} : delete the "id" contractDivision.
     *
     * @param id the id of the contractDivision to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContractDivision(@PathVariable("id") Long id) {
        LOG.debug("REST request to delete ContractDivision : {}", id);
        contractDivisionService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
