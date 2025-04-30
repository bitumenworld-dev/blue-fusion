package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.PlantSubcategoryAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.PlantSubcategory;
import com.bitumen.bluefusion.repository.PlantSubcategoryRepository;
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
 * Integration tests for the {@link PlantSubcategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlantSubcategoryResourceIT {

    private static final Long DEFAULT_PLANT_SUBCATEGORY_ID = 1L;
    private static final Long UPDATED_PLANT_SUBCATEGORY_ID = 2L;

    private static final String DEFAULT_PLANT_SUBCATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_SUBCATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PLANT_SUBCATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_SUBCATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PLANT_SUBCATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_SUBCATEGORY_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AUDITED = false;
    private static final Boolean UPDATED_IS_AUDITED = true;

    private static final String ENTITY_API_URL = "/api/plant-subcategories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlantSubcategoryRepository plantSubcategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantSubcategoryMockMvc;

    private PlantSubcategory plantSubcategory;

    private PlantSubcategory insertedPlantSubcategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantSubcategory createEntity() {
        return new PlantSubcategory()
            .plantSubcategoryId(DEFAULT_PLANT_SUBCATEGORY_ID)
            .plantSubcategoryCode(DEFAULT_PLANT_SUBCATEGORY_CODE)
            .plantSubcategoryName(DEFAULT_PLANT_SUBCATEGORY_NAME)
            .plantSubcategoryDescription(DEFAULT_PLANT_SUBCATEGORY_DESCRIPTION)
            .isAudited(DEFAULT_IS_AUDITED);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantSubcategory createUpdatedEntity() {
        return new PlantSubcategory()
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .plantSubcategoryCode(UPDATED_PLANT_SUBCATEGORY_CODE)
            .plantSubcategoryName(UPDATED_PLANT_SUBCATEGORY_NAME)
            .plantSubcategoryDescription(UPDATED_PLANT_SUBCATEGORY_DESCRIPTION)
            .isAudited(UPDATED_IS_AUDITED);
    }

    @BeforeEach
    void initTest() {
        plantSubcategory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPlantSubcategory != null) {
            plantSubcategoryRepository.delete(insertedPlantSubcategory);
            insertedPlantSubcategory = null;
        }
    }

    @Test
    @Transactional
    void createPlantSubcategory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlantSubcategory
        var returnedPlantSubcategory = om.readValue(
            restPlantSubcategoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plantSubcategory)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlantSubcategory.class
        );

        // Validate the PlantSubcategory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlantSubcategoryUpdatableFieldsEquals(returnedPlantSubcategory, getPersistedPlantSubcategory(returnedPlantSubcategory));

        insertedPlantSubcategory = returnedPlantSubcategory;
    }

    @Test
    @Transactional
    void createPlantSubcategoryWithExistingId() throws Exception {
        // Create the PlantSubcategory with an existing ID
        plantSubcategory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantSubcategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plantSubcategory)))
            .andExpect(status().isBadRequest());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlantSubcategories() throws Exception {
        // Initialize the database
        insertedPlantSubcategory = plantSubcategoryRepository.saveAndFlush(plantSubcategory);

        // Get all the plantSubcategoryList
        restPlantSubcategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantSubcategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].plantSubcategoryId").value(hasItem(DEFAULT_PLANT_SUBCATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].plantSubcategoryCode").value(hasItem(DEFAULT_PLANT_SUBCATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].plantSubcategoryName").value(hasItem(DEFAULT_PLANT_SUBCATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].plantSubcategoryDescription").value(hasItem(DEFAULT_PLANT_SUBCATEGORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isAudited").value(hasItem(DEFAULT_IS_AUDITED)));
    }

    @Test
    @Transactional
    void getPlantSubcategory() throws Exception {
        // Initialize the database
        insertedPlantSubcategory = plantSubcategoryRepository.saveAndFlush(plantSubcategory);

        // Get the plantSubcategory
        restPlantSubcategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, plantSubcategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plantSubcategory.getId().intValue()))
            .andExpect(jsonPath("$.plantSubcategoryId").value(DEFAULT_PLANT_SUBCATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.plantSubcategoryCode").value(DEFAULT_PLANT_SUBCATEGORY_CODE))
            .andExpect(jsonPath("$.plantSubcategoryName").value(DEFAULT_PLANT_SUBCATEGORY_NAME))
            .andExpect(jsonPath("$.plantSubcategoryDescription").value(DEFAULT_PLANT_SUBCATEGORY_DESCRIPTION))
            .andExpect(jsonPath("$.isAudited").value(DEFAULT_IS_AUDITED));
    }

    @Test
    @Transactional
    void getNonExistingPlantSubcategory() throws Exception {
        // Get the plantSubcategory
        restPlantSubcategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlantSubcategory() throws Exception {
        // Initialize the database
        insertedPlantSubcategory = plantSubcategoryRepository.saveAndFlush(plantSubcategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plantSubcategory
        PlantSubcategory updatedPlantSubcategory = plantSubcategoryRepository.findById(plantSubcategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlantSubcategory are not directly saved in db
        em.detach(updatedPlantSubcategory);
        updatedPlantSubcategory
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .plantSubcategoryCode(UPDATED_PLANT_SUBCATEGORY_CODE)
            .plantSubcategoryName(UPDATED_PLANT_SUBCATEGORY_NAME)
            .plantSubcategoryDescription(UPDATED_PLANT_SUBCATEGORY_DESCRIPTION)
            .isAudited(UPDATED_IS_AUDITED);

        restPlantSubcategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlantSubcategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlantSubcategory))
            )
            .andExpect(status().isOk());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlantSubcategoryToMatchAllProperties(updatedPlantSubcategory);
    }

    @Test
    @Transactional
    void putNonExistingPlantSubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantSubcategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantSubcategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plantSubcategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plantSubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlantSubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantSubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantSubcategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plantSubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlantSubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantSubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantSubcategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plantSubcategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlantSubcategoryWithPatch() throws Exception {
        // Initialize the database
        insertedPlantSubcategory = plantSubcategoryRepository.saveAndFlush(plantSubcategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plantSubcategory using partial update
        PlantSubcategory partialUpdatedPlantSubcategory = new PlantSubcategory();
        partialUpdatedPlantSubcategory.setId(plantSubcategory.getId());

        partialUpdatedPlantSubcategory
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .plantSubcategoryName(UPDATED_PLANT_SUBCATEGORY_NAME)
            .plantSubcategoryDescription(UPDATED_PLANT_SUBCATEGORY_DESCRIPTION);

        restPlantSubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantSubcategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlantSubcategory))
            )
            .andExpect(status().isOk());

        // Validate the PlantSubcategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlantSubcategoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlantSubcategory, plantSubcategory),
            getPersistedPlantSubcategory(plantSubcategory)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlantSubcategoryWithPatch() throws Exception {
        // Initialize the database
        insertedPlantSubcategory = plantSubcategoryRepository.saveAndFlush(plantSubcategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plantSubcategory using partial update
        PlantSubcategory partialUpdatedPlantSubcategory = new PlantSubcategory();
        partialUpdatedPlantSubcategory.setId(plantSubcategory.getId());

        partialUpdatedPlantSubcategory
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .plantSubcategoryCode(UPDATED_PLANT_SUBCATEGORY_CODE)
            .plantSubcategoryName(UPDATED_PLANT_SUBCATEGORY_NAME)
            .plantSubcategoryDescription(UPDATED_PLANT_SUBCATEGORY_DESCRIPTION)
            .isAudited(UPDATED_IS_AUDITED);

        restPlantSubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantSubcategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlantSubcategory))
            )
            .andExpect(status().isOk());

        // Validate the PlantSubcategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlantSubcategoryUpdatableFieldsEquals(
            partialUpdatedPlantSubcategory,
            getPersistedPlantSubcategory(partialUpdatedPlantSubcategory)
        );
    }

    @Test
    @Transactional
    void patchNonExistingPlantSubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantSubcategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantSubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plantSubcategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plantSubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlantSubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantSubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantSubcategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plantSubcategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlantSubcategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantSubcategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantSubcategoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(plantSubcategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantSubcategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlantSubcategory() throws Exception {
        // Initialize the database
        insertedPlantSubcategory = plantSubcategoryRepository.saveAndFlush(plantSubcategory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the plantSubcategory
        restPlantSubcategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, plantSubcategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return plantSubcategoryRepository.count();
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

    protected PlantSubcategory getPersistedPlantSubcategory(PlantSubcategory plantSubcategory) {
        return plantSubcategoryRepository.findById(plantSubcategory.getId()).orElseThrow();
    }

    protected void assertPersistedPlantSubcategoryToMatchAllProperties(PlantSubcategory expectedPlantSubcategory) {
        assertPlantSubcategoryAllPropertiesEquals(expectedPlantSubcategory, getPersistedPlantSubcategory(expectedPlantSubcategory));
    }

    protected void assertPersistedPlantSubcategoryToMatchUpdatableProperties(PlantSubcategory expectedPlantSubcategory) {
        assertPlantSubcategoryAllUpdatablePropertiesEquals(
            expectedPlantSubcategory,
            getPersistedPlantSubcategory(expectedPlantSubcategory)
        );
    }
}
