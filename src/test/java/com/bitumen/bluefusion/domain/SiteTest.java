package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.SiteTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Site.class);
        Site site1 = getSiteSample1();
        Site site2 = new Site();
        assertThat(site1).isNotEqualTo(site2);

        site2.setId(site1.getId());
        assertThat(site1).isEqualTo(site2);

        site2 = getSiteSample2();
        assertThat(site1).isNotEqualTo(site2);
    }
}
