package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.PlantCategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlantCategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantCategoryRepository extends JpaRepository<PlantCategory, Long> {}
