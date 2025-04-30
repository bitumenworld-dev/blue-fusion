package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.Workshop;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Workshop entity.
 */
@SuppressWarnings("unused")
@Repository
public interface WorkshopRepository extends JpaRepository<Workshop, Long> {}
