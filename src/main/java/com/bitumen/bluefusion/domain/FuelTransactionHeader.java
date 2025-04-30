package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.FuelType;
import jakarta.persistence.*;
import java.io.Serializable;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

/**
 * A FuelTransactionHeader.
 */
@Entity
@Table(name = "fuel_transaction_header")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
@SuppressWarnings("common-java:DuplicatedBlocks")
public class FuelTransactionHeader implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "fuel_transaction_header_id")
    private Long fuelTransactionHeaderId;

    @Column(name = "company_id")
    private Long companyId;

    @Column(name = "supplier_id")
    private Long supplierId;

    @Column(name = "transaction_type_id")
    private Long transactionTypeId;

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

    @Column(name = "attendee_id")
    private Long attendeeId;

    @Column(name = "operator_id")
    private Long operatorId;

    // jhipster-needle-entity-add-field - JHipster will add fields here

    public Long getId() {
        return this.id;
    }

    public FuelTransactionHeader id(Long id) {
        this.setId(id);
        return this;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFuelTransactionHeaderId() {
        return this.fuelTransactionHeaderId;
    }

    public FuelTransactionHeader fuelTransactionHeaderId(Long fuelTransactionHeaderId) {
        this.setFuelTransactionHeaderId(fuelTransactionHeaderId);
        return this;
    }

    public void setFuelTransactionHeaderId(Long fuelTransactionHeaderId) {
        this.fuelTransactionHeaderId = fuelTransactionHeaderId;
    }

    public Long getCompanyId() {
        return this.companyId;
    }

    public FuelTransactionHeader companyId(Long companyId) {
        this.setCompanyId(companyId);
        return this;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public Long getSupplierId() {
        return this.supplierId;
    }

    public FuelTransactionHeader supplierId(Long supplierId) {
        this.setSupplierId(supplierId);
        return this;
    }

    public void setSupplierId(Long supplierId) {
        this.supplierId = supplierId;
    }

    public Long getTransactionTypeId() {
        return this.transactionTypeId;
    }

    public FuelTransactionHeader transactionTypeId(Long transactionTypeId) {
        this.setTransactionTypeId(transactionTypeId);
        return this;
    }

    public void setTransactionTypeId(Long transactionTypeId) {
        this.transactionTypeId = transactionTypeId;
    }

    public FuelType getFuelType() {
        return this.fuelType;
    }

    public FuelTransactionHeader fuelType(FuelType fuelType) {
        this.setFuelType(fuelType);
        return this;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public String getOrderNumber() {
        return this.orderNumber;
    }

    public FuelTransactionHeader orderNumber(String orderNumber) {
        this.setOrderNumber(orderNumber);
        return this;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getDeliveryNote() {
        return this.deliveryNote;
    }

    public FuelTransactionHeader deliveryNote(String deliveryNote) {
        this.setDeliveryNote(deliveryNote);
        return this;
    }

    public void setDeliveryNote(String deliveryNote) {
        this.deliveryNote = deliveryNote;
    }

    public String getGrvNumber() {
        return this.grvNumber;
    }

    public FuelTransactionHeader grvNumber(String grvNumber) {
        this.setGrvNumber(grvNumber);
        return this;
    }

    public void setGrvNumber(String grvNumber) {
        this.grvNumber = grvNumber;
    }

    public String getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public FuelTransactionHeader invoiceNumber(String invoiceNumber) {
        this.setInvoiceNumber(invoiceNumber);
        return this;
    }

    public void setInvoiceNumber(String invoiceNumber) {
        this.invoiceNumber = invoiceNumber;
    }

    public Float getPricePerLitre() {
        return this.pricePerLitre;
    }

    public FuelTransactionHeader pricePerLitre(Float pricePerLitre) {
        this.setPricePerLitre(pricePerLitre);
        return this;
    }

    public void setPricePerLitre(Float pricePerLitre) {
        this.pricePerLitre = pricePerLitre;
    }

    public String getNote() {
        return this.note;
    }

    public FuelTransactionHeader note(String note) {
        this.setNote(note);
        return this;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getRegistrationNumber() {
        return this.registrationNumber;
    }

    public FuelTransactionHeader registrationNumber(String registrationNumber) {
        this.setRegistrationNumber(registrationNumber);
        return this;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public Long getAttendeeId() {
        return this.attendeeId;
    }

    public FuelTransactionHeader attendeeId(Long attendeeId) {
        this.setAttendeeId(attendeeId);
        return this;
    }

    public void setAttendeeId(Long attendeeId) {
        this.attendeeId = attendeeId;
    }

    public Long getOperatorId() {
        return this.operatorId;
    }

    public FuelTransactionHeader operatorId(Long operatorId) {
        this.setOperatorId(operatorId);
        return this;
    }

    public void setOperatorId(Long operatorId) {
        this.operatorId = operatorId;
    }

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof FuelTransactionHeader)) {
            return false;
        }
        return getId() != null && getId().equals(((FuelTransactionHeader) o).getId());
    }

    @Override
    public int hashCode() {
        // see https://vladmihalcea.com/how-to-implement-equals-and-hashcode-using-the-jpa-entity-identifier/
        return getClass().hashCode();
    }

    // prettier-ignore
    @Override
    public String toString() {
        return "FuelTransactionHeader{" +
            "id=" + getId() +
            ", fuelTransactionHeaderId=" + getFuelTransactionHeaderId() +
            ", companyId=" + getCompanyId() +
            ", supplierId=" + getSupplierId() +
            ", transactionTypeId=" + getTransactionTypeId() +
            ", fuelType='" + getFuelType() + "'" +
            ", orderNumber='" + getOrderNumber() + "'" +
            ", deliveryNote='" + getDeliveryNote() + "'" +
            ", grvNumber='" + getGrvNumber() + "'" +
            ", invoiceNumber='" + getInvoiceNumber() + "'" +
            ", pricePerLitre=" + getPricePerLitre() +
            ", note='" + getNote() + "'" +
            ", registrationNumber='" + getRegistrationNumber() + "'" +
            ", attendeeId=" + getAttendeeId() +
            ", operatorId=" + getOperatorId() +
            "}";
    }
}
