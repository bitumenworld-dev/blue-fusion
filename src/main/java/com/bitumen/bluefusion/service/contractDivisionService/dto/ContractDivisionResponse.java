package com.bitumen.bluefusion.service.contractDivisionService.dto;

import com.bitumen.bluefusion.domain.enumeration.ContractDivisionType;
import java.time.LocalTime;

public record ContractDivisionResponse(
    Long contractDivisionId,
    String contractDivisionNumber,
    String contractDivisionName,
    Long contractId,
    String buildSmartReference,
    LocalTime shiftStart,
    ContractDivisionType contractDivisionType,
    boolean completed,
    double hrHoursMondayThursday,
    double hrHoursFriday,
    double addHoursMondayFriday,
    double addHoursWeekend
) {}
