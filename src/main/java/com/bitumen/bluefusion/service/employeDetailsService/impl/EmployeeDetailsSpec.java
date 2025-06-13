package com.bitumen.bluefusion.service.employeDetailsService.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.EmployeeDetails;
import org.springframework.data.jpa.domain.Specification;

public class EmployeeDetailsSpec {

    public static Specification<EmployeeDetails> withCompany(Company company) {
        return (root, query, builder) -> company == null ? builder.conjunction() : builder.equal(root.get("company"), company);
    }

    public static Specification<EmployeeDetails> withEmployeeNumber(String employeeNumber) {
        return (root, query, builder) ->
            (employeeNumber == null || employeeNumber.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("employeeNumber"), "%" + employeeNumber + "%");
    }

    public static Specification<EmployeeDetails> withEmployeeSurname(String employeeSurname) {
        return (root, query, builder) ->
            (employeeSurname == null || employeeSurname.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("employeeSurname"), "%" + employeeSurname + "%");
    }

    public static Specification<EmployeeDetails> withEmployeeFirstNames(String employeeFirstNames) {
        return (root, query, builder) ->
            (employeeFirstNames == null || employeeFirstNames.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("employeeFirstNames"), "%" + employeeFirstNames + "%");
    }

    public static Specification<EmployeeDetails> withEmployeeMiddleName(String employeeMiddleName) {
        return (root, query, builder) ->
            (employeeMiddleName == null || employeeMiddleName.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("employeeMiddleName"), "%" + employeeMiddleName + "%");
    }

    public static Specification<EmployeeDetails> withOnCurrentPayroll(Boolean onCurrentPayroll) {
        return (root, query, builder) ->
            onCurrentPayroll == null ? builder.conjunction() : builder.equal(root.get("onCurrentPayroll"), onCurrentPayroll);
    }

    public static Specification<EmployeeDetails> withEmployeeEmail(String employeeEmail) {
        return (root, query, builder) ->
            (employeeEmail == null || employeeEmail.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("employeeEmail"), "%" + employeeEmail + "%");
    }

    public static Specification<EmployeeDetails> withEmployeeActivityShortDescription(String employeeActivityShortDescription) {
        return (root, query, builder) ->
            (employeeActivityShortDescription == null || employeeActivityShortDescription.isEmpty())
                ? builder.conjunction()
                : builder.like(root.get("employeeActivityShortDescription"), "%" + employeeActivityShortDescription + "%");
    }

    public static Specification<EmployeeDetails> withEmployeeNationalIdNumber(String employeeNationalIdNumber) {
        return (root, query, cb) ->
            (employeeNationalIdNumber == null || employeeNationalIdNumber.isEmpty())
                ? cb.conjunction()
                : cb.like(root.get("employeeNationalIdNumber"), "%" + employeeNationalIdNumber + "%");
    }

    public static Specification<EmployeeDetails> withEmployeeId(Long employeeId) {
        return (root, query, cb) -> employeeId == null ? cb.conjunction() : cb.equal(root.get("employeeId"), employeeId);
    }
}
