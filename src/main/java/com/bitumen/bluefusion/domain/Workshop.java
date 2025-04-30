package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Workshop.
 */
@Entity
@Table(name = "workshop")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Workshop implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "workshop_id")
    private Long workshopId;

    @Column(name = "site_id")
    private Long siteId;

    @Column(name = "workshop_name")
    private String workshopName;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Workshop id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getWorkshopId() {
        return this.workshopId;
    }

    public Workshop workshopId(Long workshopId) {
        this.setWorkshopId(workshopId);
        return this;
    }

    public void setWorkshopId(Long workshopId) {
        this.workshopId = workshopId;
    }

    public Long getSiteId() {
        return this.siteId;
    }

    public Workshop siteId(Long siteId) {
        this.setSiteId(siteId);
        return this;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getWorkshopName() {
        return this.workshopName;
    }

    public Workshop workshopName(String workshopName) {
        this.setWorkshopName(workshopName);
        return this;
    }

    public void setWorkshopName(String workshopName) {
        this.workshopName = workshopName;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Workshop)) {
            return false;
        }
        return getId() != null && getId().equals(((Workshop) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Workshop{" +
            "id=" + getId() +
            ", workshopId=" + getWorkshopId() +
            ", siteId=" + getSiteId() +
            ", workshopName='" + getWorkshopName() + "'" +
            "}";
    }
}
