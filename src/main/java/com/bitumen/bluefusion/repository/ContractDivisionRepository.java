package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.domain.ContractDivision;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the ContractDivision entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContractDivisionRepository extends JpaRepository<ContractDivision, Long>, JpaSpecificationExecutor<ContractDivision> {}
