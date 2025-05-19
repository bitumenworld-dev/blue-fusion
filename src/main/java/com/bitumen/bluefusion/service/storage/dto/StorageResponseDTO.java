package com.bitumen.bluefusion.service.storage.dto;

public record StorageResponseDTO(
    Long id,
    String storageCode,
    String buildSmartCode,
    String company,
    String site,
    String name,
    Boolean isFixed,
    Double capacity,
    String accessKey,
    String storageContent,
    Boolean isActive
) {}
