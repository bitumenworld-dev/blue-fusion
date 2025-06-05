package com.bitumen.bluefusion.service.contractDivisionService.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.ContractDivision;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public interface ContractDivisionSpec {
    static Specification<ContractDivision> withCompany(final Company company) {
        return ((root, query, builder) -> (Objects.isNull(company)) ? builder.conjunction() : builder.equal(root.get("company"), company));
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
