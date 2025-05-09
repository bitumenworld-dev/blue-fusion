package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelIssuanceType.
 */
@Entity
@Table(name = "fuel_issuance_type")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelIssuanceType implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fuel_issue_type_id")
    private Long fuelIssueTypeId;

    @Column(name = "fuel_issue_type")
    private String fuelIssueType;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuelIssuanceType id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuelIssueTypeId() {
        return this.fuelIssueTypeId;
    }

    public FuelIssuanceType fuelIssueTypeId(Long fuelIssueTypeId) {
        this.setFuelIssueTypeId(fuelIssueTypeId);
        return this;
    }

    public void setFuelIssueTypeId(Long fuelIssueTypeId) {
        this.fuelIssueTypeId = fuelIssueTypeId;
    }

    public String getFuelIssueType() {
        return this.fuelIssueType;
    }

    public FuelIssuanceType fuelIssueType(String fuelIssueType) {
        this.setFuelIssueType(fuelIssueType);
        return this;
    }

    public void setFuelIssueType(String fuelIssueType) {
        this.fuelIssueType = fuelIssueType;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelIssuanceType)) {
            return false;
        }
        return getId() != null && getId().equals(((FuelIssuanceType) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelIssuanceType{" +
            "id=" + getId() +
            ", fuelIssueTypeId=" + getFuelIssueTypeId() +
            ", fuelIssueType='" + getFuelIssueType() + "'" +
            "}";
    }
}
