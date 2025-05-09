package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.FuelType;
import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

/**
 * A FuelTransactionHeader.
 */
@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "fuel_transaction_header")
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelTransactionHeader extends AbstractAuditingEntity<FuelTransactionHeader> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fuel_transaction_header_id")
    private Long fuelTransactionHeaderId;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company companyId;

    @ManyToOne
    @JoinColumn(name = "supplier_id")
    private Company supplierId;

    @ManyToOne
    @JoinColumn(name = "transaction_type_id")
    private FuelTransactionType transactionTypeId;

    @ManyToOne
    @JoinColumn(name = "storage_unit_id")
    private AssetPlant storageUnitId;

    @Enumerated(EnumType.STRING)
    @Column(name = "fuel_type")
    private FuelType fuelType;

    @Column(name = "order_number")
    private String orderNumber;

    @Column(name = "delivery_note")
    private String deliveryNote;

    @Column(name = "grv_number")
    private String grvNumber;

    @Column(name = "invoice_number")
    private String invoiceNumber;

    @Column(name = "price_per_litre")
    private Float pricePerLitre;

    @Column(name = "note")
    private String note;

    @Column(name = "registration_number")
    private String registrationNumber;

    @ManyToOne
    @JoinColumn(name = "attendee_id")
    private Employee attendeeId;

    @ManyToOne
    @JoinColumn(name = "operator_id")
    private Employee operatorId;
}
