package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.AssetPlantServiceReadingTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetPlantServiceReadingTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetPlantServiceReading.class);
        AssetPlantServiceReading assetPlantServiceReading1 = getAssetPlantServiceReadingSample1();
        AssetPlantServiceReading assetPlantServiceReading2 = new AssetPlantServiceReading();
        assertThat(assetPlantServiceReading1).isNotEqualTo(assetPlantServiceReading2);

        assetPlantServiceReading2.setId(assetPlantServiceReading1.getId());
        assertThat(assetPlantServiceReading1).isEqualTo(assetPlantServiceReading2);

        assetPlantServiceReading2 = getAssetPlantServiceReadingSample2();
        assertThat(assetPlantServiceReading1).isNotEqualTo(assetPlantServiceReading2);
    }
}
