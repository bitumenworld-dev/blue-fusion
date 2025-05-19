package com.bitumen.bluefusion.service.storage.dto;

import java.util.List;

public record StorageRequestDTO(
    String storageCode,
    String buildSmartCode,
    Long companyId,
    Long siteId,
    String name,
    Boolean isFixed,
    Double capacity,
    String accessKey,
    String storageContent,
    Boolean isActive
) {}
