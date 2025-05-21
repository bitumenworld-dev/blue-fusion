package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelPump.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fuel_pump")
public class FuelPump extends AbstractAuditingEntity<FuelPump> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_pump_id")
    private Long fuelPumpId;

    @Column(name = "fuel_pump_code")
    private String fuelPumpCode;

    private String description;

    @ManyToOne
    @JoinColumn(name = "current_storage_id")
    private Storage currentStorageUnit;

    @Column(name = "is_active")
    private Boolean isActive;

    @Column(name = "valid_from")
    private LocalDate validFrom;
}
