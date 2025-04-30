package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.PlantSubcategory;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the PlantSubcategory entity.
 */
@SuppressWarnings("unused")
@Repository
public interface PlantSubcategoryRepository extends JpaRepository<PlantSubcategory, Long> {}
