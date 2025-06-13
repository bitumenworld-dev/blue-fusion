package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@EqualsAndHashCode(callSuper = false)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "smr_reading")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SmrReading extends AbstractAuditingEntity<SmrReading> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "smr_reading_id")
    private Long smrReadingId;

    @ManyToOne
    @JoinColumn(name = "asset_plant_id")
    private AssetPlant assetPlant;

    @Column(name = "smr_reading")
    private Float smrReadingValue;

    @Column(name = "reading_date_time")
    private java.time.LocalDateTime readingDateTime;

    @Column(name = "unit", length = 10)
    private String unit;

    @Column(name = "fuel_transaction_header_id")
    private Integer fuelTransactionHeaderId;

    @Column(name = "whatsapp_number", length = 50)
    private String whatsappNumber;
}
