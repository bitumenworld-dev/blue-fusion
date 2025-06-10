package com.bitumen.bluefusion.service.fuelTransaction.dto;

import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.Employee;
import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.Storage;
import com.bitumen.bluefusion.domain.enumeration.FuelTransactionType;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import java.util.Optional;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FuelTransactionHeaderResponse {

    private Long fuelTransactionHeaderId;
    private String company;
    private String supplier;
    private FuelTransactionType fuelTransactionType;
    private String storageUnit;
    private FuelType fuelType;
    private String orderNumber;
    private String deliveryNote;
    private String grvNumber;
    private String invoiceNumber;
    private Float pricePerLitre;
    private String note;

    private String attendee;
    private String operator;

    public static FuelTransactionHeaderResponse fuelTransactionHeaderResponseBuilder(FuelTransactionHeader fuelTransactionHeader) {
        return FuelTransactionHeaderResponse.builder()
            .fuelTransactionHeaderId(fuelTransactionHeader.getFuelTransactionHeaderId())
            .company(fuelTransactionHeader.getCompany().getName())
            .supplier(extractSupplier(fuelTransactionHeader))
            .fuelTransactionType(fuelTransactionHeader.getFuelTransactionType())
            .storageUnit(extractStorageUnit(fuelTransactionHeader))
            .fuelType(fuelTransactionHeader.getFuelType())
            .orderNumber(fuelTransactionHeader.getOrderNumber())
            .deliveryNote(fuelTransactionHeader.getDeliveryNote())
            .grvNumber(fuelTransactionHeader.getGrvNumber())
            .invoiceNumber(fuelTransactionHeader.getInvoiceNumber())
            .pricePerLitre(fuelTransactionHeader.getPricePerLitre())
            .note(fuelTransactionHeader.getNote())
            .attendee(extractAttendee(fuelTransactionHeader))
            .operator(extractOperator(fuelTransactionHeader))
            .build();
    }

    private static String extractSupplier(FuelTransactionHeader fuelTransactionHeader) {
        return Optional.ofNullable(fuelTransactionHeader.getSupplier()).map(Company::getName).orElse(null);
    }

    private static String extractStorageUnit(FuelTransactionHeader fuelTransactionHeader) {
        return Optional.ofNullable(fuelTransactionHeader.getStorageUnit()).map(Storage::getStorageCode).orElse(null);
    }

    private static String extractAttendee(FuelTransactionHeader fuelTransactionHeader) {
        return Optional.ofNullable(fuelTransactionHeader.getAttendee()).map(Employee::getFullName).orElse(null);
    }

    private static String extractOperator(FuelTransactionHeader fuelTransactionHeader) {
        return Optional.ofNullable(fuelTransactionHeader.getOperator()).map(Employee::getFullName).orElse(null);
    }
}
