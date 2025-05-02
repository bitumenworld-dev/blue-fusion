package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.service.site.dto.SiteResponse;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Site entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
    Optional<Site> findBySiteId(Long id);
}
