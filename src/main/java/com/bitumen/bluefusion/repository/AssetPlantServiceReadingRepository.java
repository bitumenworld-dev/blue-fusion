package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetPlantServiceReading entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetPlantServiceReadingRepository extends JpaRepository<AssetPlantServiceReading, Long> {}
