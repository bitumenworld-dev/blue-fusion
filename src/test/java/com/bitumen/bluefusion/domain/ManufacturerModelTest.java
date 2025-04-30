package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.ManufacturerModelTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ManufacturerModelTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ManufacturerModel.class);
        ManufacturerModel manufacturerModel1 = getManufacturerModelSample1();
        ManufacturerModel manufacturerModel2 = new ManufacturerModel();
        assertThat(manufacturerModel1).isNotEqualTo(manufacturerModel2);

        manufacturerModel2.setId(manufacturerModel1.getId());
        assertThat(manufacturerModel1).isEqualTo(manufacturerModel2);

        manufacturerModel2 = getManufacturerModelSample2();
        assertThat(manufacturerModel1).isNotEqualTo(manufacturerModel2);
    }
}
