package com.bitumen.bluefusion.service.companyService.dto;

public record CompanyRequest(String name, String address, String description, Boolean usesFuelSystem, Boolean isActive) {}
