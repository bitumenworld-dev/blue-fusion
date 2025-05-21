package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import com.bitumen.bluefusion.service.storage.StorageService;
import com.bitumen.bluefusion.service.storage.dto.StorageRequestDTO;
import com.bitumen.bluefusion.service.storage.dto.StorageResponseDTO;
import com.bitumen.bluefusion.service.storage.dto.StorageSearchDTO;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import tech.jhipster.web.util.PaginationUtil;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/storage")
public class StorageResource {

    private final StorageService storageService;

    @PostMapping("")
    public ResponseEntity<StorageResponseDTO> save(@RequestBody StorageRequestDTO storageRequestDTO) {
        return new ResponseEntity<>(storageService.save(storageRequestDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<StorageResponseDTO> update(@PathVariable Long id, @RequestBody StorageRequestDTO storageRequestDTO) {
        return new ResponseEntity<>(storageService.update(id, storageRequestDTO), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        storageService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{id}")
    public ResponseEntity<StorageResponseDTO> get(@PathVariable Long id) {
        return new ResponseEntity<>(storageService.get(id), HttpStatus.OK);
    }

    @GetMapping("")
    public ResponseEntity<List<StorageResponseDTO>> getAll(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "storageCode", required = false) String storageCode,
        @RequestParam(value = "buildSmartCode", required = false) String buildSmartCode,
        @RequestParam(value = "companyId", required = false) Long companyId,
        @RequestParam(value = "siteId", required = false) Long siteId,
        @RequestParam(value = "name", required = false) String name,
        @RequestParam(value = "isFixed", required = false) Boolean isFixed,
        @RequestParam(value = "accessKey", required = false) String accessKey,
        @RequestParam(value = "warehouseContent", required = false) String warehouseContent
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("storageId").descending());
        StorageSearchDTO storageSearchDTO = new StorageSearchDTO(
            storageCode,
            buildSmartCode,
            companyId,
            siteId,
            name,
            isFixed,
            accessKey,
            warehouseContent
        );

        Page<StorageResponseDTO> storagesPage = storageService.getAll(pageable, storageSearchDTO);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), storagesPage);
        return ResponseEntity.ok().headers(headers).body(storagesPage.getContent());
    }
}
