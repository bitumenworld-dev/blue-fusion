package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.PlantCategoryAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.PlantCategory;
import com.bitumen.bluefusion.repository.PlantCategoryRepository;
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
 * Integration tests for the {@link PlantCategoryResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class PlantCategoryResourceIT {

    private static final Long DEFAULT_PLANT_CATEGORY_ID = 1L;
    private static final Long UPDATED_PLANT_CATEGORY_ID = 2L;

    private static final String DEFAULT_PLANT_CATEGORY_CODE = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_CATEGORY_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_PLANT_CATEGORY_NAME = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_CATEGORY_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_PLANT_CATEGORY_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_CATEGORY_DESCRIPTION = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_AUDITED = false;
    private static final Boolean UPDATED_IS_AUDITED = true;

    private static final String ENTITY_API_URL = "/api/plant-categories";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private PlantCategoryRepository plantCategoryRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restPlantCategoryMockMvc;

    private PlantCategory plantCategory;

    private PlantCategory insertedPlantCategory;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantCategory createEntity() {
        return new PlantCategory()
            .plantCategoryId(DEFAULT_PLANT_CATEGORY_ID)
            .plantCategoryCode(DEFAULT_PLANT_CATEGORY_CODE)
            .plantCategoryName(DEFAULT_PLANT_CATEGORY_NAME)
            .plantCategoryDescription(DEFAULT_PLANT_CATEGORY_DESCRIPTION)
            .isAudited(DEFAULT_IS_AUDITED);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static PlantCategory createUpdatedEntity() {
        return new PlantCategory()
            .plantCategoryId(UPDATED_PLANT_CATEGORY_ID)
            .plantCategoryCode(UPDATED_PLANT_CATEGORY_CODE)
            .plantCategoryName(UPDATED_PLANT_CATEGORY_NAME)
            .plantCategoryDescription(UPDATED_PLANT_CATEGORY_DESCRIPTION)
            .isAudited(UPDATED_IS_AUDITED);
    }

    @BeforeEach
    void initTest() {
        plantCategory = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedPlantCategory != null) {
            plantCategoryRepository.delete(insertedPlantCategory);
            insertedPlantCategory = null;
        }
    }

    @Test
    @Transactional
    void createPlantCategory() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the PlantCategory
        var returnedPlantCategory = om.readValue(
            restPlantCategoryMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plantCategory)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            PlantCategory.class
        );

        // Validate the PlantCategory in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertPlantCategoryUpdatableFieldsEquals(returnedPlantCategory, getPersistedPlantCategory(returnedPlantCategory));

        insertedPlantCategory = returnedPlantCategory;
    }

    @Test
    @Transactional
    void createPlantCategoryWithExistingId() throws Exception {
        // Create the PlantCategory with an existing ID
        plantCategory.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restPlantCategoryMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plantCategory)))
            .andExpect(status().isBadRequest());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllPlantCategories() throws Exception {
        // Initialize the database
        insertedPlantCategory = plantCategoryRepository.saveAndFlush(plantCategory);

        // Get all the plantCategoryList
        restPlantCategoryMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(plantCategory.getId().intValue())))
            .andExpect(jsonPath("$.[*].plantCategoryId").value(hasItem(DEFAULT_PLANT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].plantCategoryCode").value(hasItem(DEFAULT_PLANT_CATEGORY_CODE)))
            .andExpect(jsonPath("$.[*].plantCategoryName").value(hasItem(DEFAULT_PLANT_CATEGORY_NAME)))
            .andExpect(jsonPath("$.[*].plantCategoryDescription").value(hasItem(DEFAULT_PLANT_CATEGORY_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].isAudited").value(hasItem(DEFAULT_IS_AUDITED)));
    }

    @Test
    @Transactional
    void getPlantCategory() throws Exception {
        // Initialize the database
        insertedPlantCategory = plantCategoryRepository.saveAndFlush(plantCategory);

        // Get the plantCategory
        restPlantCategoryMockMvc
            .perform(get(ENTITY_API_URL_ID, plantCategory.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(plantCategory.getId().intValue()))
            .andExpect(jsonPath("$.plantCategoryId").value(DEFAULT_PLANT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.plantCategoryCode").value(DEFAULT_PLANT_CATEGORY_CODE))
            .andExpect(jsonPath("$.plantCategoryName").value(DEFAULT_PLANT_CATEGORY_NAME))
            .andExpect(jsonPath("$.plantCategoryDescription").value(DEFAULT_PLANT_CATEGORY_DESCRIPTION))
            .andExpect(jsonPath("$.isAudited").value(DEFAULT_IS_AUDITED));
    }

    @Test
    @Transactional
    void getNonExistingPlantCategory() throws Exception {
        // Get the plantCategory
        restPlantCategoryMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingPlantCategory() throws Exception {
        // Initialize the database
        insertedPlantCategory = plantCategoryRepository.saveAndFlush(plantCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plantCategory
        PlantCategory updatedPlantCategory = plantCategoryRepository.findById(plantCategory.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedPlantCategory are not directly saved in db
        em.detach(updatedPlantCategory);
        updatedPlantCategory
            .plantCategoryId(UPDATED_PLANT_CATEGORY_ID)
            .plantCategoryCode(UPDATED_PLANT_CATEGORY_CODE)
            .plantCategoryName(UPDATED_PLANT_CATEGORY_NAME)
            .plantCategoryDescription(UPDATED_PLANT_CATEGORY_DESCRIPTION)
            .isAudited(UPDATED_IS_AUDITED);

        restPlantCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedPlantCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedPlantCategory))
            )
            .andExpect(status().isOk());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedPlantCategoryToMatchAllProperties(updatedPlantCategory);
    }

    @Test
    @Transactional
    void putNonExistingPlantCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantCategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, plantCategory.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plantCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchPlantCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantCategoryMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(plantCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamPlantCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantCategoryMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(plantCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdatePlantCategoryWithPatch() throws Exception {
        // Initialize the database
        insertedPlantCategory = plantCategoryRepository.saveAndFlush(plantCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plantCategory using partial update
        PlantCategory partialUpdatedPlantCategory = new PlantCategory();
        partialUpdatedPlantCategory.setId(plantCategory.getId());

        partialUpdatedPlantCategory
            .plantCategoryId(UPDATED_PLANT_CATEGORY_ID)
            .plantCategoryCode(UPDATED_PLANT_CATEGORY_CODE)
            .plantCategoryName(UPDATED_PLANT_CATEGORY_NAME)
            .plantCategoryDescription(UPDATED_PLANT_CATEGORY_DESCRIPTION);

        restPlantCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlantCategory))
            )
            .andExpect(status().isOk());

        // Validate the PlantCategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlantCategoryUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedPlantCategory, plantCategory),
            getPersistedPlantCategory(plantCategory)
        );
    }

    @Test
    @Transactional
    void fullUpdatePlantCategoryWithPatch() throws Exception {
        // Initialize the database
        insertedPlantCategory = plantCategoryRepository.saveAndFlush(plantCategory);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the plantCategory using partial update
        PlantCategory partialUpdatedPlantCategory = new PlantCategory();
        partialUpdatedPlantCategory.setId(plantCategory.getId());

        partialUpdatedPlantCategory
            .plantCategoryId(UPDATED_PLANT_CATEGORY_ID)
            .plantCategoryCode(UPDATED_PLANT_CATEGORY_CODE)
            .plantCategoryName(UPDATED_PLANT_CATEGORY_NAME)
            .plantCategoryDescription(UPDATED_PLANT_CATEGORY_DESCRIPTION)
            .isAudited(UPDATED_IS_AUDITED);

        restPlantCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedPlantCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedPlantCategory))
            )
            .andExpect(status().isOk());

        // Validate the PlantCategory in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPlantCategoryUpdatableFieldsEquals(partialUpdatedPlantCategory, getPersistedPlantCategory(partialUpdatedPlantCategory));
    }

    @Test
    @Transactional
    void patchNonExistingPlantCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantCategory.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restPlantCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, plantCategory.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plantCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchPlantCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantCategoryMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(plantCategory))
            )
            .andExpect(status().isBadRequest());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamPlantCategory() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        plantCategory.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restPlantCategoryMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(plantCategory)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the PlantCategory in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deletePlantCategory() throws Exception {
        // Initialize the database
        insertedPlantCategory = plantCategoryRepository.saveAndFlush(plantCategory);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the plantCategory
        restPlantCategoryMockMvc
            .perform(delete(ENTITY_API_URL_ID, plantCategory.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return plantCategoryRepository.count();
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

    protected PlantCategory getPersistedPlantCategory(PlantCategory plantCategory) {
        return plantCategoryRepository.findById(plantCategory.getId()).orElseThrow();
    }

    protected void assertPersistedPlantCategoryToMatchAllProperties(PlantCategory expectedPlantCategory) {
        assertPlantCategoryAllPropertiesEquals(expectedPlantCategory, getPersistedPlantCategory(expectedPlantCategory));
    }

    protected void assertPersistedPlantCategoryToMatchUpdatableProperties(PlantCategory expectedPlantCategory) {
        assertPlantCategoryAllUpdatablePropertiesEquals(expectedPlantCategory, getPersistedPlantCategory(expectedPlantCategory));
    }
}
