package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.makeModelService.MakeModelService;
import com.bitumen.bluefusion.service.makeModelService.dto.MakeModelRequest;
import com.bitumen.bluefusion.service.makeModelService.dto.MakeModelResponse;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/make-models")
public class MakeModelResource {

    private static final String ENTITY_NAME = "manufacturerModel";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MakeModelService makeModelService;

    @PostMapping("")
    public ResponseEntity<MakeModelResponse> createManufacturerModel(@RequestBody MakeModelRequest makeModelRequest)
        throws URISyntaxException {
        MakeModelResponse makeModel = makeModelService.save(makeModelRequest);
        return ResponseEntity.created(new URI("/api/make-models/" + makeModel.id()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, makeModel.id().toString()))
            .body(makeModel);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MakeModelResponse> updateManufacturerModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MakeModelRequest makeModelRequest
    ) {
        MakeModelResponse makeModel = makeModelService.update(id, makeModelRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, makeModel.id().toString()))
            .body(makeModel);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<MakeModelResponse> partialUpdateManufacturerModel(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody MakeModelRequest makeModelRequest
    ) throws URISyntaxException {
        MakeModelResponse result = makeModelService.partialUpdate(id, makeModelRequest);

        return ResponseUtil.wrapOrNotFound(
            Optional.of(result),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, id.toString())
        );
    }

    @GetMapping("")
    public ResponseEntity<List<MakeModelResponse>> getAllManufacturerModels(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "model", required = false) String model
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("modelId").descending());
        Page<MakeModelResponse> makeModelResponsePage = makeModelService.findAll(pageable, model);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(
            ServletUriComponentsBuilder.fromCurrentRequest(),
            makeModelResponsePage
        );

        return ResponseEntity.ok().headers(headers).body(makeModelResponsePage.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MakeModelResponse> getManufacturerModel(@PathVariable("id") Long id) {
        MakeModelResponse manufacturerModel = makeModelService.findOne(id);
        return new ResponseEntity<>(manufacturerModel, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteManufacturerModel(@PathVariable("id") Long id) {
        makeModelService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
