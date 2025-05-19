package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
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
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelPump extends AbstractAuditingEntity<AssetPlant> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_pump_id")
    private Long fuelPumpId;

    @Column(name = "fuel_pump_number")
    private String fuelPumpNumber;

    @ManyToOne
    @JoinColumn(name = "current_storage_unit_id")
    private AssetPlant currentStorageUnitId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.fuelPumpId;
    }

    public void setId(Long id) {
        this.fuelPumpId = id;
    }
}
