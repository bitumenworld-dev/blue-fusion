package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

/**
 * A Company.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "company")
public class Company extends AbstractAuditingEntity<Company> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "is_iac")
    private Boolean isIAC;

    @Column(name = "access_key")
    private String access_key;

    @Column(name = "uses_fuel_system")
    private Boolean usesFuelSystem;

    @Column(name = "is_active")
    private Boolean isActive;
    // jhipster-needle-entity-add-field - JHipster will add fields here

}
