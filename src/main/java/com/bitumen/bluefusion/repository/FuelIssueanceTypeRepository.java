package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelIssuanceType;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelIssuanceType entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelIssueanceTypeRepository extends JpaRepository<FuelIssuanceType, Long> {}
