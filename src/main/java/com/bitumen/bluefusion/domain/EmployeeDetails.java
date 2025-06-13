package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employee_details")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class EmployeeDetails extends AbstractAuditingEntity<EmployeeDetails> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "employee_id")
    private Long employeeId;

    @Column(name = "employee_number")
    private String employeeNumber;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @Column(name = "surname")
    private String employeeSurname;

    @Column(name = "first_names")
    private String employeeFirstNames;

    @Column(name = "middle_name")
    private String employeeMiddleName;

    @Column(name = "date_engaged")
    private LocalDate employeeDateEngaged;

    @Column(name = "on_current_payroll")
    private Boolean onCurrentPayroll;

    @Column(name = "nec_grade")
    private String employeeNecGrade;

    @Column(name = "email")
    private String employeeEmail;

    @Column(name = "gender")
    private String employeeGender;

    @Column(name = "id_copy")
    private Boolean employeeIdCopy;

    @Column(name = "national_id_number")
    private String employeeNationalIdNumber;

    @Column(name = "team_name")
    private String employeeTeamName;

    @Column(name = "special_abilities")
    private String employeeSpecialAbilities;

    @Column(name = "driver_code_payslip")
    private String employeeDriverCodePayslip;

    @Column(name = "date_of_birth")
    private LocalDate employeeDateOfBirth;

    @Column(name = "drivers_license_number")
    private String employeeDriversLicenseNumber;

    @Column(name = "license_classes")
    private String employeeLicenseClasses;

    @Column(name = "defensive_certificate_number")
    private String employeeDefensiveCertificateNumber;

    @Column(name = "defensive_expiry_date")
    private LocalDate employeeDefensiveExpiryDate;

    @Column(name = "retest_expiry_date")
    private LocalDate employeeRetestExpiryDate;

    @Column(name = "medical_expiry_date")
    private LocalDate employeeMedicalExpiryDate;

    @Column(name = "job_title")
    private String employeeJobTitle;

    @Column(name = "last_audit_date")
    private LocalDate employeeLastAuditDate;

    @Column(name = "sme_appointment")
    private Boolean employeeSmeAppointment;

    @Column(name = "haz_chem_training_date")
    private LocalDate employeeHazChemTrainingDate;

    @Column(name = "pin")
    private String employeePin;

    @Column(name = "colour_number")
    private String employeeColourNumber;

    @Column(name = "chat_name")
    private String employeeChatName;

    @Column(name = "whatsapp_number")
    private String employeeWhatsappNumber;

    @Column(name = "allow_whatsapp")
    private Boolean employeeAllowWhatsapp;

    @Column(name = "assigned_workshop")
    private String employeeAssignedWorkshop;

    @Column(name = "notify_alert")
    private Boolean employeeNotifyAlert;

    @Column(name = "current_contract")
    private String employeeCurrentContract;

    @Column(name = "current_site_id")
    private Long employeeCurrentSiteId;

    @Column(name = "address")
    private String employeeAddress;

    @Column(name = "display_order")
    private Character employeeDisplayOrder; // A or B for display purposes idk man ;-;

    @Column(name = "preferred_language")
    private Boolean employeePreferredLanguage; // changed to preferred from preffered

    @Column(name = "temp_code_corrected")
    private Boolean employeeTempCodeCorrected;

    @Column(name = "cost_centre")
    private String employeeCostCentre;

    @Column(name = "verified")
    private Boolean employeeVerified;

    @Column(name = "whatsapp_payslip_confirmed")
    private Boolean employeeWhatsappPayslipConfirmed;

    @Column(name = "document_prefix")
    private String employeeDocumentPrefix;

    @Column(name = "capture_wages_hours")
    private Boolean employeeCaptureWagesHours;

    @Column(name = "department")
    private String employeeDepartment;

    @Column(name = "allowed_to_receive_fuel")
    private Boolean employeeAllowedToReceiveFuel;

    @Column(name = "bank")
    private Integer employeeBank;

    @Column(name = "branch")
    private String employeeBranch;

    @Column(name = "accountno")
    private String employeeAccountNo;

    @Column(name = "accountname")
    private String employeeAccountName;

    @Column(name = "bankz")
    private Integer employeeBankz;

    @Column(name = "branchz")
    private String employeeBranchz;

    @Column(name = "accountnoz")
    private String employeeAccountNoz;

    @Column(name = "accountnamez")
    private String employeeAccountNamez;

    @Column(name = "ecocash")
    private String employeeEcocashNumber;

    @Column(name = "ecocashfirstname")
    private String employeeEcocashFirstName;

    @Column(name = "ecocashlastname")
    private String employeeEcocashLastName;

    @Column(name = "dlcopy")
    private Boolean employeeDriversLicenseCopy;

    @Column(name = "dflcopy")
    private Boolean employeeDriversDefensiveLicenseCopy;

    @Column(name = "rtcopy")
    private Boolean employeeRetestCopy;

    @Column(name = "mccopy")
    private Boolean employeeMedicalCertificateCopy;

    @Column(name = "debtor_account")
    private String employeeDebtorAccount;

    @Column(name = "medcopy")
    private String employeeMedicalCopy;

    @Column(name = "dccopy")
    private String employeeDefensiveCertificateCopy;

    @Column(name = "cvcopy")
    private String employeeCVCopy;

    @Column(name = "dvcopy")
    private String employeeDVCopy;

    @Column(name = "fuel_pin")
    private String employeeFuelPin;

    @Column(name = "nok")
    private String employeeNextOfKin;

    @Column(name = "nok_phone")
    private String employeeNextOfKinPhone;

    @Column(name = "date_terminated")
    private LocalDate employeeDateTerminated;

    @Column(name = "date_probation")
    private LocalDate employeeDateProbation;

    @Column(name = "contract_expired")
    private LocalDate employeeContractExpired;

    @Column(name = "idcard")
    private Boolean employeeIdCard;

    @Column(name = "employee_record_form")
    private Boolean employeeRecordForm;

    @Column(name = "other")
    private String employeeOther;

    @Column(name = "occupation")
    private String employeeOccupation;

    @Column(name = "activity_contract")
    private String employeeActivityContract;

    @Column(name = "activity_short_description")
    private String employeeActivityShortDescription;

    @Column(name = "ohs_appointment")
    private Boolean employeeOhsAppointment;

    @Column(name = "compliance_agreement")
    private Boolean employeeComplianceAgreement;

    @Column(name = "roles_responsibilities")
    private Boolean employeeRolesResponsibilities;

    @Column(name = "bw_operator_authority")
    private Boolean employeeBwOperatorAuthority;

    @Column(name = "planned_task_observation")
    private Boolean employeePlannedTaskObservation;

    @Column(name = "drivers_medical_copy")
    private Boolean employeeDriversMedicalCopy;

    @Column(name = "pneumoconsis_medical_bureau_number")
    private String employeePneumoconiosisMedicalBureauNumber;

    @Column(name = "medical_fitness_certificate")
    private Boolean medicalFitnessCertificate;

    @Column(name = "food_handlers_expiry_date")
    private LocalDate employeeFoodHandlersExpiryDate;

    @Column(name = "cv_ref")
    private Boolean employeeCVRef;

    @Column(name = "certificate_of_competence")
    private Boolean employeeCertificateOfCompetence;

    @Column(name = "mobile_number")
    private Integer employeeMobileNumber;

    @Column(name = "comments")
    private String employeeComments;

    @Column(name = "bw_staff_contract")
    private Boolean employeeBwStaffContract;

    @Column(name = "allocated_to_multi_contracts")
    private Boolean employeeAllocatedToMultiContracts;

    @Column(name = "position_on_payslip")
    private String employeePositionOnPayslip;

    @Column(name = "medical_pneumonconsis_expiry_date")
    private LocalDate employeeMedicalPneumonconsisExpiryDate;

    @Column(name = "city")
    private String employeeCity;

    @Column(name = "medical_fitness_certificate_expiry_date")
    private LocalDate employeeMedicalFitnessCertificateExpiryDate;

    @Column(name = "social_security_number")
    private String employeeSocialSecurityNumber;

    @Column(name = "company_vehicle_policy")
    private Boolean employeeCompanyVehiclePolicy;

    @Column(name = "confidentiality_agreement")
    private Boolean employeeConfidentialityAgreement;
    //--------------add more columns as needed------------------//

}
