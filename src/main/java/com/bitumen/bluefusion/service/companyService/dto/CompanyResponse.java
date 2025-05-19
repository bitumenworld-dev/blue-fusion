package com.bitumen.bluefusion.service.companyService.dto;

public record CompanyResponse(
    Long id,
    String name,
    String address,
    String access_key,
    Boolean usesFuelSystem,
    Boolean isActive,
    Boolean isIAC
) {}
