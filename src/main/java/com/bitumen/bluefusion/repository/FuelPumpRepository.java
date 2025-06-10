package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.FuelPump;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the FuelPump entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FuelPumpRepository extends JpaRepository<FuelPump, Long>, JpaSpecificationExecutor<FuelPump> {
    Boolean existsByFuelPumpId(Long fuelPumpId);
}
