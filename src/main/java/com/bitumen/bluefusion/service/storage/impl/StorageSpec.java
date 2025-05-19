package com.bitumen.bluefusion.service.storage.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import com.bitumen.bluefusion.domain.enumeration.StorageContent;
import java.util.Objects;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public interface StorageSpec {
    static Specification<Storage> withNameLike(final String name) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(name)) ? builder.conjunction() : builder.like(root.get("name"), "%" + name + "%")
        );
    }

    static Specification<Storage> withStorageCodeLike(final String storageCode) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(storageCode)) ? builder.conjunction() : builder.like(root.get("storageCode"), "%" + storageCode + "%")
        );
    }

    static Specification<Storage> withBuildSmartCode(final String buildSmartCode) {
        return (
            (root, query, builder) ->
                (!StringUtils.hasText(buildSmartCode))
                    ? builder.conjunction()
                    : builder.like(root.get("buildSmartCode"), "%" + buildSmartCode + "%")
        );
    }

    static Specification<Storage> withCompany(final Company company) {
        return ((root, query, builder) -> (Objects.isNull(company)) ? builder.conjunction() : builder.equal(root.get("company"), company));
    }

    static Specification<Storage> withSite(final Site site) {
        return ((root, query, builder) -> (Objects.isNull(site)) ? builder.conjunction() : builder.equal(root.get("site"), site));
    }

    static Specification<Storage> isFixed(final Boolean isFixed) {
        return ((root, query, builder) -> (Objects.isNull(isFixed)) ? builder.conjunction() : builder.equal(root.get("isFixed"), isFixed));
    }

    static Specification<Storage> isActive(final Boolean isFixed) {
        return ((root, query, builder) -> (Objects.isNull(isFixed)) ? builder.conjunction() : builder.equal(root.get("isActive"), isFixed));
    }

    static Specification<Storage> withStorageContent(final StorageContent storageContent) {
        return (
            (root, query, builder) ->
                (Objects.isNull(storageContent))
                    ? builder.conjunction()
                    : builder.like(root.get("storageContent"), "%" + storageContent + "%")
        );
    }
}
