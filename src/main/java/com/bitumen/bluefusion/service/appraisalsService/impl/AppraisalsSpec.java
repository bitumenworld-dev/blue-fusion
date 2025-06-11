package com.bitumen.bluefusion.service.appraisalsService.impl;

import com.bitumen.bluefusion.domain.Appraisals;
import org.springframework.data.jpa.domain.Specification;

public interface AppraisalsSpec {
    static Specification<Appraisals> withEmployeeNumber(final String employeeNumber) {
        return (root, query, builder) ->
            (employeeNumber == null || employeeNumber.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("employeeNumber"), "%" + employeeNumber + "%");
    }

    static Specification<Appraisals> withAppraisalPeriod(final Integer appraisalPeriod) {
        return (root, query, builder) ->
            (appraisalPeriod == null) ? builder.conjunction() : builder.equal(root.get("appraisalPeriod"), appraisalPeriod);
    }

    static Specification<Appraisals> withAppraisalValue(final Integer appraisalValue) {
        return (root, query, builder) ->
            (appraisalValue == null) ? builder.conjunction() : builder.equal(root.get("appraisalValue"), appraisalValue);
    }
}
