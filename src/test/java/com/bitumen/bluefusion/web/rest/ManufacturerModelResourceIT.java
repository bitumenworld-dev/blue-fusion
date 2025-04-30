package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.ManufacturerModelAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.ManufacturerModel;
import com.bitumen.bluefusion.repository.ManufacturerModelRepository;
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
 * Integration tests for the {@link ManufacturerModelResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ManufacturerModelResourceIT {

    private static final Long DEFAULT_MODEL_ID = 1L;
    private static final Long UPDATED_MODEL_ID = 2L;

    private static final String DEFAULT_MODEL_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MODEL_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/manufacturer-models";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ManufacturerModelRepository manufacturerModelRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManufacturerModelMockMvc;

    private ManufacturerModel manufacturerModel;

    private ManufacturerModel insertedManufacturerModel;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManufacturerModel createEntity() {
        return new ManufacturerModel().modelId(DEFAULT_MODEL_ID).modelName(DEFAULT_MODEL_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static ManufacturerModel createUpdatedEntity() {
        return new ManufacturerModel().modelId(UPDATED_MODEL_ID).modelName(UPDATED_MODEL_NAME);
    }

    @BeforeEach
    void initTest() {
        manufacturerModel = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedManufacturerModel != null) {
            manufacturerModelRepository.delete(insertedManufacturerModel);
            insertedManufacturerModel = null;
        }
    }

    @Test
    @Transactional
    void createManufacturerModel() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the ManufacturerModel
        var returnedManufacturerModel = om.readValue(
            restManufacturerModelMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(manufacturerModel)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            ManufacturerModel.class
        );

        // Validate the ManufacturerModel in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertManufacturerModelUpdatableFieldsEquals(returnedManufacturerModel, getPersistedManufacturerModel(returnedManufacturerModel));

        insertedManufacturerModel = returnedManufacturerModel;
    }

    @Test
    @Transactional
    void createManufacturerModelWithExistingId() throws Exception {
        // Create the ManufacturerModel with an existing ID
        manufacturerModel.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManufacturerModelMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(manufacturerModel)))
            .andExpect(status().isBadRequest());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManufacturerModels() throws Exception {
        // Initialize the database
        insertedManufacturerModel = manufacturerModelRepository.saveAndFlush(manufacturerModel);

        // Get all the manufacturerModelList
        restManufacturerModelMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturerModel.getId().intValue())))
            .andExpect(jsonPath("$.[*].modelId").value(hasItem(DEFAULT_MODEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].modelName").value(hasItem(DEFAULT_MODEL_NAME)));
    }

    @Test
    @Transactional
    void getManufacturerModel() throws Exception {
        // Initialize the database
        insertedManufacturerModel = manufacturerModelRepository.saveAndFlush(manufacturerModel);

        // Get the manufacturerModel
        restManufacturerModelMockMvc
            .perform(get(ENTITY_API_URL_ID, manufacturerModel.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manufacturerModel.getId().intValue()))
            .andExpect(jsonPath("$.modelId").value(DEFAULT_MODEL_ID.intValue()))
            .andExpect(jsonPath("$.modelName").value(DEFAULT_MODEL_NAME));
    }

    @Test
    @Transactional
    void getNonExistingManufacturerModel() throws Exception {
        // Get the manufacturerModel
        restManufacturerModelMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingManufacturerModel() throws Exception {
        // Initialize the database
        insertedManufacturerModel = manufacturerModelRepository.saveAndFlush(manufacturerModel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the manufacturerModel
        ManufacturerModel updatedManufacturerModel = manufacturerModelRepository.findById(manufacturerModel.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedManufacturerModel are not directly saved in db
        em.detach(updatedManufacturerModel);
        updatedManufacturerModel.modelId(UPDATED_MODEL_ID).modelName(UPDATED_MODEL_NAME);

        restManufacturerModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedManufacturerModel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedManufacturerModel))
            )
            .andExpect(status().isOk());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedManufacturerModelToMatchAllProperties(updatedManufacturerModel);
    }

    @Test
    @Transactional
    void putNonExistingManufacturerModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturerModel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManufacturerModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, manufacturerModel.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(manufacturerModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManufacturerModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturerModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerModelMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(manufacturerModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManufacturerModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturerModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerModelMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(manufacturerModel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManufacturerModelWithPatch() throws Exception {
        // Initialize the database
        insertedManufacturerModel = manufacturerModelRepository.saveAndFlush(manufacturerModel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the manufacturerModel using partial update
        ManufacturerModel partialUpdatedManufacturerModel = new ManufacturerModel();
        partialUpdatedManufacturerModel.setId(manufacturerModel.getId());

        partialUpdatedManufacturerModel.modelId(UPDATED_MODEL_ID);

        restManufacturerModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManufacturerModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedManufacturerModel))
            )
            .andExpect(status().isOk());

        // Validate the ManufacturerModel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertManufacturerModelUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedManufacturerModel, manufacturerModel),
            getPersistedManufacturerModel(manufacturerModel)
        );
    }

    @Test
    @Transactional
    void fullUpdateManufacturerModelWithPatch() throws Exception {
        // Initialize the database
        insertedManufacturerModel = manufacturerModelRepository.saveAndFlush(manufacturerModel);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the manufacturerModel using partial update
        ManufacturerModel partialUpdatedManufacturerModel = new ManufacturerModel();
        partialUpdatedManufacturerModel.setId(manufacturerModel.getId());

        partialUpdatedManufacturerModel.modelId(UPDATED_MODEL_ID).modelName(UPDATED_MODEL_NAME);

        restManufacturerModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManufacturerModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedManufacturerModel))
            )
            .andExpect(status().isOk());

        // Validate the ManufacturerModel in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertManufacturerModelUpdatableFieldsEquals(
            partialUpdatedManufacturerModel,
            getPersistedManufacturerModel(partialUpdatedManufacturerModel)
        );
    }

    @Test
    @Transactional
    void patchNonExistingManufacturerModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturerModel.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManufacturerModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, manufacturerModel.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(manufacturerModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManufacturerModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturerModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerModelMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(manufacturerModel))
            )
            .andExpect(status().isBadRequest());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManufacturerModel() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturerModel.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerModelMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(manufacturerModel)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the ManufacturerModel in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManufacturerModel() throws Exception {
        // Initialize the database
        insertedManufacturerModel = manufacturerModelRepository.saveAndFlush(manufacturerModel);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the manufacturerModel
        restManufacturerModelMockMvc
            .perform(delete(ENTITY_API_URL_ID, manufacturerModel.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return manufacturerModelRepository.count();
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

    protected ManufacturerModel getPersistedManufacturerModel(ManufacturerModel manufacturerModel) {
        return manufacturerModelRepository.findById(manufacturerModel.getId()).orElseThrow();
    }

    protected void assertPersistedManufacturerModelToMatchAllProperties(ManufacturerModel expectedManufacturerModel) {
        assertManufacturerModelAllPropertiesEquals(expectedManufacturerModel, getPersistedManufacturerModel(expectedManufacturerModel));
    }

    protected void assertPersistedManufacturerModelToMatchUpdatableProperties(ManufacturerModel expectedManufacturerModel) {
        assertManufacturerModelAllUpdatablePropertiesEquals(
            expectedManufacturerModel,
            getPersistedManufacturerModel(expectedManufacturerModel)
        );
    }
}
