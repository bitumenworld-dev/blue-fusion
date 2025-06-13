package com.bitumen.bluefusion.repository;

import com.bitumen.bluefusion.domain.EmployeeDetails;
import java.io.Serializable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeDetailsRepository
    extends JpaRepository<EmployeeDetails, Long>, JpaSpecificationExecutor<EmployeeDetails>, Serializable {}
