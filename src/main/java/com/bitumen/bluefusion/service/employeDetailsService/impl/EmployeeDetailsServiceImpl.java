package com.bitumen.bluefusion.service.employeDetailsService.impl;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.EmployeeDetails;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.repository.EmployeeDetailsRepository;
import com.bitumen.bluefusion.service.employeDetailsService.EmployeeDetailsService;
import com.bitumen.bluefusion.service.employeDetailsService.dto.EmployeeDetailsCriteria;
import com.bitumen.bluefusion.service.employeDetailsService.dto.EmployeeDetailsMapper;
import com.bitumen.bluefusion.service.employeDetailsService.payload.EmployeeDetailsRequest;
import com.bitumen.bluefusion.service.employeDetailsService.payload.EmployeeDetailsResponse;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@RequiredArgsConstructor
public class EmployeeDetailsServiceImpl implements EmployeeDetailsService {

    private final EmployeeDetailsRepository employeeDetailsRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeDetailsMapper mapper;

    @Override
    public EmployeeDetailsResponse save(EmployeeDetailsRequest employeeDetailsRequest) {
        EmployeeDetails employeeDetails = buildEmployeeDetails(employeeDetailsRequest);

        employeeDetails = employeeDetailsRepository.save(employeeDetails);
        return mapper.map(employeeDetails);
    }

    @Override
    public EmployeeDetailsResponse update(long employeeId, EmployeeDetailsRequest employeeDetailsRequest) {
        EmployeeDetails employeeDetails = buildEmployeeDetails(employeeDetailsRequest);
        employeeDetails.setEmployeeId(employeeId);

        employeeDetails = employeeDetailsRepository.save(employeeDetails);
        return mapper.map(employeeDetails);
    }

    @Transactional(readOnly = true)
    @Override
    public Page<EmployeeDetailsResponse> findAll(Pageable pageable, EmployeeDetailsCriteria criteria) {
        //        EmployeeDetails details = detailsResolver.resolveEntitiesForCriteria(criteria);
        Company company = null;
        if (criteria.companyId() != null) {
            company = companyRepository
                .findById(criteria.companyId())
                .orElseThrow(() -> new IllegalArgumentException("Invalid company ID"));
        }
        Specification<EmployeeDetails> specification = EmployeeDetailsSpec.withCompany(company)
            .and(EmployeeDetailsSpec.withEmployeeNumber(criteria.employeeNumber()))
            .and(EmployeeDetailsSpec.withEmployeeSurname(criteria.employeeSurname()))
            .and(EmployeeDetailsSpec.withEmployeeFirstNames(criteria.employeeFirstNames()))
            .and(EmployeeDetailsSpec.withEmployeeMiddleName(criteria.employeeMiddleName()))
            .and(EmployeeDetailsSpec.withOnCurrentPayroll(criteria.onCurrentPayroll()))
            .and(EmployeeDetailsSpec.withEmployeeEmail(criteria.employeeEmail()))
            .and(EmployeeDetailsSpec.withEmployeeActivityShortDescription(criteria.employeeActivityShortDescription()))
            .and(EmployeeDetailsSpec.withEmployeeNationalIdNumber(criteria.employeeNationalIdNumber()))
            .and(EmployeeDetailsSpec.withEmployeeId(criteria.employeeId()));

        return employeeDetailsRepository.findAll(specification, pageable).map(mapper::map);
    }

    @Transactional(readOnly = true)
    @Override
    public EmployeeDetailsResponse findOne(Long employeeId) {
        EmployeeDetails employeeDetails = findByEmployeeId(employeeId);
        return mapper.map(employeeDetails);
    }

    @Override
    public void delete(Long employeeId) {
        EmployeeDetails employeeDetails = findByEmployeeId(employeeId);
        employeeDetailsRepository.delete(employeeDetails);
    }

    //Private Helper Methods

    private EmployeeDetails findByEmployeeId(Long employeeId) {
        return employeeDetailsRepository
            .findById(employeeId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Employee not found: %s", employeeId)));
    }

    private EmployeeDetails buildEmployeeDetails(EmployeeDetailsRequest request) {
        return EmployeeDetails.builder()
            .employeeNumber(request.employeeNumber())
            .company(request.companyId())
            .employeeSurname(request.employeeSurname())
            .employeeFirstNames(request.employeeFirstNames())
            .employeeMiddleName(request.employeeMiddleName())
            .employeeDateEngaged(request.employeeDateEngaged())
            .onCurrentPayroll(request.onCurrentPayroll())
            .employeeNecGrade(request.employeeNecGrade())
            .employeeEmail(request.employeeEmail())
            .employeeGender(request.employeeGender())
            .employeeIdCopy(request.employeeIdCopy())
            .employeeNationalIdNumber(request.employeeNationalIdNumber())
            .employeeTeamName(request.employeeTeamName())
            .employeeSpecialAbilities(request.employeeSpecialAbilities())
            .employeeDriverCodePayslip(request.employeeDriverCodePayslip())
            .employeeDateOfBirth(request.employeeDateOfBirth())
            .employeeDriversLicenseNumber(request.employeeDriversLicenseNumber())
            .employeeLicenseClasses(request.employeeLicenseClasses())
            .employeeDefensiveCertificateNumber(request.employeeDefensiveCertificateNumber())
            .employeeDefensiveExpiryDate(request.employeeDefensiveExpiryDate())
            .employeeRetestExpiryDate(request.employeeRetestExpiryDate())
            .employeeMedicalExpiryDate(request.employeeMedicalExpiryDate())
            .employeeJobTitle(request.employeeJobTitle())
            .employeeLastAuditDate(request.employeeLastAuditDate())
            .employeeSmeAppointment(request.employeeSmeAppointment())
            .employeeHazChemTrainingDate(request.employeeHazChemTrainingDate())
            .employeePin(request.employeePin())
            .employeeColourNumber(request.employeeColourNumber())
            .employeeChatName(request.employeeChatName())
            .employeeWhatsappNumber(request.employeeWhatsappNumber())
            .employeeAllowWhatsapp(request.employeeAllowWhatsapp())
            .employeeAssignedWorkshop(request.employeeAssignedWorkshop())
            .employeeNotifyAlert(request.employeeNotifyAlert())
            .employeeCurrentContract(request.employeeCurrentContract())
            .employeeCurrentSiteId(request.employeeCurrentSiteId())
            .employeeAddress(request.employeeAddress())
            .employeeDisplayOrder(request.employeeDisplayOrder())
            .employeePreferredLanguage(request.employeePreferredLanguage())
            .employeeTempCodeCorrected(request.employeeTempCodeCorrected())
            .employeeCostCentre(request.employeeCostCentre())
            .employeeVerified(request.employeeVerified())
            .employeeWhatsappPayslipConfirmed(request.employeeWhatsappPayslipConfirmed())
            .employeeDocumentPrefix(request.employeeDocumentPrefix())
            .employeeCaptureWagesHours(request.employeeCaptureWagesHours())
            .employeeDepartment(request.employeeDepartment())
            .employeeAllowedToReceiveFuel(request.employeeAllowedToReceiveFuel())
            .employeeBank(request.employeeBank())
            .employeeBranch(request.employeeBranch())
            .employeeAccountNo(request.employeeAccountNo())
            .employeeAccountName(request.employeeAccountName())
            .employeeBankz(request.employeeBankz())
            .employeeBranchz(request.employeeBranchz())
            .employeeAccountNoz(request.employeeAccountNoz())
            .employeeAccountNamez(request.employeeAccountNamez())
            .employeeEcocashNumber(request.employeeEcocashNumber())
            .employeeEcocashFirstName(request.employeeEcocashFirstName())
            .employeeEcocashLastName(request.employeeEcocashLastName())
            .employeeDriversLicenseCopy(request.employeeDriversLicenseCopy())
            .employeeDriversDefensiveLicenseCopy(request.employeeDriversDefensiveLicenseCopy())
            .employeeRetestCopy(request.employeeReTestCopy())
            .employeeMedicalCopy(request.employeeMedicalCopy())
            .employeeDebtorAccount(request.employeeDebtorAccount())
            .employeeMedicalCertificateCopy(request.employeeMedicalCertificateCopy())
            .employeeDefensiveCertificateCopy(request.employeeDefensiveCertificateCopy())
            .employeeCVCopy(request.employeeCVCopy())
            .employeeDVCopy(request.employeeDVCopy())
            .employeeFuelPin(request.employeeFuelPin())
            .employeeNextOfKin(request.employeeNextOfKin())
            .employeeNextOfKinPhone(request.employeeNextOfKinPhone())
            .employeeDateTerminated(request.employeeDateTerminated())
            .employeeDateProbation(request.employeeDateProbation())
            .employeeContractExpired(request.employeeContractExpired())
            .employeeIdCard(request.employeeIdCard())
            .employeeRecordForm(request.employeeRecordForm())
            .employeeOther(request.employeeOther())
            .employeeOccupation(request.employeeOccupation())
            .employeeActivityContract(request.employeeActivityContract())
            .employeeActivityShortDescription(request.employeeActivityShortDescription())
            .employeeOhsAppointment(request.employeeOhsAppointment())
            .employeeComplianceAgreement(request.employeeComplianceAgreement())
            .employeeRolesResponsibilities(request.employeeRolesResponsibilities())
            .employeeBwOperatorAuthority(request.employeeBwOperatorAuthority())
            .employeePlannedTaskObservation(request.employeePlannedTaskObservation())
            .employeeRetestCopy(request.employeeRetestCopy())
            .employeeDriversMedicalCopy(request.employeeDriversMedicalCopy())
            .employeePneumoconiosisMedicalBureauNumber(request.employeePneumoconiosisMedicalBureauNumber())
            .medicalFitnessCertificate(request.medicalFitnessCertificate())
            .employeeFoodHandlersExpiryDate(request.employeeFoodHandlersExpiryDate())
            .employeeCVRef(request.employeeCVRef())
            .employeeCertificateOfCompetence(request.employeeCertificateOfCompetence())
            .employeeMobileNumber(request.employeeMobileNumber())
            .employeeComments(request.employeeComments())
            .employeeBwStaffContract(request.employeeBwStaffContract())
            .employeeAllocatedToMultiContracts(request.employeeAllocatedToMultiContracts())
            .employeePositionOnPayslip(request.employeePositionOnPayslip())
            .employeeMedicalPneumonconsisExpiryDate(request.employeeMedicalPneumonconsisExpiryDate())
            .employeeCity(request.employeeCity())
            .employeeMedicalFitnessCertificateExpiryDate(request.employeeMedicalFitnessCertificateExpiryDate())
            .employeeSocialSecurityNumber(request.employeeSocialSecurityNumber())
            .employeeCompanyVehiclePolicy(request.employeeCompanyVehiclePolicy())
            .employeeConfidentialityAgreement(request.employeeConfidentialityAgreement())
            .build();
    }
}
