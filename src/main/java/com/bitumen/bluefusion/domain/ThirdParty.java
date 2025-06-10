package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "third_party")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class ThirdParty extends AbstractAuditingEntity<ThirdParty> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "third_party_id")
    private Long thirdPartyId;

    @Column(name = "third_party_name")
    private String thirdPartyName;

    @Column(name = "third_party_short_name")
    private String thirdPartyShortName;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "uses_fuel_system")
    private Boolean usesFuelSystem;
}
