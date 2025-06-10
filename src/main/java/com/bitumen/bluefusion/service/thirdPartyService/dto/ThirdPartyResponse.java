package com.bitumen.bluefusion.service.thirdPartyService.dto;

public record ThirdPartyResponse(
    Long thirdPartyId,
    String thirdPartyName,
    String thirdPartyShortName,
    Boolean isActive,
    Boolean usesFuelSystem
) {}
