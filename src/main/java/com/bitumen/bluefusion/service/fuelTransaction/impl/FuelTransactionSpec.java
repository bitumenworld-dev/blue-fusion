package com.bitumen.bluefusion.service.fuelTransaction.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Employee;
import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.domain.enumeration.FuelTransactionType;
import com.bitumen.bluefusion.domain.enumeration.IssuanceTransactionType;
import java.time.LocalDate;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;

public interface FuelTransactionSpec {
    static Specification<FuelTransactionHeader> withOperator(final Employee operator) {
        return (
            (root, query, builder) -> (Objects.isNull(operator)) ? builder.conjunction() : builder.equal(root.get("operator"), operator)
        );
    }

    static Specification<FuelTransactionHeader> withAttendee(final Employee attendee) {
        return (
            (root, query, builder) -> (Objects.isNull(attendee)) ? builder.conjunction() : builder.equal(root.get("attendee"), attendee)
        );
    }

    static Specification<FuelTransactionHeader> withFuelTransactionType(final FuelTransactionType fuelTransactionType) {
        return (
            (root, query, builder) ->
                (Objects.isNull(fuelTransactionType))
                    ? builder.conjunction()
                    : builder.equal(root.get("fuelTransactionType"), fuelTransactionType)
        );
    }

    static Specification<FuelTransactionHeader> withIssuanceTransactionType(final IssuanceTransactionType issuanceTransactionType) {
        return (
            (root, query, builder) ->
                (Objects.isNull(issuanceTransactionType))
                    ? builder.conjunction()
                    : builder.equal(root.get("issuanceTransactionType"), issuanceTransactionType)
        );
    }

    static Specification<FuelTransactionHeader> withCompany(final Company company) {
        return ((root, query, builder) -> (Objects.isNull(company)) ? builder.conjunction() : builder.equal(root.get("company"), company));
    }

    static Specification<FuelTransactionHeader> withStorage(final Storage storage) {
        return (
            (root, query, builder) -> (Objects.isNull(storage)) ? builder.conjunction() : builder.equal(root.get("storageUnit"), storage)
        );
    }

    static Specification<FuelTransactionHeader> withCreatedDateBetween(final LocalDate startDate, final LocalDate endDate) {
        return (root, query, builder) -> {
            if (Objects.isNull(startDate) && Objects.isNull(endDate)) {
                return builder.conjunction();
            }

            if (Objects.isNull(startDate)) {
                return builder.lessThanOrEqualTo(root.get("createdDate"), endDate);
            }

            if (Objects.isNull(endDate)) {
                return builder.greaterThanOrEqualTo(root.get("createdDate"), startDate);
            }

            return builder.between(root.get("createdDate"), startDate, endDate);
        };
    }
}
