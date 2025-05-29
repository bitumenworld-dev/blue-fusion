package com.bitumen.bluefusion.domain;

import jakarta.persistence.*;
import java.io.Serializable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "plant_category")
public class PlantCategory extends AbstractAuditingEntity<PlantCategory> implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "plant_category_id")
    private Long plantCategoryId;

    @Column(name = "plant_category_code")
    private String plantCategoryCode;

    @Column(name = "plant_category_description")
    private String plantCategoryDescription;
}
