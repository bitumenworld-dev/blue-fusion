package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "site")
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

    @Column(name = "has_workshop")
    private Boolean hasWorkshop;

    @Column(name = "site_notes")
    private String siteNotes;

    @Column(name = "site_image")
    private byte[] siteImage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;
}
