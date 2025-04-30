package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.AssetPlantTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetPlantTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetPlant.class);
        AssetPlant assetPlant1 = getAssetPlantSample1();
        AssetPlant assetPlant2 = new AssetPlant();
        assertThat(assetPlant1).isNotEqualTo(assetPlant2);

        assetPlant2.setId(assetPlant1.getId());
        assertThat(assetPlant1).isEqualTo(assetPlant2);

        assetPlant2 = getAssetPlantSample2();
        assertThat(assetPlant1).isNotEqualTo(assetPlant2);
    }
}
