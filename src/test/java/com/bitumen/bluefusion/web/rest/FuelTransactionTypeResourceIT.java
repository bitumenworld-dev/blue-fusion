package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.FuelTransactionTypeAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.FuelTransactionType;
import com.bitumen.bluefusion.repository.FuelTransactionTypeRepository;
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
 * Integration tests for the {@link FuelTransactionTypeResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelTransactionTypeResourceIT {

    private static final Long DEFAULT_FUEL_TRANSACTION_TYPE_ID = 1L;
    private static final Long UPDATED_FUEL_TRANSACTION_TYPE_ID = 2L;

    private static final String DEFAULT_FUEL_TRANSACTION_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FUEL_TRANSACTION_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String ENTITY_API_URL = "/api/fuel-transaction-types";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuelTransactionTypeRepository fuelTransactionTypeRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelTransactionTypeMockMvc;

    private FuelTransactionType fuelTransactionType;

    private FuelTransactionType insertedFuelTransactionType;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTransactionType createEntity() {
        return new FuelTransactionType()
            .fuelTransactionTypeId(DEFAULT_FUEL_TRANSACTION_TYPE_ID)
            .fuelTransactionType(DEFAULT_FUEL_TRANSACTION_TYPE)
            .isActive(DEFAULT_IS_ACTIVE);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTransactionType createUpdatedEntity() {
        return new FuelTransactionType()
            .fuelTransactionTypeId(UPDATED_FUEL_TRANSACTION_TYPE_ID)
            .fuelTransactionType(UPDATED_FUEL_TRANSACTION_TYPE)
            .isActive(UPDATED_IS_ACTIVE);
    }

    @BeforeEach
    void initTest() {
        fuelTransactionType = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFuelTransactionType != null) {
            fuelTransactionTypeRepository.delete(insertedFuelTransactionType);
            insertedFuelTransactionType = null;
        }
    }

    @Test
    @Transactional
    void createFuelTransactionType() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuelTransactionType
        var returnedFuelTransactionType = om.readValue(
            restFuelTransactionTypeMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionType)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuelTransactionType.class
        );

        // Validate the FuelTransactionType in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuelTransactionTypeUpdatableFieldsEquals(
            returnedFuelTransactionType,
            getPersistedFuelTransactionType(returnedFuelTransactionType)
        );

        insertedFuelTransactionType = returnedFuelTransactionType;
    }

    @Test
    @Transactional
    void createFuelTransactionTypeWithExistingId() throws Exception {
        // Create the FuelTransactionType with an existing ID
        fuelTransactionType.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTransactionTypeMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionType)))
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelTransactionTypes() throws Exception {
        // Initialize the database
        insertedFuelTransactionType = fuelTransactionTypeRepository.saveAndFlush(fuelTransactionType);

        // Get all the fuelTransactionTypeList
        restFuelTransactionTypeMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelTransactionType.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuelTransactionTypeId").value(hasItem(DEFAULT_FUEL_TRANSACTION_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].fuelTransactionType").value(hasItem(DEFAULT_FUEL_TRANSACTION_TYPE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)));
    }

    @Test
    @Transactional
    void getFuelTransactionType() throws Exception {
        // Initialize the database
        insertedFuelTransactionType = fuelTransactionTypeRepository.saveAndFlush(fuelTransactionType);

        // Get the fuelTransactionType
        restFuelTransactionTypeMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelTransactionType.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelTransactionType.getId().intValue()))
            .andExpect(jsonPath("$.fuelTransactionTypeId").value(DEFAULT_FUEL_TRANSACTION_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.fuelTransactionType").value(DEFAULT_FUEL_TRANSACTION_TYPE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE));
    }

    @Test
    @Transactional
    void getNonExistingFuelTransactionType() throws Exception {
        // Get the fuelTransactionType
        restFuelTransactionTypeMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelTransactionType() throws Exception {
        // Initialize the database
        insertedFuelTransactionType = fuelTransactionTypeRepository.saveAndFlush(fuelTransactionType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionType
        FuelTransactionType updatedFuelTransactionType = fuelTransactionTypeRepository.findById(fuelTransactionType.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuelTransactionType are not directly saved in db
        em.detach(updatedFuelTransactionType);
        updatedFuelTransactionType
            .fuelTransactionTypeId(UPDATED_FUEL_TRANSACTION_TYPE_ID)
            .fuelTransactionType(UPDATED_FUEL_TRANSACTION_TYPE)
            .isActive(UPDATED_IS_ACTIVE);

        restFuelTransactionTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuelTransactionType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuelTransactionType))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuelTransactionTypeToMatchAllProperties(updatedFuelTransactionType);
    }

    @Test
    @Transactional
    void putNonExistingFuelTransactionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTransactionTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTransactionType.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTransactionType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelTransactionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionTypeMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTransactionType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelTransactionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionTypeMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelTransactionTypeWithPatch() throws Exception {
        // Initialize the database
        insertedFuelTransactionType = fuelTransactionTypeRepository.saveAndFlush(fuelTransactionType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionType using partial update
        FuelTransactionType partialUpdatedFuelTransactionType = new FuelTransactionType();
        partialUpdatedFuelTransactionType.setId(fuelTransactionType.getId());

        partialUpdatedFuelTransactionType.fuelTransactionTypeId(UPDATED_FUEL_TRANSACTION_TYPE_ID);

        restFuelTransactionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTransactionType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelTransactionType))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTransactionTypeUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuelTransactionType, fuelTransactionType),
            getPersistedFuelTransactionType(fuelTransactionType)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuelTransactionTypeWithPatch() throws Exception {
        // Initialize the database
        insertedFuelTransactionType = fuelTransactionTypeRepository.saveAndFlush(fuelTransactionType);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionType using partial update
        FuelTransactionType partialUpdatedFuelTransactionType = new FuelTransactionType();
        partialUpdatedFuelTransactionType.setId(fuelTransactionType.getId());

        partialUpdatedFuelTransactionType
            .fuelTransactionTypeId(UPDATED_FUEL_TRANSACTION_TYPE_ID)
            .fuelTransactionType(UPDATED_FUEL_TRANSACTION_TYPE)
            .isActive(UPDATED_IS_ACTIVE);

        restFuelTransactionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTransactionType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelTransactionType))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionType in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTransactionTypeUpdatableFieldsEquals(
            partialUpdatedFuelTransactionType,
            getPersistedFuelTransactionType(partialUpdatedFuelTransactionType)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFuelTransactionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionType.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTransactionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelTransactionType.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTransactionType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelTransactionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionTypeMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTransactionType))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelTransactionType() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionType.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionTypeMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fuelTransactionType)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTransactionType in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelTransactionType() throws Exception {
        // Initialize the database
        insertedFuelTransactionType = fuelTransactionTypeRepository.saveAndFlush(fuelTransactionType);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fuelTransactionType
        restFuelTransactionTypeMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelTransactionType.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fuelTransactionTypeRepository.count();
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

    protected FuelTransactionType getPersistedFuelTransactionType(FuelTransactionType fuelTransactionType) {
        return fuelTransactionTypeRepository.findById(fuelTransactionType.getId()).orElseThrow();
    }

    protected void assertPersistedFuelTransactionTypeToMatchAllProperties(FuelTransactionType expectedFuelTransactionType) {
        assertFuelTransactionTypeAllPropertiesEquals(
            expectedFuelTransactionType,
            getPersistedFuelTransactionType(expectedFuelTransactionType)
        );
    }

    protected void assertPersistedFuelTransactionTypeToMatchUpdatableProperties(FuelTransactionType expectedFuelTransactionType) {
        assertFuelTransactionTypeAllUpdatablePropertiesEquals(
            expectedFuelTransactionType,
            getPersistedFuelTransactionType(expectedFuelTransactionType)
        );
    }
}
