package com.bitumen.bluefusion.service.companyService.dto;

public record CompanyResponse(Long id, String name, String address, String description, Boolean usesFuelSystem, Boolean isActive) {}
