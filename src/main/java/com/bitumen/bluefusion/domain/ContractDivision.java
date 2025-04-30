package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.DivisionType;
import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalTime;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A ContractDivision.
 */
@Entity
@Table(name = "contract_division")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class ContractDivision implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "contract_division_id")
    private Long contractDivisionId;

    @Column(name = "contract_division_number")
    private String contractDivisionNumber;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "build_smart_reference")
    private String buildSmartReference;

    @Column(name = "shift_start")
    private LocalTime shiftStart;

    @Column(name = "shift_end")
    private LocalTime shiftEnd;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private DivisionType type;

    @Column(name = "completed")
    private Boolean completed;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public ContractDivision id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getContractDivisionId() {
        return this.contractDivisionId;
    }

    public ContractDivision contractDivisionId(Long contractDivisionId) {
        this.setContractDivisionId(contractDivisionId);
        return this;
    }

    public void setContractDivisionId(Long contractDivisionId) {
        this.contractDivisionId = contractDivisionId;
    }

    public String getContractDivisionNumber() {
        return this.contractDivisionNumber;
    }

    public ContractDivision contractDivisionNumber(String contractDivisionNumber) {
        this.setContractDivisionNumber(contractDivisionNumber);
        return this;
    }

    public void setContractDivisionNumber(String contractDivisionNumber) {
        this.contractDivisionNumber = contractDivisionNumber;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public ContractDivision companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getBuildSmartReference() {
        return this.buildSmartReference;
    }

    public ContractDivision buildSmartReference(String buildSmartReference) {
        this.setBuildSmartReference(buildSmartReference);
        return this;
    }

    public void setBuildSmartReference(String buildSmartReference) {
        this.buildSmartReference = buildSmartReference;
    }

    public LocalTime getShiftStart() {
        return this.shiftStart;
    }

    public ContractDivision shiftStart(LocalTime shiftStart) {
        this.setShiftStart(shiftStart);
        return this;
    }

    public void setShiftStart(LocalTime shiftStart) {
        this.shiftStart = shiftStart;
    }

    public LocalTime getShiftEnd() {
        return this.shiftEnd;
    }

    public ContractDivision shiftEnd(LocalTime shiftEnd) {
        this.setShiftEnd(shiftEnd);
        return this;
    }

    public void setShiftEnd(LocalTime shiftEnd) {
        this.shiftEnd = shiftEnd;
    }

    public DivisionType getType() {
        return this.type;
    }

    public ContractDivision type(DivisionType type) {
        this.setType(type);
        return this;
    }

    public void setType(DivisionType type) {
        this.type = type;
    }

    public Boolean getCompleted() {
        return this.completed;
    }

    public ContractDivision completed(Boolean completed) {
        this.setCompleted(completed);
        return this;
    }

    public void setCompleted(Boolean completed) {
        this.completed = completed;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof ContractDivision)) {
            return false;
        }
        return getId() != null && getId().equals(((ContractDivision) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "ContractDivision{" +
            "id=" + getId() +
            ", contractDivisionId=" + getContractDivisionId() +
            ", contractDivisionNumber='" + getContractDivisionNumber() + "'" +
            ", companyId=" + getCompanyId() +
            ", buildSmartReference='" + getBuildSmartReference() + "'" +
            ", shiftStart='" + getShiftStart() + "'" +
            ", shiftEnd='" + getShiftEnd() + "'" +
            ", type='" + getType() + "'" +
            ", completed='" + getCompleted() + "'" +
            "}";
    }
}
