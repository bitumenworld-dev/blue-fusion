package com.bitumen.bluefusion.web.rest;

import com.bitumen.bluefusion.service.employeDetailsService.EmployeeDetailsService;
import com.bitumen.bluefusion.service.employeDetailsService.dto.EmployeeDetailsCriteria;
import com.bitumen.bluefusion.service.employeDetailsService.payload.EmployeeDetailsRequest;
import com.bitumen.bluefusion.service.employeDetailsService.payload.EmployeeDetailsResponse;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import java.net.URI;
import java.net.URISyntaxException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tech.jhipster.web.util.HeaderUtil;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/employee-details")
public class EmployeeDetailsResource {

    private static final String ENTITY_NAME = "employeeDetails";

    private final String applicationName = "blueFusionApp";

    private final EmployeeDetailsService employeeDetailsService;

    @PostMapping
    public ResponseEntity<EmployeeDetailsResponse> createEmployeeDetails(@RequestBody EmployeeDetailsRequest employeeDetailsRequest)
        throws URISyntaxException {
        EmployeeDetailsResponse employeeDetailsResponse = employeeDetailsService.save(employeeDetailsRequest);
        return ResponseEntity.created(new URI("/api/employee-details/" + employeeDetailsResponse.employeeId()))
            .headers(
                HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, employeeDetailsResponse.employeeId().toString())
            )
            .body(employeeDetailsResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDetailsResponse> updateEmployeeDetails(
        @PathVariable(value = "id", required = false) final Long id,
        @RequestBody EmployeeDetailsRequest employeeDetailsRequest
    ) {
        EmployeeDetailsResponse employeeDetailsResponse = employeeDetailsService.update(id, employeeDetailsRequest);
        return ResponseEntity.ok()
            .headers(
                HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, employeeDetailsResponse.employeeId().toString())
            )
            .body(employeeDetailsResponse);
    }

    @GetMapping
    public ResponseEntity<Page<EmployeeDetailsResponse>> getAllEmployeeDetails(
        @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
        @RequestParam(value = "size", defaultValue = "100", required = false) Integer size,
        @RequestParam(value = "employeeId", required = false) Long employeeId,
        @RequestParam(value = "employeeNumber", required = false) String employeeNumber,
        @RequestParam(value = "companyId", required = false) Long companyId,
        @RequestParam(value = "employeeFirstNames", required = false) String employeeFirstNames,
        @RequestParam(value = "employeeSurname", required = false) String employeeSurname,
        @RequestParam(value = "employeeMiddleName", required = false) String employeeMiddleName,
        @RequestParam(value = "onCurrentPayroll", required = false) Boolean onCurrentPayroll,
        @RequestParam(value = "employeeNationalIdNumber", required = false) String employeeNationalIdNumber,
        @RequestParam(value = "employeeEmail", required = false) String employeeEmail,
        @RequestParam(value = "employeeActivityShortDescription", required = false) String employeeActivityShortDescription
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("employeeId").descending());
        EmployeeDetailsCriteria criteria = new EmployeeDetailsCriteria(
            employeeId,
            employeeNumber,
            companyId,
            employeeFirstNames,
            employeeSurname,
            employeeMiddleName,
            onCurrentPayroll,
            employeeNationalIdNumber,
            employeeEmail,
            employeeActivityShortDescription
        );
        Page<EmployeeDetailsResponse> pageResult = employeeDetailsService.findAll(pageable, criteria);
        return ResponseEntity.ok().body(pageResult);
    }
}
