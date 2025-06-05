package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.repository.ContractDivisionRepository;
import com.bitumen.bluefusion.service.contractDivisionService.ContractDivisionService;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionRequest;
import com.bitumen.bluefusion.service.contractDivisionService.dto.ContractDivisionResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
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
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bitumen.bluefusion.domain.ContractDivision}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/contract-divisions")
public class ContractDivisionResource {

    private static final String ENTITY_NAME = "contractDivision";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContractDivisionService contractDivisionService;

    private final ContractDivisionRepository contractDivisionRepository;

    @PostMapping("")
    public ResponseEntity<ContractDivisionResponse> createContractDivision(@RequestBody ContractDivisionRequest contractDivisionRequest)
        throws URISyntaxException {
        ContractDivisionResponse contractDivision = contractDivisionService.save(contractDivisionRequest);
        return ResponseEntity.created(new URI("/api/contract-divisions/" + contractDivision.contractDivisionId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    String.valueOf(contractDivision.contractDivisionId())
                )
            )
            .body(contractDivision);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ContractDivisionResponse> updateContractDivision(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractDivisionRequest contractDivisionRequest
    ) throws URISyntaxException {
        ContractDivisionResponse contractDivision = contractDivisionService.update(id, contractDivisionRequest);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(
                    applicationName,
                    true,
                    ENTITY_NAME,
                    String.valueOf(contractDivision.contractDivisionId())
                )
            )
            .body(contractDivision);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ContractDivisionResponse> partialUpdateContractDivision(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ContractDivisionRequest contractDivisionRequest
    ) throws URISyntaxException {
        ContractDivisionResponse contractDivision = contractDivisionService.partialupdate(id, contractDivisionRequest);

        return ResponseUtil.wrapOrNotFound(
            Optional.of(contractDivision),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(contractDivision.contractDivisionId()))
        );
    }

    @GetMapping("")
    public ResponseEntity<List<ContractDivisionResponse>> getAllContractDivisions(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(required = false) Long contractDivisionId,
        @RequestParam(required = false) String contractDivisionNumber,
        @RequestParam(required = false) String contractDivisionName
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("contractDivisionNumber").descending());
        Page<ContractDivisionResponse> contractDivisions = contractDivisionService.findAll(
            pageable,
            contractDivisionId,
            contractDivisionNumber,
            contractDivisionName
        );
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            contractDivisions
        );
        return ResponseEntity.ok().headers(headers).body(contractDivisions.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContractDivisionResponse> getContractDivision(
        @PathVariable("id") Long ContractDivisionId,
        @RequestBody ContractDivisionRequest contractDivisionRequest
    ) {
        return ResponseEntity.ok().body(contractDivisionService.findOne(ContractDivisionId, contractDivisionRequest));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteContractDivision(
        @PathVariable("id") Long ContractDivisionId,
        @RequestBody ContractDivisionRequest contractDivisionRequest
    ) {
        contractDivisionService.delete(ContractDivisionId, contractDivisionRequest);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, String.valueOf(ContractDivisionId)))
            .build();
    }
}
