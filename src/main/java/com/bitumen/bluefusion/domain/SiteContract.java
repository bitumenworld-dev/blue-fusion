package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A SiteContract.
 */
@Entity
@Table(name = "site_contract")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class SiteContract implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "site_contract_id")
    private Long siteContractId;

    @Column(name = "site_id")
    private Long siteId;

    @Column(name = "contract_id")
    private Long contractId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public SiteContract id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteContractId() {
        return this.siteContractId;
    }

    public SiteContract siteContractId(Long siteContractId) {
        this.setSiteContractId(siteContractId);
        return this;
    }

    public void setSiteContractId(Long siteContractId) {
        this.siteContractId = siteContractId;
    }

    public Long getSiteId() {
        return this.siteId;
    }

    public SiteContract siteId(Long siteId) {
        this.setSiteId(siteId);
        return this;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public Long getContractId() {
        return this.contractId;
    }

    public SiteContract contractId(Long contractId) {
        this.setContractId(contractId);
        return this;
    }

    public void setContractId(Long contractId) {
        this.contractId = contractId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SiteContract)) {
            return false;
        }
        return getId() != null && getId().equals(((SiteContract) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "SiteContract{" +
            "id=" + getId() +
            ", siteContractId=" + getSiteContractId() +
            ", siteId=" + getSiteId() +
            ", contractId=" + getContractId() +
            "}";
    }
}
