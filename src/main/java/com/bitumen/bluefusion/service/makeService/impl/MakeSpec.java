package com.bitumen.bluefusion.service.makeService.impl;

import com.bitumen.bluefusion.domain.Make;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface MakeSpec {
    static Specification<Make> withMake(final String make) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(make)) ? builder.conjunction() : builder.like(root.get("make"), "%" + make + "%")
        );
    }
}
