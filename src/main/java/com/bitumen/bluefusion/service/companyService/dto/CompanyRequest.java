package com.bitumen.bluefusion.service.companyService.dto;

public record CompanyRequest(String name, String address, String accessKey, Boolean usesFuelSystem, Boolean isActive, Boolean isIAC) {}
