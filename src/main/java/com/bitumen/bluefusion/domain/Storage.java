package com.bitumen.bluefusion.domain;

import com.bitumen.bluefusion.domain.enumeration.StorageContent;
import jakarta.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;
import lombok.*;

@Data
@Entity
@Table(name = "storage")
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Storage extends AbstractAuditingEntity<Storage> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "storage_id")
    private Long storageId;

    private String storageCode;

    private String buildSmartCode;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    @ManyToOne
    @JoinColumn(name = "site_id")
    private Site site;

    private String name;

    private Boolean isFixed;

    private Double capacity;

    @Transient
    private Double fuelBalance;

    private String accessKey;

    @Column(name = "storage_content")
    private String storageContent;

    private Boolean isActive;

    @Transient
    private List<StorageContent> storageContents;

    @PostLoad
    private void onLoad() {
        if (storageContent != null && !storageContent.isEmpty()) {
            storageContents = Arrays.stream(storageContent.split(","))
                .map(String::trim)
                .map(StorageContent::valueOf)
                .collect(Collectors.toList());
        } else {
            storageContents = new ArrayList<>();
        }
    }

    @PrePersist
    @PreUpdate
    private void onSave() {
        if (storageContents != null && !storageContents.isEmpty()) {
            storageContent = storageContents.stream().map(StorageContent::name).collect(Collectors.joining(","));
        }
    }
}
