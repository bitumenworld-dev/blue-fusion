package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.SiteContractTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class SiteContractTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(SiteContract.class);
        SiteContract siteContract1 = getSiteContractSample1();
        SiteContract siteContract2 = new SiteContract();
        assertThat(siteContract1).isNotEqualTo(siteContract2);

        siteContract2.setId(siteContract1.getId());
        assertThat(siteContract1).isEqualTo(siteContract2);

        siteContract2 = getSiteContractSample2();
        assertThat(siteContract1).isNotEqualTo(siteContract2);
    }
}
