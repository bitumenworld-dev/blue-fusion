package com.bitumen.bluefusion.service.fuelPump.impl;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.domain.Storage;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface FuelPumpSpec {
    static Specification<FuelPump> withFuelPumpCode(final String fuelPumpCode) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(fuelPumpCode))
                    ? builder.conjunction()
                    : builder.like(root.get("fuelPumpCode"), "%" + fuelPumpCode + "%")
        );
    }

    static Specification<FuelPump> isActive(final Boolean isFixed) {
        return ((root, query, builder) -> (Objects.isNull(isFixed)) ? builder.conjunction() : builder.equal(root.get("isActive"), isFixed));
    }

    static Specification<FuelPump> withCurrentStorageUnit(final Storage storageUnit) {
        return (
            (root, query, builder) ->
                (Objects.isNull(storageUnit)) ? builder.conjunction() : builder.equal(root.get("currentStorageUnit"), storageUnit)
        );
    }
}
