package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.FuelTransactionLineAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.FuelTransactionLine;
import com.bitumen.bluefusion.repository.FuelTransactionLineRepository;
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
 * Integration tests for the {@link FuelTransactionLineResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelTransactionLineResourceIT {

    private static final Long DEFAULT_FUEL_TRANSACTION_LINE_ID = 1L;
    private static final Long UPDATED_FUEL_TRANSACTION_LINE_ID = 2L;

    private static final Long DEFAULT_FUEL_TRANSACTION_HEADER_ID = 1L;
    private static final Long UPDATED_FUEL_TRANSACTION_HEADER_ID = 2L;

    private static final Long DEFAULT_ASSET_PLANT_ID = 1L;
    private static final Long UPDATED_ASSET_PLANT_ID = 2L;

    private static final Long DEFAULT_CONTRACT_DIVISION_ID = 1L;
    private static final Long UPDATED_CONTRACT_DIVISION_ID = 2L;

    private static final Long DEFAULT_ISSUANCE_TYPE_ID = 1L;
    private static final Long UPDATED_ISSUANCE_TYPE_ID = 2L;

    private static final Long DEFAULT_PUMP_ID = 1L;
    private static final Long UPDATED_PUMP_ID = 2L;

    private static final Long DEFAULT_STORAGE_UNIT_ID = 1L;
    private static final Long UPDATED_STORAGE_UNIT_ID = 2L;

    private static final Float DEFAULT_LITRES = 1F;
    private static final Float UPDATED_LITRES = 2F;

    private static final Float DEFAULT_METER_READING_START = 1F;
    private static final Float UPDATED_METER_READING_START = 2F;

    private static final Float DEFAULT_METER_READING_END = 1F;
    private static final Float UPDATED_METER_READING_END = 2F;

    private static final Integer DEFAULT_MULTIPLIER = 1;
    private static final Integer UPDATED_MULTIPLIER = 2;

    private static final String ENTITY_API_URL = "/api/fuel-transaction-lines";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuelTransactionLineRepository fuelTransactionLineRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelTransactionLineMockMvc;

    private FuelTransactionLine fuelTransactionLine;

    private FuelTransactionLine insertedFuelTransactionLine;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTransactionLine createEntity() {
        return new FuelTransactionLine()
            .fuelTransactionLineId(DEFAULT_FUEL_TRANSACTION_LINE_ID)
            .fuelTransactionHeaderId(DEFAULT_FUEL_TRANSACTION_HEADER_ID)
            .assetPlantId(DEFAULT_ASSET_PLANT_ID)
            .contractDivisionId(DEFAULT_CONTRACT_DIVISION_ID)
            .issuanceTypeId(DEFAULT_ISSUANCE_TYPE_ID)
            .pumpId(DEFAULT_PUMP_ID)
            .storageUnitId(DEFAULT_STORAGE_UNIT_ID)
            .litres(DEFAULT_LITRES)
            .meterReadingStart(DEFAULT_METER_READING_START)
            .meterReadingEnd(DEFAULT_METER_READING_END)
            .multiplier(DEFAULT_MULTIPLIER);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTransactionLine createUpdatedEntity() {
        return new FuelTransactionLine()
            .fuelTransactionLineId(UPDATED_FUEL_TRANSACTION_LINE_ID)
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .issuanceTypeId(UPDATED_ISSUANCE_TYPE_ID)
            .pumpId(UPDATED_PUMP_ID)
            .storageUnitId(UPDATED_STORAGE_UNIT_ID)
            .litres(UPDATED_LITRES)
            .meterReadingStart(UPDATED_METER_READING_START)
            .meterReadingEnd(UPDATED_METER_READING_END)
            .multiplier(UPDATED_MULTIPLIER);
    }

    @BeforeEach
    void initTest() {
        fuelTransactionLine = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFuelTransactionLine != null) {
            fuelTransactionLineRepository.delete(insertedFuelTransactionLine);
            insertedFuelTransactionLine = null;
        }
    }

    @Test
    @Transactional
    void createFuelTransactionLine() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuelTransactionLine
        var returnedFuelTransactionLine = om.readValue(
            restFuelTransactionLineMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionLine)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuelTransactionLine.class
        );

        // Validate the FuelTransactionLine in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuelTransactionLineUpdatableFieldsEquals(
            returnedFuelTransactionLine,
            getPersistedFuelTransactionLine(returnedFuelTransactionLine)
        );

        insertedFuelTransactionLine = returnedFuelTransactionLine;
    }

    @Test
    @Transactional
    void createFuelTransactionLineWithExistingId() throws Exception {
        // Create the FuelTransactionLine with an existing ID
        fuelTransactionLine.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTransactionLineMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionLine)))
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelTransactionLines() throws Exception {
        // Initialize the database
        insertedFuelTransactionLine = fuelTransactionLineRepository.saveAndFlush(fuelTransactionLine);

        // Get all the fuelTransactionLineList
        restFuelTransactionLineMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelTransactionLine.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuelTransactionLineId").value(hasItem(DEFAULT_FUEL_TRANSACTION_LINE_ID.intValue())))
            .andExpect(jsonPath("$.[*].fuelTransactionHeaderId").value(hasItem(DEFAULT_FUEL_TRANSACTION_HEADER_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPlantId").value(hasItem(DEFAULT_ASSET_PLANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].contractDivisionId").value(hasItem(DEFAULT_CONTRACT_DIVISION_ID.intValue())))
            .andExpect(jsonPath("$.[*].issuanceTypeId").value(hasItem(DEFAULT_ISSUANCE_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].pumpId").value(hasItem(DEFAULT_PUMP_ID.intValue())))
            .andExpect(jsonPath("$.[*].storageUnitId").value(hasItem(DEFAULT_STORAGE_UNIT_ID.intValue())))
            .andExpect(jsonPath("$.[*].litres").value(hasItem(DEFAULT_LITRES.doubleValue())))
            .andExpect(jsonPath("$.[*].meterReadingStart").value(hasItem(DEFAULT_METER_READING_START.doubleValue())))
            .andExpect(jsonPath("$.[*].meterReadingEnd").value(hasItem(DEFAULT_METER_READING_END.doubleValue())))
            .andExpect(jsonPath("$.[*].multiplier").value(hasItem(DEFAULT_MULTIPLIER)));
    }

    @Test
    @Transactional
    void getFuelTransactionLine() throws Exception {
        // Initialize the database
        insertedFuelTransactionLine = fuelTransactionLineRepository.saveAndFlush(fuelTransactionLine);

        // Get the fuelTransactionLine
        restFuelTransactionLineMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelTransactionLine.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelTransactionLine.getId().intValue()))
            .andExpect(jsonPath("$.fuelTransactionLineId").value(DEFAULT_FUEL_TRANSACTION_LINE_ID.intValue()))
            .andExpect(jsonPath("$.fuelTransactionHeaderId").value(DEFAULT_FUEL_TRANSACTION_HEADER_ID.intValue()))
            .andExpect(jsonPath("$.assetPlantId").value(DEFAULT_ASSET_PLANT_ID.intValue()))
            .andExpect(jsonPath("$.contractDivisionId").value(DEFAULT_CONTRACT_DIVISION_ID.intValue()))
            .andExpect(jsonPath("$.issuanceTypeId").value(DEFAULT_ISSUANCE_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.pumpId").value(DEFAULT_PUMP_ID.intValue()))
            .andExpect(jsonPath("$.storageUnitId").value(DEFAULT_STORAGE_UNIT_ID.intValue()))
            .andExpect(jsonPath("$.litres").value(DEFAULT_LITRES.doubleValue()))
            .andExpect(jsonPath("$.meterReadingStart").value(DEFAULT_METER_READING_START.doubleValue()))
            .andExpect(jsonPath("$.meterReadingEnd").value(DEFAULT_METER_READING_END.doubleValue()))
            .andExpect(jsonPath("$.multiplier").value(DEFAULT_MULTIPLIER));
    }

    @Test
    @Transactional
    void getNonExistingFuelTransactionLine() throws Exception {
        // Get the fuelTransactionLine
        restFuelTransactionLineMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelTransactionLine() throws Exception {
        // Initialize the database
        insertedFuelTransactionLine = fuelTransactionLineRepository.saveAndFlush(fuelTransactionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionLine
        FuelTransactionLine updatedFuelTransactionLine = fuelTransactionLineRepository.findById(fuelTransactionLine.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedFuelTransactionLine are not directly saved in db
        em.detach(updatedFuelTransactionLine);
        updatedFuelTransactionLine
            .fuelTransactionLineId(UPDATED_FUEL_TRANSACTION_LINE_ID)
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .issuanceTypeId(UPDATED_ISSUANCE_TYPE_ID)
            .pumpId(UPDATED_PUMP_ID)
            .storageUnitId(UPDATED_STORAGE_UNIT_ID)
            .litres(UPDATED_LITRES)
            .meterReadingStart(UPDATED_METER_READING_START)
            .meterReadingEnd(UPDATED_METER_READING_END)
            .multiplier(UPDATED_MULTIPLIER);

        restFuelTransactionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuelTransactionLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuelTransactionLine))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuelTransactionLineToMatchAllProperties(updatedFuelTransactionLine);
    }

    @Test
    @Transactional
    void putNonExistingFuelTransactionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionLine.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTransactionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTransactionLine.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTransactionLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelTransactionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionLineMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTransactionLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelTransactionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionLineMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionLine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelTransactionLineWithPatch() throws Exception {
        // Initialize the database
        insertedFuelTransactionLine = fuelTransactionLineRepository.saveAndFlush(fuelTransactionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionLine using partial update
        FuelTransactionLine partialUpdatedFuelTransactionLine = new FuelTransactionLine();
        partialUpdatedFuelTransactionLine.setId(fuelTransactionLine.getId());

        partialUpdatedFuelTransactionLine
            .fuelTransactionLineId(UPDATED_FUEL_TRANSACTION_LINE_ID)
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .pumpId(UPDATED_PUMP_ID)
            .litres(UPDATED_LITRES)
            .meterReadingEnd(UPDATED_METER_READING_END)
            .multiplier(UPDATED_MULTIPLIER);

        restFuelTransactionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTransactionLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelTransactionLine))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTransactionLineUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuelTransactionLine, fuelTransactionLine),
            getPersistedFuelTransactionLine(fuelTransactionLine)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuelTransactionLineWithPatch() throws Exception {
        // Initialize the database
        insertedFuelTransactionLine = fuelTransactionLineRepository.saveAndFlush(fuelTransactionLine);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionLine using partial update
        FuelTransactionLine partialUpdatedFuelTransactionLine = new FuelTransactionLine();
        partialUpdatedFuelTransactionLine.setId(fuelTransactionLine.getId());

        partialUpdatedFuelTransactionLine
            .fuelTransactionLineId(UPDATED_FUEL_TRANSACTION_LINE_ID)
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .contractDivisionId(UPDATED_CONTRACT_DIVISION_ID)
            .issuanceTypeId(UPDATED_ISSUANCE_TYPE_ID)
            .pumpId(UPDATED_PUMP_ID)
            .storageUnitId(UPDATED_STORAGE_UNIT_ID)
            .litres(UPDATED_LITRES)
            .meterReadingStart(UPDATED_METER_READING_START)
            .meterReadingEnd(UPDATED_METER_READING_END)
            .multiplier(UPDATED_MULTIPLIER);

        restFuelTransactionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTransactionLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelTransactionLine))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionLine in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTransactionLineUpdatableFieldsEquals(
            partialUpdatedFuelTransactionLine,
            getPersistedFuelTransactionLine(partialUpdatedFuelTransactionLine)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFuelTransactionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionLine.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTransactionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelTransactionLine.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTransactionLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelTransactionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionLineMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTransactionLine))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelTransactionLine() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionLine.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionLineMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fuelTransactionLine)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTransactionLine in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelTransactionLine() throws Exception {
        // Initialize the database
        insertedFuelTransactionLine = fuelTransactionLineRepository.saveAndFlush(fuelTransactionLine);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fuelTransactionLine
        restFuelTransactionLineMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelTransactionLine.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fuelTransactionLineRepository.count();
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

    protected FuelTransactionLine getPersistedFuelTransactionLine(FuelTransactionLine fuelTransactionLine) {
        return fuelTransactionLineRepository.findById(fuelTransactionLine.getId()).orElseThrow();
    }

    protected void assertPersistedFuelTransactionLineToMatchAllProperties(FuelTransactionLine expectedFuelTransactionLine) {
        assertFuelTransactionLineAllPropertiesEquals(
            expectedFuelTransactionLine,
            getPersistedFuelTransactionLine(expectedFuelTransactionLine)
        );
    }

    protected void assertPersistedFuelTransactionLineToMatchUpdatableProperties(FuelTransactionLine expectedFuelTransactionLine) {
        assertFuelTransactionLineAllUpdatablePropertiesEquals(
            expectedFuelTransactionLine,
            getPersistedFuelTransactionLine(expectedFuelTransactionLine)
        );
    }
}
