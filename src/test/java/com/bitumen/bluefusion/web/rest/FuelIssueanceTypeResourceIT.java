package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.FuelIssueanceTypeAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.FuelIssueanceType;
import com.bitumen.bluefusion.repository.FuelIssueanceTypeRepository;
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
 * Integration tests for the {@link FuelIssueanceTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelIssueanceTypeResourceIT {

    private static final Long DEFAULT_FUEL_ISSUE_TYPE_ID = 1L;
    private static final Long UPDATED_FUEL_ISSUE_TYPE_ID = 2L;

    private static final String DEFAULT_FUEL_ISSUE_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FUEL_ISSUE_TYPE = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/fuel-issueance-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuelIssueanceTypeRepository fuelIssueanceTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelIssueanceTypeMockMvc;

    private FuelIssueanceType fuelIssueanceType;

    private FuelIssueanceType insertedFuelIssueanceType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelIssueanceType createEntity() {
        return new FuelIssueanceType().fuelIssueTypeId(DEFAULT_FUEL_ISSUE_TYPE_ID).fuelIssueType(DEFAULT_FUEL_ISSUE_TYPE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelIssueanceType createUpdatedEntity() {
        return new FuelIssueanceType().fuelIssueTypeId(UPDATED_FUEL_ISSUE_TYPE_ID).fuelIssueType(UPDATED_FUEL_ISSUE_TYPE);
    }

    @BeforeEach
    void initTest() {
        fuelIssueanceType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFuelIssueanceType != null) {
            fuelIssueanceTypeRepository.delete(insertedFuelIssueanceType);
            insertedFuelIssueanceType = null;
        }
    }

    @Test
    @Transactional
    void createFuelIssueanceType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuelIssueanceType
        var returnedFuelIssueanceType = om.readValue(
            restFuelIssueanceTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelIssueanceType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuelIssueanceType.class
        );

        // Validate the FuelIssueanceType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuelIssueanceTypeUpdatableFieldsEquals(returnedFuelIssueanceType, getPersistedFuelIssueanceType(returnedFuelIssueanceType));

        insertedFuelIssueanceType = returnedFuelIssueanceType;
    }

    @Test
    @Transactional
    void createFuelIssueanceTypeWithExistingId() throws Exception {
        // Create the FuelIssueanceType with an existing ID
        fuelIssueanceType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelIssueanceTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelIssueanceType)))
            .andExpect(status().isBadRequest());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelIssueanceTypes() throws Exception {
        // Initialize the database
        insertedFuelIssueanceType = fuelIssueanceTypeRepository.saveAndFlush(fuelIssueanceType);

        // Get all the fuelIssueanceTypeList
        restFuelIssueanceTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelIssueanceType.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuelIssueTypeId").value(hasItem(DEFAULT_FUEL_ISSUE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].fuelIssueType").value(hasItem(DEFAULT_FUEL_ISSUE_TYPE)));
    }

    @Test
    @Transactional
    void getFuelIssueanceType() throws Exception {
        // Initialize the database
        insertedFuelIssueanceType = fuelIssueanceTypeRepository.saveAndFlush(fuelIssueanceType);

        // Get the fuelIssueanceType
        restFuelIssueanceTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelIssueanceType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelIssueanceType.getId().intValue()))
            .andExpect(jsonPath("$.fuelIssueTypeId").value(DEFAULT_FUEL_ISSUE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.fuelIssueType").value(DEFAULT_FUEL_ISSUE_TYPE));
    }

    @Test
    @Transactional
    void getNonExistingFuelIssueanceType() throws Exception {
        // Get the fuelIssueanceType
        restFuelIssueanceTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelIssueanceType() throws Exception {
        // Initialize the database
        insertedFuelIssueanceType = fuelIssueanceTypeRepository.saveAndFlush(fuelIssueanceType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelIssueanceType
        FuelIssueanceType updatedFuelIssueanceType = fuelIssueanceTypeRepository.findById(fuelIssueanceType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuelIssueanceType are not directly saved in db
        em.detach(updatedFuelIssueanceType);
        updatedFuelIssueanceType.fuelIssueTypeId(UPDATED_FUEL_ISSUE_TYPE_ID).fuelIssueType(UPDATED_FUEL_ISSUE_TYPE);

        restFuelIssueanceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuelIssueanceType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuelIssueanceType))
            )
            .andExpect(status().isOk());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuelIssueanceTypeToMatchAllProperties(updatedFuelIssueanceType);
    }

    @Test
    @Transactional
    void putNonExistingFuelIssueanceType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelIssueanceType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelIssueanceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelIssueanceType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelIssueanceType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelIssueanceType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelIssueanceType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelIssueanceTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelIssueanceType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelIssueanceType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelIssueanceType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelIssueanceTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelIssueanceType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelIssueanceTypeWithPatch() throws Exception {
        // Initialize the database
        insertedFuelIssueanceType = fuelIssueanceTypeRepository.saveAndFlush(fuelIssueanceType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelIssueanceType using partial update
        FuelIssueanceType partialUpdatedFuelIssueanceType = new FuelIssueanceType();
        partialUpdatedFuelIssueanceType.setId(fuelIssueanceType.getId());

        partialUpdatedFuelIssueanceType.fuelIssueTypeId(UPDATED_FUEL_ISSUE_TYPE_ID).fuelIssueType(UPDATED_FUEL_ISSUE_TYPE);

        restFuelIssueanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelIssueanceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelIssueanceType))
            )
            .andExpect(status().isOk());

        // Validate the FuelIssueanceType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelIssueanceTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuelIssueanceType, fuelIssueanceType),
            getPersistedFuelIssueanceType(fuelIssueanceType)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuelIssueanceTypeWithPatch() throws Exception {
        // Initialize the database
        insertedFuelIssueanceType = fuelIssueanceTypeRepository.saveAndFlush(fuelIssueanceType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelIssueanceType using partial update
        FuelIssueanceType partialUpdatedFuelIssueanceType = new FuelIssueanceType();
        partialUpdatedFuelIssueanceType.setId(fuelIssueanceType.getId());

        partialUpdatedFuelIssueanceType.fuelIssueTypeId(UPDATED_FUEL_ISSUE_TYPE_ID).fuelIssueType(UPDATED_FUEL_ISSUE_TYPE);

        restFuelIssueanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelIssueanceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelIssueanceType))
            )
            .andExpect(status().isOk());

        // Validate the FuelIssueanceType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelIssueanceTypeUpdatableFieldsEquals(
            partialUpdatedFuelIssueanceType,
            getPersistedFuelIssueanceType(partialUpdatedFuelIssueanceType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFuelIssueanceType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelIssueanceType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelIssueanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelIssueanceType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelIssueanceType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelIssueanceType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelIssueanceType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelIssueanceTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelIssueanceType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelIssueanceType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelIssueanceType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelIssueanceTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fuelIssueanceType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelIssueanceType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelIssueanceType() throws Exception {
        // Initialize the database
        insertedFuelIssueanceType = fuelIssueanceTypeRepository.saveAndFlush(fuelIssueanceType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fuelIssueanceType
        restFuelIssueanceTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelIssueanceType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fuelIssueanceTypeRepository.count();
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

    protected FuelIssueanceType getPersistedFuelIssueanceType(FuelIssueanceType fuelIssueanceType) {
        return fuelIssueanceTypeRepository.findById(fuelIssueanceType.getId()).orElseThrow();
    }

    protected void assertPersistedFuelIssueanceTypeToMatchAllProperties(FuelIssueanceType expectedFuelIssueanceType) {
        assertFuelIssueanceTypeAllPropertiesEquals(expectedFuelIssueanceType, getPersistedFuelIssueanceType(expectedFuelIssueanceType));
    }

    protected void assertPersistedFuelIssueanceTypeToMatchUpdatableProperties(FuelIssueanceType expectedFuelIssueanceType) {
        assertFuelIssueanceTypeAllUpdatablePropertiesEquals(
            expectedFuelIssueanceType,
            getPersistedFuelIssueanceType(expectedFuelIssueanceType)
        );
    }
}
