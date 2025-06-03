package com.bitumen.bluefusion.service.contractDivisionService.dto;

import com.bitumen.bluefusion.domain.enumeration.ContractDivisionType;
import java.time.LocalTime;

public record ContractDivisionRequest(
    String contractDivisionNumber,
    Long companyId,
    String contractDivisionName,
    String buildSmartReference,
    LocalTime shiftStart,
    LocalTime shiftEnd,
    ContractDivisionType contractDivisionType,
    Boolean completed,
    Double hrHoursMondayThursday,
    Double hrHoursFriday,
    Double addHoursMondayFriday,
    Double addHoursWeekend
) {}
