package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.SmrReading;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface SmrReadingRepository extends JpaRepository<SmrReading, Long>, JpaSpecificationExecutor<SmrReading> {}
