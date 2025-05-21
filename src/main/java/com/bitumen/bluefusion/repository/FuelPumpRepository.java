package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.domain.Site;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelPump entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelPumpRepository extends JpaRepository<FuelPump, Long>, JpaSpecificationExecutor<FuelPump> {}
