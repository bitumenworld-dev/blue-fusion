package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.ThirdParty;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface ThirdPartyRepository extends JpaRepository<ThirdParty, Long>, JpaSpecificationExecutor<ThirdParty> {}
