package com.bitumen.bluefusion.service.employeDetailsService.dto;

import com.bitumen.bluefusion.domain.EmployeeDetails;
import com.bitumen.bluefusion.service.employeDetailsService.payload.EmployeeDetailsResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EmployeeDetailsMapper {
    @Mapping(source = "company.companyId", target = "companyId")
    EmployeeDetailsResponse map(EmployeeDetails employeeDetails);
}
