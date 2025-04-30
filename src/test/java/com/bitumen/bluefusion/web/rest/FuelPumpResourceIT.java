package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.FuelPumpAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.FuelPump;
import com.bitumen.bluefusion.repository.FuelPumpRepository;
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
 * Integration tests for the {@link FuelPumpResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelPumpResourceIT {

    private static final Long DEFAULT_FUEL_PUMP_ID = 1L;
    private static final Long UPDATED_FUEL_PUMP_ID = 2L;

    private static final String DEFAULT_FUEL_PUMP_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FUEL_PUMP_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_CURRENT_STORAGE_UNIT_ID = 1L;
    private static final Long UPDATED_CURRENT_STORAGE_UNIT_ID = 2L;

    private static final String ENTITY_API_URL = "/api/fuel-pumps";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuelPumpRepository fuelPumpRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelPumpMockMvc;

    private FuelPump fuelPump;

    private FuelPump insertedFuelPump;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelPump createEntity() {
        return new FuelPump()
            .fuelPumpId(DEFAULT_FUEL_PUMP_ID)
            .fuelPumpNumber(DEFAULT_FUEL_PUMP_NUMBER)
            .currentStorageUnitId(DEFAULT_CURRENT_STORAGE_UNIT_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelPump createUpdatedEntity() {
        return new FuelPump()
            .fuelPumpId(UPDATED_FUEL_PUMP_ID)
            .fuelPumpNumber(UPDATED_FUEL_PUMP_NUMBER)
            .currentStorageUnitId(UPDATED_CURRENT_STORAGE_UNIT_ID);
    }

    @BeforeEach
    void initTest() {
        fuelPump = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFuelPump != null) {
            fuelPumpRepository.delete(insertedFuelPump);
            insertedFuelPump = null;
        }
    }

    @Test
    @Transactional
    void createFuelPump() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuelPump
        var returnedFuelPump = om.readValue(
            restFuelPumpMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelPump)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuelPump.class
        );

        // Validate the FuelPump in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuelPumpUpdatableFieldsEquals(returnedFuelPump, getPersistedFuelPump(returnedFuelPump));

        insertedFuelPump = returnedFuelPump;
    }

    @Test
    @Transactional
    void createFuelPumpWithExistingId() throws Exception {
        // Create the FuelPump with an existing ID
        fuelPump.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelPumpMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelPump)))
            .andExpect(status().isBadRequest());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelPumps() throws Exception {
        // Initialize the database
        insertedFuelPump = fuelPumpRepository.saveAndFlush(fuelPump);

        // Get all the fuelPumpList
        restFuelPumpMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelPump.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuelPumpId").value(hasItem(DEFAULT_FUEL_PUMP_ID.intValue())))
            .andExpect(jsonPath("$.[*].fuelPumpNumber").value(hasItem(DEFAULT_FUEL_PUMP_NUMBER)))
            .andExpect(jsonPath("$.[*].currentStorageUnitId").value(hasItem(DEFAULT_CURRENT_STORAGE_UNIT_ID.intValue())));
    }

    @Test
    @Transactional
    void getFuelPump() throws Exception {
        // Initialize the database
        insertedFuelPump = fuelPumpRepository.saveAndFlush(fuelPump);

        // Get the fuelPump
        restFuelPumpMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelPump.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelPump.getId().intValue()))
            .andExpect(jsonPath("$.fuelPumpId").value(DEFAULT_FUEL_PUMP_ID.intValue()))
            .andExpect(jsonPath("$.fuelPumpNumber").value(DEFAULT_FUEL_PUMP_NUMBER))
            .andExpect(jsonPath("$.currentStorageUnitId").value(DEFAULT_CURRENT_STORAGE_UNIT_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFuelPump() throws Exception {
        // Get the fuelPump
        restFuelPumpMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelPump() throws Exception {
        // Initialize the database
        insertedFuelPump = fuelPumpRepository.saveAndFlush(fuelPump);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelPump
        FuelPump updatedFuelPump = fuelPumpRepository.findById(fuelPump.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuelPump are not directly saved in db
        em.detach(updatedFuelPump);
        updatedFuelPump
            .fuelPumpId(UPDATED_FUEL_PUMP_ID)
            .fuelPumpNumber(UPDATED_FUEL_PUMP_NUMBER)
            .currentStorageUnitId(UPDATED_CURRENT_STORAGE_UNIT_ID);

        restFuelPumpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuelPump.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuelPump))
            )
            .andExpect(status().isOk());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuelPumpToMatchAllProperties(updatedFuelPump);
    }

    @Test
    @Transactional
    void putNonExistingFuelPump() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelPump.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelPumpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelPump.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelPump))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelPump() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelPump.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelPumpMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelPump))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelPump() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelPump.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelPumpMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelPump)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelPumpWithPatch() throws Exception {
        // Initialize the database
        insertedFuelPump = fuelPumpRepository.saveAndFlush(fuelPump);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelPump using partial update
        FuelPump partialUpdatedFuelPump = new FuelPump();
        partialUpdatedFuelPump.setId(fuelPump.getId());

        partialUpdatedFuelPump.fuelPumpId(UPDATED_FUEL_PUMP_ID).fuelPumpNumber(UPDATED_FUEL_PUMP_NUMBER);

        restFuelPumpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelPump.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelPump))
            )
            .andExpect(status().isOk());

        // Validate the FuelPump in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelPumpUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedFuelPump, fuelPump), getPersistedFuelPump(fuelPump));
    }

    @Test
    @Transactional
    void fullUpdateFuelPumpWithPatch() throws Exception {
        // Initialize the database
        insertedFuelPump = fuelPumpRepository.saveAndFlush(fuelPump);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelPump using partial update
        FuelPump partialUpdatedFuelPump = new FuelPump();
        partialUpdatedFuelPump.setId(fuelPump.getId());

        partialUpdatedFuelPump
            .fuelPumpId(UPDATED_FUEL_PUMP_ID)
            .fuelPumpNumber(UPDATED_FUEL_PUMP_NUMBER)
            .currentStorageUnitId(UPDATED_CURRENT_STORAGE_UNIT_ID);

        restFuelPumpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelPump.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelPump))
            )
            .andExpect(status().isOk());

        // Validate the FuelPump in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelPumpUpdatableFieldsEquals(partialUpdatedFuelPump, getPersistedFuelPump(partialUpdatedFuelPump));
    }

    @Test
    @Transactional
    void patchNonExistingFuelPump() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelPump.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelPumpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelPump.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelPump))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelPump() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelPump.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelPumpMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelPump))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelPump() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelPump.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelPumpMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fuelPump)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelPump in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelPump() throws Exception {
        // Initialize the database
        insertedFuelPump = fuelPumpRepository.saveAndFlush(fuelPump);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fuelPump
        restFuelPumpMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelPump.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fuelPumpRepository.count();
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

    protected FuelPump getPersistedFuelPump(FuelPump fuelPump) {
        return fuelPumpRepository.findById(fuelPump.getId()).orElseThrow();
    }

    protected void assertPersistedFuelPumpToMatchAllProperties(FuelPump expectedFuelPump) {
        assertFuelPumpAllPropertiesEquals(expectedFuelPump, getPersistedFuelPump(expectedFuelPump));
    }

    protected void assertPersistedFuelPumpToMatchUpdatableProperties(FuelPump expectedFuelPump) {
        assertFuelPumpAllUpdatablePropertiesEquals(expectedFuelPump, getPersistedFuelPump(expectedFuelPump));
    }
}
