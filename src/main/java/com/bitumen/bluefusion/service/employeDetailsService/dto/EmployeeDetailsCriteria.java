package com.bitumen.bluefusion.service.employeDetailsService.dto;

public record EmployeeDetailsCriteria(
    Long employeeId,
    String employeeNumber,
    Long companyId,
    String employeeFirstNames,
    String employeeSurname,
    String employeeMiddleName,
    Boolean onCurrentPayroll,
    String employeeNationalIdNumber,
    String employeeEmail,
    String employeeActivityShortDescription
) {}
