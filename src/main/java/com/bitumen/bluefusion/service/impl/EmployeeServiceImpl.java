package com.bitumen.bluefusion.service.impl;

import com.bitumen.bluefusion.domain.Employee;
import com.bitumen.bluefusion.repository.EmployeeRepository;
import com.bitumen.bluefusion.service.EmployeeService;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link com.bitumen.bluefusion.domain.Employee}.
 */
@Service
@Transactional
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger LOG = LoggerFactory.getLogger(EmployeeServiceImpl.class);

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee save(Employee employee) {
        LOG.debug("Request to save Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Employee update(Employee employee) {
        LOG.debug("Request to update Employee : {}", employee);
        return employeeRepository.save(employee);
    }

    @Override
    public Optional<Employee> partialUpdate(Employee employee) {
        LOG.debug("Request to partially update Employee : {}", employee);

        return employeeRepository
            .findById(employee.getId())
            .map(existingEmployee -> {
                if (employee.getEmployeeId() != null) {
                    existingEmployee.setEmployeeId(employee.getEmployeeId());
                }
                if (employee.getContractActive() != null) {
                    existingEmployee.setContractActive(employee.getContractActive());
                }

                return existingEmployee;
            })
            .map(employeeRepository::save);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<Employee> findAll(Pageable pageable) {
        LOG.debug("Request to get all Employees");
        return employeeRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Employee> findOne(Long id) {
        LOG.debug("Request to get Employee : {}", id);
        return employeeRepository.findById(id);
    }

    @Override
    public void delete(Long id) {
        LOG.debug("Request to delete Employee : {}", id);
        employeeRepository.deleteById(id);
    }
}
