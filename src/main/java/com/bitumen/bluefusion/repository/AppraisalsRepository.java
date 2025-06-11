package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.Appraisals;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface AppraisalsRepository extends JpaRepository<Appraisals, Long>, JpaSpecificationExecutor<Appraisals> {}
