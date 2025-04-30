package com.bitumen.bluefusion.domain;

import static com.bitumen.bluefusion.domain.ContractDivisionTestSamples.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.bitumen.bluefusion.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ContractDivisionTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContractDivision.class);
        ContractDivision contractDivision1 = getContractDivisionSample1();
        ContractDivision contractDivision2 = new ContractDivision();
        assertThat(contractDivision1).isNotEqualTo(contractDivision2);

        contractDivision2.setId(contractDivision1.getId());
        assertThat(contractDivision1).isEqualTo(contractDivision2);

        contractDivision2 = getContractDivisionSample2();
        assertThat(contractDivision1).isNotEqualTo(contractDivision2);
    }
}
