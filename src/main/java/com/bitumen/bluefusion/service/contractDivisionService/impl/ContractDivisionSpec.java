package com.bitumen.bluefusion.service.contractDivisionService.impl;

import com.bitumen.bluefusion.domain.ContractDivision;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface ContractDivisionSpec {
    static Specification<ContractDivision> withcompanyId(final Long companyId) {
        return (
            (root, query, builder) -> (companyId == null) ? builder.conjunction() : builder.equal(root.get("contractDivisionId"), companyId)
        );
    }

    static Specification<ContractDivision> withContractDivisionNumber(final String contractDivisionNumber) {
        return (root, query, builder) ->
            (contractDivisionNumber == null || contractDivisionNumber.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("contractDivisionNumber"), "%" + contractDivisionNumber + "%");
    }

    static Specification<ContractDivision> withContractDivisionName(final String contractDivisionName) {
        return (root, query, builder) ->
            (contractDivisionName == null || contractDivisionName.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("contractDivisionName"), "%" + contractDivisionName + "%");
    }
}
