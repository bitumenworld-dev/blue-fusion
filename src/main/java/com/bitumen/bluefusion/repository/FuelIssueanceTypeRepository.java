package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelIssueanceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelIssueanceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelIssueanceTypeRepository extends JpaRepository<FuelIssueanceType, Long> {}
