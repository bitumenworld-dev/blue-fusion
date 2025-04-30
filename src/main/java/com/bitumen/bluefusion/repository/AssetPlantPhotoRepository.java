package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.AssetPlantPhoto;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the AssetPlantPhoto entity.
 */
@SuppressWarnings("unused")
@Repository
public interface AssetPlantPhotoRepository extends JpaRepository<AssetPlantPhoto, Long> {}
