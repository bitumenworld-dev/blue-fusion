package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.PlantCategoryTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class PlantCategoryTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PlantCategory.class);
        PlantCategory plantCategory1 = getPlantCategorySample1();
        PlantCategory plantCategory2 = new PlantCategory();
        assertThat(plantCategory1).isNotEqualTo(plantCategory2);

        plantCategory2.setId(plantCategory1.getId());
        assertThat(plantCategory1).isEqualTo(plantCategory2);

        plantCategory2 = getPlantCategorySample2();
        assertThat(plantCategory1).isNotEqualTo(plantCategory2);
    }
}
