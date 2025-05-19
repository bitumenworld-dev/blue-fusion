package com.bitumen.bluefusion.service.storage.dto;

import java.util.List;

public record StorageSearchDTO(
    String storageCode,
    String buildSmartCode,
    Long companyId,
    Long siteId,
    String name,
    Boolean isFixed,
    String accessKey,
    String warehouseContent
) {}
