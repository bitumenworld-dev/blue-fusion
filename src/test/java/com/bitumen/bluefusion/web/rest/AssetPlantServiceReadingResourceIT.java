package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.AssetPlantServiceReadingAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.AssetPlantServiceReading;
import com.bitumen.bluefusion.domain.enumeration.ServiceUnit;
import com.bitumen.bluefusion.repository.AssetPlantServiceReadingRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.time.LocalDate;
import java.time.ZoneId;
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
 * Integration tests for the {@link AssetPlantServiceReadingResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetPlantServiceReadingResourceIT {

    private static final Long DEFAULT_ASSET_PLANT_SERVICE_READING_ID = 1L;
    private static final Long UPDATED_ASSET_PLANT_SERVICE_READING_ID = 2L;

    private static final Long DEFAULT_ASSET_PLANT_ID = 1L;
    private static final Long UPDATED_ASSET_PLANT_ID = 2L;

    private static final Float DEFAULT_NEXT_SERVICE_SMR_READING = 1F;
    private static final Float UPDATED_NEXT_SERVICE_SMR_READING = 2F;

    private static final Float DEFAULT_ESTIMATED_UNITS_PER_DAY = 1F;
    private static final Float UPDATED_ESTIMATED_UNITS_PER_DAY = 2F;

    private static final LocalDate DEFAULT_ESTIMATED_NEXT_SERVICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_ESTIMATED_NEXT_SERVICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_LATEST_SMR_READINGS = 1F;
    private static final Float UPDATED_LATEST_SMR_READINGS = 2F;

    private static final Float DEFAULT_SERVICE_INTERVAL = 1F;
    private static final Float UPDATED_SERVICE_INTERVAL = 2F;

    private static final LocalDate DEFAULT_LAST_SERVICE_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LAST_SERVICE_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final LocalDate DEFAULT_LATEST_SMR_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_LATEST_SMR_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final Float DEFAULT_LAST_SERVICE_SMR = 1F;
    private static final Float UPDATED_LAST_SERVICE_SMR = 2F;

    private static final ServiceUnit DEFAULT_SERVICE_UNIT = ServiceUnit.KILOMETER_BASED;
    private static final ServiceUnit UPDATED_SERVICE_UNIT = ServiceUnit.HOUR_BASED;

    private static final String ENTITY_API_URL = "/api/asset-plant-service-readings";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetPlantServiceReadingRepository assetPlantServiceReadingRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetPlantServiceReadingMockMvc;

    private AssetPlantServiceReading assetPlantServiceReading;

    private AssetPlantServiceReading insertedAssetPlantServiceReading;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetPlantServiceReading createEntity() {
        return new AssetPlantServiceReading()
            .assetPlantServiceReadingId(DEFAULT_ASSET_PLANT_SERVICE_READING_ID)
            .assetPlantId(DEFAULT_ASSET_PLANT_ID)
            .nextServiceSmrReading(DEFAULT_NEXT_SERVICE_SMR_READING)
            .estimatedUnitsPerDay(DEFAULT_ESTIMATED_UNITS_PER_DAY)
            .estimatedNextServiceDate(DEFAULT_ESTIMATED_NEXT_SERVICE_DATE)
            .latestSmrReadings(DEFAULT_LATEST_SMR_READINGS)
            .serviceInterval(DEFAULT_SERVICE_INTERVAL)
            .lastServiceDate(DEFAULT_LAST_SERVICE_DATE)
            .latestSmrDate(DEFAULT_LATEST_SMR_DATE)
            .lastServiceSmr(DEFAULT_LAST_SERVICE_SMR)
            .serviceUnit(DEFAULT_SERVICE_UNIT);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetPlantServiceReading createUpdatedEntity() {
        return new AssetPlantServiceReading()
            .assetPlantServiceReadingId(UPDATED_ASSET_PLANT_SERVICE_READING_ID)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .nextServiceSmrReading(UPDATED_NEXT_SERVICE_SMR_READING)
            .estimatedUnitsPerDay(UPDATED_ESTIMATED_UNITS_PER_DAY)
            .estimatedNextServiceDate(UPDATED_ESTIMATED_NEXT_SERVICE_DATE)
            .latestSmrReadings(UPDATED_LATEST_SMR_READINGS)
            .serviceInterval(UPDATED_SERVICE_INTERVAL)
            .lastServiceDate(UPDATED_LAST_SERVICE_DATE)
            .latestSmrDate(UPDATED_LATEST_SMR_DATE)
            .lastServiceSmr(UPDATED_LAST_SERVICE_SMR)
            .serviceUnit(UPDATED_SERVICE_UNIT);
    }

    @BeforeEach
    void initTest() {
        assetPlantServiceReading = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAssetPlantServiceReading != null) {
            assetPlantServiceReadingRepository.delete(insertedAssetPlantServiceReading);
            insertedAssetPlantServiceReading = null;
        }
    }

    @Test
    @Transactional
    void createAssetPlantServiceReading() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssetPlantServiceReading
        var returnedAssetPlantServiceReading = om.readValue(
            restAssetPlantServiceReadingMockMvc
                .perform(
                    post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlantServiceReading))
                )
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetPlantServiceReading.class
        );

        // Validate the AssetPlantServiceReading in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAssetPlantServiceReadingUpdatableFieldsEquals(
            returnedAssetPlantServiceReading,
            getPersistedAssetPlantServiceReading(returnedAssetPlantServiceReading)
        );

        insertedAssetPlantServiceReading = returnedAssetPlantServiceReading;
    }

    @Test
    @Transactional
    void createAssetPlantServiceReadingWithExistingId() throws Exception {
        // Create the AssetPlantServiceReading with an existing ID
        assetPlantServiceReading.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetPlantServiceReadingMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlantServiceReading)))
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetPlantServiceReadings() throws Exception {
        // Initialize the database
        insertedAssetPlantServiceReading = assetPlantServiceReadingRepository.saveAndFlush(assetPlantServiceReading);

        // Get all the assetPlantServiceReadingList
        restAssetPlantServiceReadingMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetPlantServiceReading.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetPlantServiceReadingId").value(hasItem(DEFAULT_ASSET_PLANT_SERVICE_READING_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPlantId").value(hasItem(DEFAULT_ASSET_PLANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].nextServiceSmrReading").value(hasItem(DEFAULT_NEXT_SERVICE_SMR_READING.doubleValue())))
            .andExpect(jsonPath("$.[*].estimatedUnitsPerDay").value(hasItem(DEFAULT_ESTIMATED_UNITS_PER_DAY.doubleValue())))
            .andExpect(jsonPath("$.[*].estimatedNextServiceDate").value(hasItem(DEFAULT_ESTIMATED_NEXT_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].latestSmrReadings").value(hasItem(DEFAULT_LATEST_SMR_READINGS.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceInterval").value(hasItem(DEFAULT_SERVICE_INTERVAL.doubleValue())))
            .andExpect(jsonPath("$.[*].lastServiceDate").value(hasItem(DEFAULT_LAST_SERVICE_DATE.toString())))
            .andExpect(jsonPath("$.[*].latestSmrDate").value(hasItem(DEFAULT_LATEST_SMR_DATE.toString())))
            .andExpect(jsonPath("$.[*].lastServiceSmr").value(hasItem(DEFAULT_LAST_SERVICE_SMR.doubleValue())))
            .andExpect(jsonPath("$.[*].serviceUnit").value(hasItem(DEFAULT_SERVICE_UNIT.toString())));
    }

    @Test
    @Transactional
    void getAssetPlantServiceReading() throws Exception {
        // Initialize the database
        insertedAssetPlantServiceReading = assetPlantServiceReadingRepository.saveAndFlush(assetPlantServiceReading);

        // Get the assetPlantServiceReading
        restAssetPlantServiceReadingMockMvc
            .perform(get(ENTITY_API_URL_ID, assetPlantServiceReading.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetPlantServiceReading.getId().intValue()))
            .andExpect(jsonPath("$.assetPlantServiceReadingId").value(DEFAULT_ASSET_PLANT_SERVICE_READING_ID.intValue()))
            .andExpect(jsonPath("$.assetPlantId").value(DEFAULT_ASSET_PLANT_ID.intValue()))
            .andExpect(jsonPath("$.nextServiceSmrReading").value(DEFAULT_NEXT_SERVICE_SMR_READING.doubleValue()))
            .andExpect(jsonPath("$.estimatedUnitsPerDay").value(DEFAULT_ESTIMATED_UNITS_PER_DAY.doubleValue()))
            .andExpect(jsonPath("$.estimatedNextServiceDate").value(DEFAULT_ESTIMATED_NEXT_SERVICE_DATE.toString()))
            .andExpect(jsonPath("$.latestSmrReadings").value(DEFAULT_LATEST_SMR_READINGS.doubleValue()))
            .andExpect(jsonPath("$.serviceInterval").value(DEFAULT_SERVICE_INTERVAL.doubleValue()))
            .andExpect(jsonPath("$.lastServiceDate").value(DEFAULT_LAST_SERVICE_DATE.toString()))
            .andExpect(jsonPath("$.latestSmrDate").value(DEFAULT_LATEST_SMR_DATE.toString()))
            .andExpect(jsonPath("$.lastServiceSmr").value(DEFAULT_LAST_SERVICE_SMR.doubleValue()))
            .andExpect(jsonPath("$.serviceUnit").value(DEFAULT_SERVICE_UNIT.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAssetPlantServiceReading() throws Exception {
        // Get the assetPlantServiceReading
        restAssetPlantServiceReadingMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetPlantServiceReading() throws Exception {
        // Initialize the database
        insertedAssetPlantServiceReading = assetPlantServiceReadingRepository.saveAndFlush(assetPlantServiceReading);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlantServiceReading
        AssetPlantServiceReading updatedAssetPlantServiceReading = assetPlantServiceReadingRepository
            .findById(assetPlantServiceReading.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedAssetPlantServiceReading are not directly saved in db
        em.detach(updatedAssetPlantServiceReading);
        updatedAssetPlantServiceReading
            .assetPlantServiceReadingId(UPDATED_ASSET_PLANT_SERVICE_READING_ID)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .nextServiceSmrReading(UPDATED_NEXT_SERVICE_SMR_READING)
            .estimatedUnitsPerDay(UPDATED_ESTIMATED_UNITS_PER_DAY)
            .estimatedNextServiceDate(UPDATED_ESTIMATED_NEXT_SERVICE_DATE)
            .latestSmrReadings(UPDATED_LATEST_SMR_READINGS)
            .serviceInterval(UPDATED_SERVICE_INTERVAL)
            .lastServiceDate(UPDATED_LAST_SERVICE_DATE)
            .latestSmrDate(UPDATED_LATEST_SMR_DATE)
            .lastServiceSmr(UPDATED_LAST_SERVICE_SMR)
            .serviceUnit(UPDATED_SERVICE_UNIT);

        restAssetPlantServiceReadingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssetPlantServiceReading.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAssetPlantServiceReading))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetPlantServiceReadingToMatchAllProperties(updatedAssetPlantServiceReading);
    }

    @Test
    @Transactional
    void putNonExistingAssetPlantServiceReading() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantServiceReading.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetPlantServiceReadingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetPlantServiceReading.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetPlantServiceReading))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetPlantServiceReading() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantServiceReading.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantServiceReadingMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetPlantServiceReading))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetPlantServiceReading() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantServiceReading.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantServiceReadingMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlantServiceReading)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetPlantServiceReadingWithPatch() throws Exception {
        // Initialize the database
        insertedAssetPlantServiceReading = assetPlantServiceReadingRepository.saveAndFlush(assetPlantServiceReading);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlantServiceReading using partial update
        AssetPlantServiceReading partialUpdatedAssetPlantServiceReading = new AssetPlantServiceReading();
        partialUpdatedAssetPlantServiceReading.setId(assetPlantServiceReading.getId());

        partialUpdatedAssetPlantServiceReading
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .nextServiceSmrReading(UPDATED_NEXT_SERVICE_SMR_READING)
            .estimatedNextServiceDate(UPDATED_ESTIMATED_NEXT_SERVICE_DATE)
            .latestSmrReadings(UPDATED_LATEST_SMR_READINGS)
            .latestSmrDate(UPDATED_LATEST_SMR_DATE)
            .lastServiceSmr(UPDATED_LAST_SERVICE_SMR);

        restAssetPlantServiceReadingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetPlantServiceReading.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetPlantServiceReading))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlantServiceReading in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetPlantServiceReadingUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssetPlantServiceReading, assetPlantServiceReading),
            getPersistedAssetPlantServiceReading(assetPlantServiceReading)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssetPlantServiceReadingWithPatch() throws Exception {
        // Initialize the database
        insertedAssetPlantServiceReading = assetPlantServiceReadingRepository.saveAndFlush(assetPlantServiceReading);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlantServiceReading using partial update
        AssetPlantServiceReading partialUpdatedAssetPlantServiceReading = new AssetPlantServiceReading();
        partialUpdatedAssetPlantServiceReading.setId(assetPlantServiceReading.getId());

        partialUpdatedAssetPlantServiceReading
            .assetPlantServiceReadingId(UPDATED_ASSET_PLANT_SERVICE_READING_ID)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .nextServiceSmrReading(UPDATED_NEXT_SERVICE_SMR_READING)
            .estimatedUnitsPerDay(UPDATED_ESTIMATED_UNITS_PER_DAY)
            .estimatedNextServiceDate(UPDATED_ESTIMATED_NEXT_SERVICE_DATE)
            .latestSmrReadings(UPDATED_LATEST_SMR_READINGS)
            .serviceInterval(UPDATED_SERVICE_INTERVAL)
            .lastServiceDate(UPDATED_LAST_SERVICE_DATE)
            .latestSmrDate(UPDATED_LATEST_SMR_DATE)
            .lastServiceSmr(UPDATED_LAST_SERVICE_SMR)
            .serviceUnit(UPDATED_SERVICE_UNIT);

        restAssetPlantServiceReadingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetPlantServiceReading.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetPlantServiceReading))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlantServiceReading in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetPlantServiceReadingUpdatableFieldsEquals(
            partialUpdatedAssetPlantServiceReading,
            getPersistedAssetPlantServiceReading(partialUpdatedAssetPlantServiceReading)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAssetPlantServiceReading() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantServiceReading.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetPlantServiceReadingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetPlantServiceReading.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetPlantServiceReading))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetPlantServiceReading() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantServiceReading.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantServiceReadingMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetPlantServiceReading))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetPlantServiceReading() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantServiceReading.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantServiceReadingMockMvc
            .perform(
                patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assetPlantServiceReading))
            )
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetPlantServiceReading in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetPlantServiceReading() throws Exception {
        // Initialize the database
        insertedAssetPlantServiceReading = assetPlantServiceReadingRepository.saveAndFlush(assetPlantServiceReading);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assetPlantServiceReading
        restAssetPlantServiceReadingMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetPlantServiceReading.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetPlantServiceReadingRepository.count();
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

    protected AssetPlantServiceReading getPersistedAssetPlantServiceReading(AssetPlantServiceReading assetPlantServiceReading) {
        return assetPlantServiceReadingRepository.findById(assetPlantServiceReading.getId()).orElseThrow();
    }

    protected void assertPersistedAssetPlantServiceReadingToMatchAllProperties(AssetPlantServiceReading expectedAssetPlantServiceReading) {
        assertAssetPlantServiceReadingAllPropertiesEquals(
            expectedAssetPlantServiceReading,
            getPersistedAssetPlantServiceReading(expectedAssetPlantServiceReading)
        );
    }

    protected void assertPersistedAssetPlantServiceReadingToMatchUpdatableProperties(
        AssetPlantServiceReading expectedAssetPlantServiceReading
    ) {
        assertAssetPlantServiceReadingAllUpdatablePropertiesEquals(
            expectedAssetPlantServiceReading,
            getPersistedAssetPlantServiceReading(expectedAssetPlantServiceReading)
        );
    }
}
