package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.assetPlant.AssetPlantService;
import com.bitumen.bluefusion.service.assetPlant.dto.AssetPlantFilterCriteria;
import com.bitumen.bluefusion.service.assetPlant.payload.AssetPlantRequest;
import com.bitumen.bluefusion.service.assetPlant.payload.AssetPlantResponse;
import jakarta.mail.MethodNotSupportedException;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.HeaderUtil;
import tech.jhipster.web.util.PaginationUtil;
import tech.jhipster.web.util.ResponseUtil;

/**
 * REST controller for managing {@link com.bitumen.bluefusion.domain.AssetPlant}.
 */
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/asset-plants")
public class AssetPlantResource {

    private static final String ENTITY_NAME = "assetPlant";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AssetPlantService assetPlantService;

    @PostMapping("")
    public ResponseEntity<AssetPlantResponse> createAssetPlant(@Valid @RequestBody AssetPlantRequest assetPlantRequest)
        throws URISyntaxException {
        AssetPlantResponse assetPlantResponse = assetPlantService.save(assetPlantRequest);
        return ResponseEntity.created(new URI("/api/asset-plants/" + assetPlantResponse.assetPlantId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, assetPlantResponse.assetPlantId().toString()))
            .body(assetPlantResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AssetPlantResponse> updateAssetPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @Valid @RequestBody AssetPlantRequest assetPlantRequest
    ) {
        AssetPlantResponse assetPlant = assetPlantService.update(id, assetPlantRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, assetPlant.assetPlantId().toString()))
            .body(assetPlant);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<AssetPlantResponse> partialUpdateAssetPlant(
        @PathVariable(value = "id", required = false) final Long id,
        @NotNull @RequestBody AssetPlantRequest assetPlantRequest
    ) throws MethodNotSupportedException {
        AssetPlantResponse result = assetPlantService.partialUpdate(id, assetPlantRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<AssetPlantResponse>> getAllAssetPlants(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "fleetNumber", required = false) String fleetNumber,
        @RequestParam(value = "make", required = false) Long make,
        @RequestParam(value = "model", required = false) Long model,
        @RequestParam(value = "company", required = false) Long company,
        @RequestParam(value = "category", required = false) Long category,
        @RequestParam(value = "subcategory", required = false) Long subcategory,
        @RequestParam(value = "trackConsumption", required = false) Boolean trackConsumption,
        @RequestParam(value = "trackSmrReading", required = false) Boolean trackSmrReading
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("assetPlantId").descending());
        AssetPlantFilterCriteria criteria = new AssetPlantFilterCriteria(
            fleetNumber,
            company,
            make,
            model,
            category,
            subcategory,
            trackConsumption,
            trackSmrReading
        );

        Page<AssetPlantResponse> assetPlantResponsePage = assetPlantService.findAll(pageable, criteria);

        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            assetPlantResponsePage
        );

        return ResponseEntity.ok().headers(headers).body(assetPlantResponsePage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<AssetPlantResponse> getAssetPlant(@PathVariable("id") Long id) {
        AssetPlantResponse assetPlant = assetPlantService.findOne(id);
        return new ResponseEntity<>(assetPlant, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAssetPlant(@PathVariable("id") Long id) {
        assetPlantService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }

    @PutMapping("/set-operator/{assetPlantId}/{operatorId}")
    ResponseEntity<AssetPlantResponse> setCurrentOperator(
        @PathVariable("assetPlantId") Long assetPlantId,
        @PathVariable("operatorId") Long operatorId
    ) {
        AssetPlantResponse assetPlantResponse = assetPlantService.setCurrentOperator(assetPlantId, operatorId);
        return new ResponseEntity<>(assetPlantResponse, HttpStatus.OK);
    }

    @PutMapping("/set-accessible-company/{assetPlantId}/{accessibleByCompanyId}")
    ResponseEntity<AssetPlantResponse> setAccessibleByCompany(
        @PathVariable("assetPlantId") Long assetPlantId,
        @PathVariable("accessibleByCompanyId") Long accessibleByCompanyId
    ) {
        AssetPlantResponse assetPlantResponse = assetPlantService.setAccessibleByCompany(assetPlantId, accessibleByCompanyId);
        return new ResponseEntity<>(assetPlantResponse, HttpStatus.OK);
    }

    @PutMapping("/set-current-contract-division/{assetPlantId}/{contractDivisionId}")
    ResponseEntity<AssetPlantResponse> setCurrentContractDivision(
        @PathVariable("assetPlantId") Long assetPlantId,
        @PathVariable("contractDivisionId") Long contractDivisionId
    ) {
        AssetPlantResponse assetPlantResponse = assetPlantService.setCurrentContractDivision(assetPlantId, contractDivisionId);
        return new ResponseEntity<>(assetPlantResponse, HttpStatus.OK);
    }
}
