package com.bitumen.bluefusion.service.storage.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.domain.enumeration.StorageContent;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.repository.SiteRepository;
import com.bitumen.bluefusion.repository.StorageRepository;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import com.bitumen.bluefusion.service.storage.StorageService;
import com.bitumen.bluefusion.service.storage.dto.StorageRequestDTO;
import com.bitumen.bluefusion.service.storage.dto.StorageResponseDTO;
import com.bitumen.bluefusion.service.storage.dto.StorageResponseMapper;
import com.bitumen.bluefusion.service.storage.dto.StorageSearchDTO;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StorageServiceImpl implements StorageService {

    private final StorageRepository storageRepository;
    private final SiteRepository siteRepository;
    private final CompanyRepository companyRepository;

    @Override
    public StorageResponseDTO save(StorageRequestDTO storageRequestDTO) {
        Storage storage = Storage.builder()
            .accessKey(storageRequestDTO.accessKey())
            .buildSmartCode(storageRequestDTO.buildSmartCode())
            .site(getSite(storageRequestDTO.siteId()))
            .company(getCompany(storageRequestDTO.companyId()))
            .storageCode(storageRequestDTO.storageCode())
            .capacity(storageRequestDTO.capacity())
            .name(storageRequestDTO.name())
            .isFixed(storageRequestDTO.isFixed())
            .storageContent(storageRequestDTO.storageContent())
            .isActive(true)
            .build();

        Storage storageUnit = storageRepository.save(storage);
        return StorageResponseMapper.map.apply(storageUnit);
    }

    @Override
    public StorageResponseDTO update(Long storageId, StorageRequestDTO storageRequestDTO) {
        return storageRepository
            .findById(storageId)
            .map(existingStorage -> {
                Optional.ofNullable(storageRequestDTO.storageCode()).ifPresent(existingStorage::setStorageCode);

                Optional.ofNullable(storageRequestDTO.name()).ifPresent(existingStorage::setName);

                Optional.ofNullable(storageRequestDTO.capacity()).ifPresent(existingStorage::setCapacity);

                Optional.ofNullable(storageRequestDTO.buildSmartCode()).ifPresent(existingStorage::setBuildSmartCode);

                Optional.ofNullable(storageRequestDTO.isFixed()).ifPresent(existingStorage::setIsFixed);

                Optional.ofNullable(storageRequestDTO.isActive()).ifPresent(existingStorage::setIsActive);

                Optional.ofNullable(storageRequestDTO.accessKey()).ifPresent(existingStorage::setAccessKey);

                Optional.ofNullable(storageRequestDTO.companyId()).ifPresent(companyId -> existingStorage.setCompany(getCompany(companyId))
                );

                Optional.ofNullable(storageRequestDTO.siteId()).ifPresent(siteId -> existingStorage.setSite(getSite(siteId)));

                return existingStorage;
            })
            .map(storageRepository::save)
            .map(StorageResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("storage with id %s was not found", storageId)));
    }

    private Site getSite(Long siteId) {
        Site site = null;
        if (!Objects.isNull(siteId)) {
            site = siteRepository
                .findBySiteId(siteId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Site with id %s does not exist", siteId)));
        }
        return site;
    }

    private Company getCompany(Long companyId) {
        Company company = null;
        if (!Objects.isNull(companyId)) {
            company = companyRepository
                .findById(companyId)
                .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %s does not exist", companyId)));
        }
        return company;
    }

    @Override
    public void delete(Long storageId) {
        storageRepository
            .findById(storageId)
            .ifPresentOrElse(storageRepository::delete, () -> {
                throw new RecordNotFoundException(String.format("storage with id %s was not found", storageId));
            });
    }

    @Override
    public StorageResponseDTO get(Long storageId) {
        return storageRepository
            .findById(storageId)
            .map(StorageResponseMapper.map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Storage with id %s does not exist", storageId)));
    }

    @Override
    public Page<StorageResponseDTO> getAll(Pageable pageable, StorageSearchDTO storageSearchDTO) {
        Specification<Storage> specification = StorageSpec.withNameLike(storageSearchDTO.name())
            .and(StorageSpec.withBuildSmartCode(storageSearchDTO.buildSmartCode()))
            .and(StorageSpec.withStorageCodeLike(storageSearchDTO.storageCode()))
            .and(StorageSpec.isFixed(storageSearchDTO.isFixed()))
            .and(StorageSpec.isActive(true))
            .and(StorageSpec.withCompany(getCompany(storageSearchDTO.companyId())))
            .and(StorageSpec.withSite(getSite(storageSearchDTO.siteId())));

        if (!Objects.isNull(storageSearchDTO.warehouseContent())) {
            specification.and(StorageSpec.withStorageContent(StorageContent.valueOf(storageSearchDTO.warehouseContent())));
        }

        return storageRepository.findAll(specification, pageable).map(StorageResponseMapper.map);
    }
}
