package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A Site.
 */
@Entity
@Table(name = "site")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class Site implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "site_id")
    private Long siteId;

    @Column(name = "site_name")
    private String siteName;

    @Column(name = "latitude")
    private String latitude;

    @Column(name = "longitude")
    private String longitude;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "site_notes")
    private String siteNotes;

    @Column(name = "site_image_url")
    private String siteImageUrl;

    @Column(name = "company_id")
    private Long companyId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public Site id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getSiteId() {
        return this.siteId;
    }

    public Site siteId(Long siteId) {
        this.setSiteId(siteId);
        return this;
    }

    public void setSiteId(Long siteId) {
        this.siteId = siteId;
    }

    public String getSiteName() {
        return this.siteName;
    }

    public Site siteName(String siteName) {
        this.setSiteName(siteName);
        return this;
    }

    public void setSiteName(String siteName) {
        this.siteName = siteName;
    }

    public String getLatitude() {
        return this.latitude;
    }

    public Site latitude(String latitude) {
        this.setLatitude(latitude);
        return this;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return this.longitude;
    }

    public Site longitude(String longitude) {
        this.setLongitude(longitude);
        return this;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public Site isActive(Boolean isActive) {
        this.setIsActive(isActive);
        return this;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getSiteNotes() {
        return this.siteNotes;
    }

    public Site siteNotes(String siteNotes) {
        this.setSiteNotes(siteNotes);
        return this;
    }

    public void setSiteNotes(String siteNotes) {
        this.siteNotes = siteNotes;
    }

    public String getSiteImageUrl() {
        return this.siteImageUrl;
    }

    public Site siteImageUrl(String siteImageUrl) {
        this.setSiteImageUrl(siteImageUrl);
        return this;
    }

    public void setSiteImageUrl(String siteImageUrl) {
        this.siteImageUrl = siteImageUrl;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public Site companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Site)) {
            return false;
        }
        return getId() != null && getId().equals(((Site) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "Site{" +
            "id=" + getId() +
            ", siteId=" + getSiteId() +
            ", siteName='" + getSiteName() + "'" +
            ", latitude='" + getLatitude() + "'" +
            ", longitude='" + getLongitude() + "'" +
            ", isActive='" + getIsActive() + "'" +
            ", siteNotes='" + getSiteNotes() + "'" +
            ", siteImageUrl='" + getSiteImageUrl() + "'" +
            ", companyId=" + getCompanyId() +
            "}";
    }
}
