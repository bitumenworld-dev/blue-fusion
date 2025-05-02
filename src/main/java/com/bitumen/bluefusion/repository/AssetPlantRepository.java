package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.AssetPlant;
import java.util.Optional;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetPlant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetPlantRepository extends JpaRepository<AssetPlant, Long> {
    Optional<AssetPlant> findByAssetPlantId(Long id);
}
