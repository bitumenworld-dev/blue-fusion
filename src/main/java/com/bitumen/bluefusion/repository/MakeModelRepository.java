package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.Make;
import com.bitumen.bluefusion.domain.MakeModel;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

@Repository
public interface MakeModelRepository extends JpaRepository<MakeModel, Long>, JpaSpecificationExecutor<MakeModel> {}
