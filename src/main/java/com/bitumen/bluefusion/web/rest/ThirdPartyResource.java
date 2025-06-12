package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.thirdPartyService.ThirdPartyService;
import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyRequest;
import com.bitumen.bluefusion.service.thirdPartyService.dto.ThirdPartyResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
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

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/third-party")
public class ThirdPartyResource {

    private static final String ENTITY_NAME = "thirdParty";

    @Value("blueFusionApp}")
    private String applicationName;

    private final ThirdPartyService thirdPartyService;

    @PostMapping("")
    public ResponseEntity<ThirdPartyResponse> createThirdParty(@RequestBody ThirdPartyRequest thirdPartyRequest) throws URISyntaxException {
        ThirdPartyResponse thirdParty = thirdPartyService.save(thirdPartyRequest);
        return ResponseEntity.created(new URI("/api/third-party" + thirdParty.thirdPartyId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, String.valueOf(thirdParty.thirdPartyId())))
            .body(thirdParty);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ThirdPartyResponse> updateThirdParty(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThirdPartyRequest thirdPartyRequest
    ) {
        ThirdPartyResponse thirdParty = thirdPartyService.update(id, thirdPartyRequest);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(thirdParty.thirdPartyId())))
            .body(thirdParty);
    }

    @PatchMapping(value = "/{id}", consumes = { "application/json", "application/merge-patch+json" })
    public ResponseEntity<ThirdPartyResponse> partialUpdateThirdParty(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody ThirdPartyRequest thirdPartyRequest
    ) {
        ThirdPartyResponse thirdParty = thirdPartyService.partialUpdate(id, thirdPartyRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.of(thirdParty),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(thirdParty.thirdPartyId()))
        );
    }

    @GetMapping("")
    public ResponseEntity<List<ThirdPartyResponse>> getAllThirdParty(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(required = false) String thirdPartyName,
        @RequestParam(required = false) String thirdPartyShortName,
        @RequestParam(required = false) Boolean isActive
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("createdDate").descending());
        Page<ThirdPartyResponse> thirdParties = thirdPartyService.findAll(pageable, thirdPartyName, thirdPartyShortName, isActive);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), thirdParties);
        return ResponseEntity.ok().headers(headers).body(thirdParties.getContent());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ThirdPartyResponse> getThirdParty(@PathVariable Long id, ThirdPartyRequest thirdPartyRequest) {
        ThirdPartyResponse thirdParty = thirdPartyService.findOne(id, thirdPartyRequest);
        return ResponseUtil.wrapOrNotFound(
            Optional.ofNullable(thirdParty),
            HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, String.valueOf(id))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteThirdParty(@PathVariable Long id) {
        thirdPartyService.delete(id);
        return ResponseEntity.noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, String.valueOf(id)))
            .build();
    }
}
