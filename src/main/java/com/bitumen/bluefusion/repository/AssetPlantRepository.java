package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.AssetPlant;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetPlant entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetPlantRepository extends JpaRepository<AssetPlant, Long>, JpaSpecificationExecutor<AssetPlant> {
    Optional<AssetPlant> findByAssetPlantId(Long id);
}
