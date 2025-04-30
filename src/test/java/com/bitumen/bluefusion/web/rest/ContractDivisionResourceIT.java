package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.ContractDivisionAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.ContractDivision;
import com.bitumen.bluefusion.domain.enumeration.DivisionType;
import com.bitumen.bluefusion.repository.ContractDivisionRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalTime;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

/**
 * Integration tests for the {@link ContractDivisionResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ContractDivisionResourceIT {

    private static final Long DEFAULT_CONTRACT_DIVISION_ID = 1L;
    private static final Long UPDATED_CONTRACT_DIVISION_ID = 2L;

    private static final String DEFAULT_CONTRACT_DIVISION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CONTRACT_DIVISION_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String DEFAULT_BUILD_SMART_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_BUILD_SMART_REFERENCE = "BBBBBBBBBB";

    private static final LocalTime DEFAULT_SHIFT_START = LocalTime.NOON;
    private static final LocalTime UPDATED_SHIFT_START = LocalTime.MAX.withNano(0);

    private static final LocalTime DEFAULT_SHIFT_END = LocalTime.NOON;
    private static final LocalTime UPDATED_SHIFT_END = LocalTime.MAX.withNano(0);

    private static final DivisionType DEFAULT_TYPE = DivisionType.CONTRACT;
    private static final DivisionType UPDATED_TYPE = DivisionType.DIVISION;

    private static final Boolean DEFAULT_COMPLETED = false;
    private static final Boolean UPDATED_COMPLETED = true;

    private static final String ENTITY_API_URL = "/api/contract-divisions";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ContractDivisionRepository contractDivisionRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restContractDivisionMockMvc;

    private ContractDivision contractDivision;

    private ContractDivision insertedContractDivision;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractDivision createEntity() {
        return new ContractDivision()
            .contractDivisionId(DEFAULT_CONTRACT_DIVISION_ID)
            .contractDivisionNumber(DEFAULT_CONTRACT_DIVISION_NUMBER)
            .companyId(DEFAULT_COMPANY_ID)
            .buildSmartReference(DEFAULT_BUILD_SMART_REFERENCE)
            .shiftStart(DEFAULT_SHIFT_START)
            .shiftEnd(DEFAULT_SHIFT_END)
            .type(DEFAULT_TYPE)
            .completed(DEFAULT_COMPLETED);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ContractDivision createUpdatedEntity() {
        return new ContractDivision()
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .contractDivisionNumber(UPDATED_CONTRACT_DIVISION_NUMBER)
            .companyId(UPDATED_COMPANY_ID)
            .buildSmartReference(UPDATED_BUILD_SMART_REFERENCE)
            .shiftStart(UPDATED_SHIFT_START)
            .shiftEnd(UPDATED_SHIFT_END)
            .type(UPDATED_TYPE)
            .completed(UPDATED_COMPLETED);
    }

    @BeforeEach
    void initTest() {
        contractDivision = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedContractDivision != null) {
            contractDivisionRepository.delete(insertedContractDivision);
            insertedContractDivision = null;
        }
    }

    @Test
    @Transactional
    void createContractDivision() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ContractDivision
        var returnedContractDivision = om.readValue(
            restContractDivisionMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contractDivision)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ContractDivision.class
        );

        // Validate the ContractDivision in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertContractDivisionUpdatableFieldsEquals(returnedContractDivision, getPersistedContractDivision(returnedContractDivision));

        insertedContractDivision = returnedContractDivision;
    }

    @Test
    @Transactional
    void createContractDivisionWithExistingId() throws Exception {
        // Create the ContractDivision with an existing ID
        contractDivision.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restContractDivisionMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contractDivision)))
            .andExpect(status().isBadRequest());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllContractDivisions() throws Exception {
        // Initialize the database
        insertedContractDivision = contractDivisionRepository.saveAndFlush(contractDivision);

        // Get all the contractDivisionList
        restContractDivisionMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(contractDivision.getId().intValue())))
            .andExpect(jsonPath("$.[*].contractDivisionId").value(hasItem(DEFAULT_CONTRACT_DIVISION_ID.intValue())))
            .andExpect(jsonPath("$.[*].contractDivisionNumber").value(hasItem(DEFAULT_CONTRACT_DIVISION_NUMBER)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].buildSmartReference").value(hasItem(DEFAULT_BUILD_SMART_REFERENCE)))
            .andExpect(jsonPath("$.[*].shiftStart").value(hasItem(DEFAULT_SHIFT_START.toString())))
            .andExpect(jsonPath("$.[*].shiftEnd").value(hasItem(DEFAULT_SHIFT_END.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].completed").value(hasItem(DEFAULT_COMPLETED)));
    }

    @Test
    @Transactional
    void getContractDivision() throws Exception {
        // Initialize the database
        insertedContractDivision = contractDivisionRepository.saveAndFlush(contractDivision);

        // Get the contractDivision
        restContractDivisionMockMvc
            .perform(get(ENTITY_API_URL_ID, contractDivision.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(contractDivision.getId().intValue()))
            .andExpect(jsonPath("$.contractDivisionId").value(DEFAULT_CONTRACT_DIVISION_ID.intValue()))
            .andExpect(jsonPath("$.contractDivisionNumber").value(DEFAULT_CONTRACT_DIVISION_NUMBER))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.buildSmartReference").value(DEFAULT_BUILD_SMART_REFERENCE))
            .andExpect(jsonPath("$.shiftStart").value(DEFAULT_SHIFT_START.toString()))
            .andExpect(jsonPath("$.shiftEnd").value(DEFAULT_SHIFT_END.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.completed").value(DEFAULT_COMPLETED));
    }

    @Test
    @Transactional
    void getNonExistingContractDivision() throws Exception {
        // Get the contractDivision
        restContractDivisionMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingContractDivision() throws Exception {
        // Initialize the database
        insertedContractDivision = contractDivisionRepository.saveAndFlush(contractDivision);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contractDivision
        ContractDivision updatedContractDivision = contractDivisionRepository.findById(contractDivision.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedContractDivision are not directly saved in db
        em.detach(updatedContractDivision);
        updatedContractDivision
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .contractDivisionNumber(UPDATED_CONTRACT_DIVISION_NUMBER)
            .companyId(UPDATED_COMPANY_ID)
            .buildSmartReference(UPDATED_BUILD_SMART_REFERENCE)
            .shiftStart(UPDATED_SHIFT_START)
            .shiftEnd(UPDATED_SHIFT_END)
            .type(UPDATED_TYPE)
            .completed(UPDATED_COMPLETED);

        restContractDivisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedContractDivision.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedContractDivision))
            )
            .andExpect(status().isOk());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedContractDivisionToMatchAllProperties(updatedContractDivision);
    }

    @Test
    @Transactional
    void putNonExistingContractDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contractDivision.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractDivisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, contractDivision.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contractDivision))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchContractDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contractDivision.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractDivisionMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(contractDivision))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamContractDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contractDivision.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractDivisionMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(contractDivision)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateContractDivisionWithPatch() throws Exception {
        // Initialize the database
        insertedContractDivision = contractDivisionRepository.saveAndFlush(contractDivision);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contractDivision using partial update
        ContractDivision partialUpdatedContractDivision = new ContractDivision();
        partialUpdatedContractDivision.setId(contractDivision.getId());

        partialUpdatedContractDivision
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .contractDivisionNumber(UPDATED_CONTRACT_DIVISION_NUMBER)
            .companyId(UPDATED_COMPANY_ID)
            .shiftEnd(UPDATED_SHIFT_END);

        restContractDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractDivision.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContractDivision))
            )
            .andExpect(status().isOk());

        // Validate the ContractDivision in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContractDivisionUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedContractDivision, contractDivision),
            getPersistedContractDivision(contractDivision)
        );
    }

    @Test
    @Transactional
    void fullUpdateContractDivisionWithPatch() throws Exception {
        // Initialize the database
        insertedContractDivision = contractDivisionRepository.saveAndFlush(contractDivision);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the contractDivision using partial update
        ContractDivision partialUpdatedContractDivision = new ContractDivision();
        partialUpdatedContractDivision.setId(contractDivision.getId());

        partialUpdatedContractDivision
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .contractDivisionNumber(UPDATED_CONTRACT_DIVISION_NUMBER)
            .companyId(UPDATED_COMPANY_ID)
            .buildSmartReference(UPDATED_BUILD_SMART_REFERENCE)
            .shiftStart(UPDATED_SHIFT_START)
            .shiftEnd(UPDATED_SHIFT_END)
            .type(UPDATED_TYPE)
            .completed(UPDATED_COMPLETED);

        restContractDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedContractDivision.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedContractDivision))
            )
            .andExpect(status().isOk());

        // Validate the ContractDivision in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertContractDivisionUpdatableFieldsEquals(
            partialUpdatedContractDivision,
            getPersistedContractDivision(partialUpdatedContractDivision)
        );
    }

    @Test
    @Transactional
    void patchNonExistingContractDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contractDivision.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restContractDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, contractDivision.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contractDivision))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchContractDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contractDivision.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractDivisionMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(contractDivision))
            )
            .andExpect(status().isBadRequest());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamContractDivision() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        contractDivision.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restContractDivisionMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(contractDivision)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ContractDivision in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteContractDivision() throws Exception {
        // Initialize the database
        insertedContractDivision = contractDivisionRepository.saveAndFlush(contractDivision);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the contractDivision
        restContractDivisionMockMvc
            .perform(delete(ENTITY_API_URL_ID, contractDivision.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return contractDivisionRepository.count();
    }

    protected void assertIncrementedRepositoryCount(long countBefore) {
        assertThat(countBefore + 1).isEqualTo(getRepositoryCount());
    }

    protected void assertDecrementedRepositoryCount(long countBefore) {
        assertThat(countBefore - 1).isEqualTo(getRepositoryCount());
    }

    protected void assertSameRepositoryCount(long countBefore) {
        assertThat(countBefore).isEqualTo(getRepositoryCount());
    }

    protected ContractDivision getPersistedContractDivision(ContractDivision contractDivision) {
        return contractDivisionRepository.findById(contractDivision.getId()).orElseThrow();
    }

    protected void assertPersistedContractDivisionToMatchAllProperties(ContractDivision expectedContractDivision) {
        assertContractDivisionAllPropertiesEquals(expectedContractDivision, getPersistedContractDivision(expectedContractDivision));
    }

    protected void assertPersistedContractDivisionToMatchUpdatableProperties(ContractDivision expectedContractDivision) {
        assertContractDivisionAllUpdatablePropertiesEquals(
            expectedContractDivision,
            getPersistedContractDivision(expectedContractDivision)
        );
    }
}
