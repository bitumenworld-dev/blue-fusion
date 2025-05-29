package com.bitumen.bluefusion.service.makeModelService.impl;

import com.bitumen.bluefusion.domain.MakeModel;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface MakeModelSpec {
    static Specification<MakeModel> withModel(final String model) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(model)) ? builder.conjunction() : builder.like(root.get("model"), "%" + model + "%")
        );
    }
}
