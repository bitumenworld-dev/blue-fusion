package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.SiteContractAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.SiteContract;
import com.bitumen.bluefusion.repository.SiteContractRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
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
 * Integration tests for the {@link SiteContractResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteContractResourceIT {

    private static final Long DEFAULT_SITE_CONTRACT_ID = 1L;
    private static final Long UPDATED_SITE_CONTRACT_ID = 2L;

    private static final Long DEFAULT_SITE_ID = 1L;
    private static final Long UPDATED_SITE_ID = 2L;

    private static final Long DEFAULT_CONTRACT_ID = 1L;
    private static final Long UPDATED_CONTRACT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/site-contracts";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SiteContractRepository siteContractRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteContractMockMvc;

    private SiteContract siteContract;

    private SiteContract insertedSiteContract;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteContract createEntity() {
        return new SiteContract().siteContractId(DEFAULT_SITE_CONTRACT_ID).siteId(DEFAULT_SITE_ID).contractId(DEFAULT_CONTRACT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static SiteContract createUpdatedEntity() {
        return new SiteContract().siteContractId(UPDATED_SITE_CONTRACT_ID).siteId(UPDATED_SITE_ID).contractId(UPDATED_CONTRACT_ID);
    }

    @BeforeEach
    void initTest() {
        siteContract = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSiteContract != null) {
            siteContractRepository.delete(insertedSiteContract);
            insertedSiteContract = null;
        }
    }

    @Test
    @Transactional
    void createSiteContract() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the SiteContract
        var returnedSiteContract = om.readValue(
            restSiteContractMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteContract)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            SiteContract.class
        );

        // Validate the SiteContract in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSiteContractUpdatableFieldsEquals(returnedSiteContract, getPersistedSiteContract(returnedSiteContract));

        insertedSiteContract = returnedSiteContract;
    }

    @Test
    @Transactional
    void createSiteContractWithExistingId() throws Exception {
        // Create the SiteContract with an existing ID
        siteContract.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteContractMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteContract)))
            .andExpect(status().isBadRequest());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSiteContracts() throws Exception {
        // Initialize the database
        insertedSiteContract = siteContractRepository.saveAndFlush(siteContract);

        // Get all the siteContractList
        restSiteContractMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(siteContract.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteContractId").value(hasItem(DEFAULT_SITE_CONTRACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID.intValue())))
            .andExpect(jsonPath("$.[*].contractId").value(hasItem(DEFAULT_CONTRACT_ID.intValue())));
    }

    @Test
    @Transactional
    void getSiteContract() throws Exception {
        // Initialize the database
        insertedSiteContract = siteContractRepository.saveAndFlush(siteContract);

        // Get the siteContract
        restSiteContractMockMvc
            .perform(get(ENTITY_API_URL_ID, siteContract.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(siteContract.getId().intValue()))
            .andExpect(jsonPath("$.siteContractId").value(DEFAULT_SITE_CONTRACT_ID.intValue()))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID.intValue()))
            .andExpect(jsonPath("$.contractId").value(DEFAULT_CONTRACT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSiteContract() throws Exception {
        // Get the siteContract
        restSiteContractMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSiteContract() throws Exception {
        // Initialize the database
        insertedSiteContract = siteContractRepository.saveAndFlush(siteContract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the siteContract
        SiteContract updatedSiteContract = siteContractRepository.findById(siteContract.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSiteContract are not directly saved in db
        em.detach(updatedSiteContract);
        updatedSiteContract.siteContractId(UPDATED_SITE_CONTRACT_ID).siteId(UPDATED_SITE_ID).contractId(UPDATED_CONTRACT_ID);

        restSiteContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSiteContract.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSiteContract))
            )
            .andExpect(status().isOk());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSiteContractToMatchAllProperties(updatedSiteContract);
    }

    @Test
    @Transactional
    void putNonExistingSiteContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteContract.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, siteContract.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(siteContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSiteContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteContract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteContractMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(siteContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSiteContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteContract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteContractMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(siteContract)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteContractWithPatch() throws Exception {
        // Initialize the database
        insertedSiteContract = siteContractRepository.saveAndFlush(siteContract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the siteContract using partial update
        SiteContract partialUpdatedSiteContract = new SiteContract();
        partialUpdatedSiteContract.setId(siteContract.getId());

        restSiteContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSiteContract))
            )
            .andExpect(status().isOk());

        // Validate the SiteContract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteContractUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedSiteContract, siteContract),
            getPersistedSiteContract(siteContract)
        );
    }

    @Test
    @Transactional
    void fullUpdateSiteContractWithPatch() throws Exception {
        // Initialize the database
        insertedSiteContract = siteContractRepository.saveAndFlush(siteContract);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the siteContract using partial update
        SiteContract partialUpdatedSiteContract = new SiteContract();
        partialUpdatedSiteContract.setId(siteContract.getId());

        partialUpdatedSiteContract.siteContractId(UPDATED_SITE_CONTRACT_ID).siteId(UPDATED_SITE_ID).contractId(UPDATED_CONTRACT_ID);

        restSiteContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSiteContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSiteContract))
            )
            .andExpect(status().isOk());

        // Validate the SiteContract in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteContractUpdatableFieldsEquals(partialUpdatedSiteContract, getPersistedSiteContract(partialUpdatedSiteContract));
    }

    @Test
    @Transactional
    void patchNonExistingSiteContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteContract.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, siteContract.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(siteContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSiteContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteContract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteContractMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(siteContract))
            )
            .andExpect(status().isBadRequest());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSiteContract() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        siteContract.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteContractMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(siteContract)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the SiteContract in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSiteContract() throws Exception {
        // Initialize the database
        insertedSiteContract = siteContractRepository.saveAndFlush(siteContract);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the siteContract
        restSiteContractMockMvc
            .perform(delete(ENTITY_API_URL_ID, siteContract.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return siteContractRepository.count();
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

    protected SiteContract getPersistedSiteContract(SiteContract siteContract) {
        return siteContractRepository.findById(siteContract.getId()).orElseThrow();
    }

    protected void assertPersistedSiteContractToMatchAllProperties(SiteContract expectedSiteContract) {
        assertSiteContractAllPropertiesEquals(expectedSiteContract, getPersistedSiteContract(expectedSiteContract));
    }

    protected void assertPersistedSiteContractToMatchUpdatableProperties(SiteContract expectedSiteContract) {
        assertSiteContractAllUpdatablePropertiesEquals(expectedSiteContract, getPersistedSiteContract(expectedSiteContract));
    }
}
