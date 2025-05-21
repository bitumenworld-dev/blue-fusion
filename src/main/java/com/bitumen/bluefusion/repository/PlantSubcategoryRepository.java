package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.PlantSubcategory;
import com.bitumen.bluefusion.domain.Storage;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface PlantSubcategoryRepository extends JpaRepository<PlantSubcategory, Long>, JpaSpecificationExecutor<PlantSubcategory> {}
