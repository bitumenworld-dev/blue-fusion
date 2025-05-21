package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.companyService.CompanyService;
import com.bitumen.bluefusion.service.companyService.dto.CompanyRequest;
import com.bitumen.bluefusion.service.companyService.dto.CompanyResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
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
 * REST controller for managing {@link com.bitumen.bluefusion.domain.Company}.
 */
@Slf4j
@RestController
@RequestMapping("/api/companies")
public class CompanyResource {

    private static final String ENTITY_NAME = "company";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CompanyService companyService;

    public CompanyResource(CompanyService companyService) {
        this.companyService = companyService;
    }

    /**
     * {@code POST  /companies} : Create a new company.
     *
     * @param companyRequest the company to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new company, or with status {@code 400 (Bad Request)} if the company has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("")
    public ResponseEntity<CompanyResponse> createCompany(@RequestBody CompanyRequest companyRequest) throws URISyntaxException {
        CompanyResponse company = companyService.save(companyRequest);
        return ResponseEntity.created(new URI("/api/companies/" + company.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, company.id().toString()))
            .body(company);
    }

    /**
     * {@code PUT  /companies/:id} : Updates an existing company.
     *
     * @param id the id of the company to save.
     * @param companyRequest the company to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated company,
     * or with status {@code 400 (Bad Request)} if the company is not valid,
     * or with status {@code 500 (Internal Server Error)} if the company couldn't be updated.
     *
     */
    @PutMapping("/{id}")
    public ResponseEntity<CompanyResponse> updateCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyRequest companyRequest
    ) {
        CompanyResponse company = companyService.update(id, companyRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, company.id().toString()))
            .body(company);
    }

    /**
     * {@code PATCH  /companies/:id} : Partial updates given fields of an existing company, field will ignore if it is null
     *
     * @param id the id of the company to save.
     * @param companyRequest the company to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated company,
     * or with status {@code 400 (Bad Request)} if the company is not valid,
     * or with status {@code 404 (Not Found)} if the company is not found,
     * or with status {@code 500 (Internal Server Error)} if the company couldn't be updated.
     *
     */
    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<CompanyResponse> partialUpdateCompany(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody CompanyRequest companyRequest
    ) {
        CompanyResponse company = companyService.partialUpdate(id, companyRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(company),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, company.id().toString())
        );
    }

    /**
     * {@code GET  /companies} : get all the companies.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of companies in body.
     */
    @GetMapping("")
    public ResponseEntity<List<CompanyResponse>> getAllCompanies(
        Pageable pageable,
        @RequestParam(value = "companyName", required = false) String companyName,
        @RequestParam(value = "usesFuelSystem", required = false) Boolean usesFuelSystem,
        @RequestParam(value = "isActive", required = false) Boolean isActive
    ) {
        Page<CompanyResponse> page = companyService.findAll(pageable, companyName, usesFuelSystem, isActive);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /companies/:id} : get the "id" company.
     *
     * @param id the id of the company to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the company, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/{id}")
    public ResponseEntity<CompanyResponse> getCompany(@PathVariable("id") Long id) {
        CompanyResponse company = companyService.findByCompanyId(id);
        return ResponseUtil.wrapOrNotFound(Optional.of(company));
    }

    /**
     * {@code DELETE  /companies/:id} : delete the "id" company.
     *
     * @param id the id of the company to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCompany(@PathVariable("id") Long id) {
        log.debug("REST request to delete Company : {}", id);
        companyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
