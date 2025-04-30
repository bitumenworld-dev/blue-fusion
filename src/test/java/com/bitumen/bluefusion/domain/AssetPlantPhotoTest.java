package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.AssetPlantPhotoTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class AssetPlantPhotoTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(AssetPlantPhoto.class);
        AssetPlantPhoto assetPlantPhoto1 = getAssetPlantPhotoSample1();
        AssetPlantPhoto assetPlantPhoto2 = new AssetPlantPhoto();
        assertThat(assetPlantPhoto1).isNotEqualTo(assetPlantPhoto2);

        assetPlantPhoto2.setId(assetPlantPhoto1.getId());
        assertThat(assetPlantPhoto1).isEqualTo(assetPlantPhoto2);

        assetPlantPhoto2 = getAssetPlantPhotoSample2();
        assertThat(assetPlantPhoto1).isNotEqualTo(assetPlantPhoto2);
    }
}
