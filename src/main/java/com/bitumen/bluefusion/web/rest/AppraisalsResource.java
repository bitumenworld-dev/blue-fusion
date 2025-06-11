package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.appraisalsService.AppraisalsService;
import com.bitumen.bluefusion.service.appraisalsService.dto.AppraisalsRequest;
import com.bitumen.bluefusion.service.appraisalsService.dto.AppraisalsResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/appraisals")
public class AppraisalsResource {

    private static final String ENTITY_NAME = "appraisal";

    @Value("blueFusionApp")
    private String applicationName;

    private final AppraisalsService appraisalsService;

    @PostMapping("")
    public ResponseEntity<AppraisalsResponse> createAppraisal(@RequestBody AppraisalsRequest appraisalsRequest) throws URISyntaxException {
        AppraisalsResponse savedAppraisal = appraisalsService.save(appraisalsRequest);
        return ResponseEntity.created(new URI("/api/appraisals/" + savedAppraisal.appraisalId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(savedAppraisal.appraisalId())))
            .body(savedAppraisal);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AppraisalsResponse> updateAppraisal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalsRequest appraisalsRequest
    ) {
        AppraisalsResponse updatedAppraisal = appraisalsService.update(id, appraisalsRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(updatedAppraisal.appraisalId())))
            .body(updatedAppraisal);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AppraisalsResponse> partialUpdateAppraisal(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody AppraisalsRequest appraisalsRequest
    ) {
        AppraisalsResponse updatedAppraisal = appraisalsService.partialUpdate(id, appraisalsRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(updatedAppraisal.appraisalId())))
            .body(updatedAppraisal);
    }

    @GetMapping("")
    public ResponseEntity<List<AppraisalsResponse>> getAllAppraisals(
        @RequestParam(value = "page", defaultValue = "0") int page,
        @RequestParam(value = "size", defaultValue = "100") int size,
        @RequestParam(required = false) String employeeNumber,
        @RequestParam(required = false) Integer appraisalPeriod
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("employeeNumber").descending());
        Page<AppraisalsResponse> appraisalsPage = appraisalsService.findAll(pageable, employeeNumber, appraisalPeriod, null);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            appraisalsPage
        );
        return ResponseEntity.ok().headers(headers).body(appraisalsPage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppraisalsResponse> getAppraisal(@PathVariable Long id) {
        AppraisalsResponse appraisal = appraisalsService.findOne(id);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(appraisal.appraisalId())))
            .body(appraisal);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppraisal(@PathVariable Long id) {
        appraisalsService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, String.valueOf(id)))
            .build();
    }
}
