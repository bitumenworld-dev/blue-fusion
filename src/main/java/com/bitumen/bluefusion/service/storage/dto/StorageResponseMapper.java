package com.bitumen.bluefusion.service.storage.dto;

import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.domain.enumeration.StorageContent;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public interface StorageResponseMapper {
    Function<Storage, StorageResponseDTO> map = storage ->
        new StorageResponseDTO(
            storage.getStorageId(),
            storage.getStorageCode(),
            storage.getBuildSmartCode(),
            Objects.isNull(storage.getCompany()) ? "" : storage.getCompany().getName(),
            Objects.isNull(storage.getSite()) ? "" : storage.getSite().getSiteName(),
            storage.getName(),
            storage.getIsFixed(),
            storage.getCapacity(),
            storage.getAccessKey(),
            storage.getStorageContent(),
            storage.getIsActive()
        );
}
