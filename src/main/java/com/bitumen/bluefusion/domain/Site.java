package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

/**
 * A Site.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "site")
//@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Site extends AbstractAuditingEntity<Site> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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

    @Column(name = "site_image")
    private byte[] siteImage;

    @ManyToOne(fetch = FetchType.LAZY)
    private Company company;

    public Long getId() {
        return this.siteId;
    }
}
