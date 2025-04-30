package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.SiteContract;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the SiteContract entity.
 */
@SuppressWarnings("unused")
@Repository
public interface SiteContractRepository extends JpaRepository<SiteContract, Long> {}
