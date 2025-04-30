package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.AssetPlantPhotoAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.AssetPlantPhoto;
import com.bitumen.bluefusion.domain.enumeration.AssetPlantPhotoLabel;
import com.bitumen.bluefusion.repository.AssetPlantPhotoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import java.util.Base64;
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
 * Integration tests for the {@link AssetPlantPhotoResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetPlantPhotoResourceIT {

    private static final Long DEFAULT_ASSET_PLANT_PHOTO_ID = 1L;
    private static final Long UPDATED_ASSET_PLANT_PHOTO_ID = 2L;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final byte[] DEFAULT_IMAGE = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_IMAGE = TestUtil.createByteArray(1, "1");
    private static final String DEFAULT_IMAGE_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_IMAGE_CONTENT_TYPE = "image/png";

    private static final Long DEFAULT_ASSET_PLANT_ID = 1L;
    private static final Long UPDATED_ASSET_PLANT_ID = 2L;

    private static final AssetPlantPhotoLabel DEFAULT_ASSET_PLANT_PHOTO_LABEL = AssetPlantPhotoLabel.PHOTO;
    private static final AssetPlantPhotoLabel UPDATED_ASSET_PLANT_PHOTO_LABEL = AssetPlantPhotoLabel.DATA_PLATE;

    private static final String ENTITY_API_URL = "/api/asset-plant-photos";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetPlantPhotoRepository assetPlantPhotoRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetPlantPhotoMockMvc;

    private AssetPlantPhoto assetPlantPhoto;

    private AssetPlantPhoto insertedAssetPlantPhoto;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetPlantPhoto createEntity() {
        return new AssetPlantPhoto()
            .assetPlantPhotoId(DEFAULT_ASSET_PLANT_PHOTO_ID)
            .name(DEFAULT_NAME)
            .image(DEFAULT_IMAGE)
            .imageContentType(DEFAULT_IMAGE_CONTENT_TYPE)
            .assetPlantId(DEFAULT_ASSET_PLANT_ID)
            .assetPlantPhotoLabel(DEFAULT_ASSET_PLANT_PHOTO_LABEL);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetPlantPhoto createUpdatedEntity() {
        return new AssetPlantPhoto()
            .assetPlantPhotoId(UPDATED_ASSET_PLANT_PHOTO_ID)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .assetPlantPhotoLabel(UPDATED_ASSET_PLANT_PHOTO_LABEL);
    }

    @BeforeEach
    void initTest() {
        assetPlantPhoto = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAssetPlantPhoto != null) {
            assetPlantPhotoRepository.delete(insertedAssetPlantPhoto);
            insertedAssetPlantPhoto = null;
        }
    }

    @Test
    @Transactional
    void createAssetPlantPhoto() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssetPlantPhoto
        var returnedAssetPlantPhoto = om.readValue(
            restAssetPlantPhotoMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlantPhoto)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetPlantPhoto.class
        );

        // Validate the AssetPlantPhoto in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAssetPlantPhotoUpdatableFieldsEquals(returnedAssetPlantPhoto, getPersistedAssetPlantPhoto(returnedAssetPlantPhoto));

        insertedAssetPlantPhoto = returnedAssetPlantPhoto;
    }

    @Test
    @Transactional
    void createAssetPlantPhotoWithExistingId() throws Exception {
        // Create the AssetPlantPhoto with an existing ID
        assetPlantPhoto.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetPlantPhotoMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlantPhoto)))
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetPlantPhotos() throws Exception {
        // Initialize the database
        insertedAssetPlantPhoto = assetPlantPhotoRepository.saveAndFlush(assetPlantPhoto);

        // Get all the assetPlantPhotoList
        restAssetPlantPhotoMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetPlantPhoto.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetPlantPhotoId").value(hasItem(DEFAULT_ASSET_PLANT_PHOTO_ID.intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME)))
            .andExpect(jsonPath("$.[*].imageContentType").value(hasItem(DEFAULT_IMAGE_CONTENT_TYPE)))
            .andExpect(jsonPath("$.[*].image").value(hasItem(Base64.getEncoder().encodeToString(DEFAULT_IMAGE))))
            .andExpect(jsonPath("$.[*].assetPlantId").value(hasItem(DEFAULT_ASSET_PLANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].assetPlantPhotoLabel").value(hasItem(DEFAULT_ASSET_PLANT_PHOTO_LABEL.toString())));
    }

    @Test
    @Transactional
    void getAssetPlantPhoto() throws Exception {
        // Initialize the database
        insertedAssetPlantPhoto = assetPlantPhotoRepository.saveAndFlush(assetPlantPhoto);

        // Get the assetPlantPhoto
        restAssetPlantPhotoMockMvc
            .perform(get(ENTITY_API_URL_ID, assetPlantPhoto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetPlantPhoto.getId().intValue()))
            .andExpect(jsonPath("$.assetPlantPhotoId").value(DEFAULT_ASSET_PLANT_PHOTO_ID.intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME))
            .andExpect(jsonPath("$.imageContentType").value(DEFAULT_IMAGE_CONTENT_TYPE))
            .andExpect(jsonPath("$.image").value(Base64.getEncoder().encodeToString(DEFAULT_IMAGE)))
            .andExpect(jsonPath("$.assetPlantId").value(DEFAULT_ASSET_PLANT_ID.intValue()))
            .andExpect(jsonPath("$.assetPlantPhotoLabel").value(DEFAULT_ASSET_PLANT_PHOTO_LABEL.toString()));
    }

    @Test
    @Transactional
    void getNonExistingAssetPlantPhoto() throws Exception {
        // Get the assetPlantPhoto
        restAssetPlantPhotoMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetPlantPhoto() throws Exception {
        // Initialize the database
        insertedAssetPlantPhoto = assetPlantPhotoRepository.saveAndFlush(assetPlantPhoto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlantPhoto
        AssetPlantPhoto updatedAssetPlantPhoto = assetPlantPhotoRepository.findById(assetPlantPhoto.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAssetPlantPhoto are not directly saved in db
        em.detach(updatedAssetPlantPhoto);
        updatedAssetPlantPhoto
            .assetPlantPhotoId(UPDATED_ASSET_PLANT_PHOTO_ID)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .assetPlantPhotoLabel(UPDATED_ASSET_PLANT_PHOTO_LABEL);

        restAssetPlantPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssetPlantPhoto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAssetPlantPhoto))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetPlantPhotoToMatchAllProperties(updatedAssetPlantPhoto);
    }

    @Test
    @Transactional
    void putNonExistingAssetPlantPhoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantPhoto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetPlantPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetPlantPhoto.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetPlantPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetPlantPhoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantPhoto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantPhotoMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetPlantPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetPlantPhoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantPhoto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantPhotoMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlantPhoto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetPlantPhotoWithPatch() throws Exception {
        // Initialize the database
        insertedAssetPlantPhoto = assetPlantPhotoRepository.saveAndFlush(assetPlantPhoto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlantPhoto using partial update
        AssetPlantPhoto partialUpdatedAssetPlantPhoto = new AssetPlantPhoto();
        partialUpdatedAssetPlantPhoto.setId(assetPlantPhoto.getId());

        partialUpdatedAssetPlantPhoto.name(UPDATED_NAME).image(UPDATED_IMAGE).imageContentType(UPDATED_IMAGE_CONTENT_TYPE);

        restAssetPlantPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetPlantPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetPlantPhoto))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlantPhoto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetPlantPhotoUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssetPlantPhoto, assetPlantPhoto),
            getPersistedAssetPlantPhoto(assetPlantPhoto)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssetPlantPhotoWithPatch() throws Exception {
        // Initialize the database
        insertedAssetPlantPhoto = assetPlantPhotoRepository.saveAndFlush(assetPlantPhoto);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlantPhoto using partial update
        AssetPlantPhoto partialUpdatedAssetPlantPhoto = new AssetPlantPhoto();
        partialUpdatedAssetPlantPhoto.setId(assetPlantPhoto.getId());

        partialUpdatedAssetPlantPhoto
            .assetPlantPhotoId(UPDATED_ASSET_PLANT_PHOTO_ID)
            .name(UPDATED_NAME)
            .image(UPDATED_IMAGE)
            .imageContentType(UPDATED_IMAGE_CONTENT_TYPE)
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .assetPlantPhotoLabel(UPDATED_ASSET_PLANT_PHOTO_LABEL);

        restAssetPlantPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetPlantPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetPlantPhoto))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlantPhoto in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetPlantPhotoUpdatableFieldsEquals(
            partialUpdatedAssetPlantPhoto,
            getPersistedAssetPlantPhoto(partialUpdatedAssetPlantPhoto)
        );
    }

    @Test
    @Transactional
    void patchNonExistingAssetPlantPhoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantPhoto.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetPlantPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetPlantPhoto.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetPlantPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetPlantPhoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantPhoto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantPhotoMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetPlantPhoto))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetPlantPhoto() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlantPhoto.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantPhotoMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assetPlantPhoto)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetPlantPhoto in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetPlantPhoto() throws Exception {
        // Initialize the database
        insertedAssetPlantPhoto = assetPlantPhotoRepository.saveAndFlush(assetPlantPhoto);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assetPlantPhoto
        restAssetPlantPhotoMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetPlantPhoto.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetPlantPhotoRepository.count();
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

    protected AssetPlantPhoto getPersistedAssetPlantPhoto(AssetPlantPhoto assetPlantPhoto) {
        return assetPlantPhotoRepository.findById(assetPlantPhoto.getId()).orElseThrow();
    }

    protected void assertPersistedAssetPlantPhotoToMatchAllProperties(AssetPlantPhoto expectedAssetPlantPhoto) {
        assertAssetPlantPhotoAllPropertiesEquals(expectedAssetPlantPhoto, getPersistedAssetPlantPhoto(expectedAssetPlantPhoto));
    }

    protected void assertPersistedAssetPlantPhotoToMatchUpdatableProperties(AssetPlantPhoto expectedAssetPlantPhoto) {
        assertAssetPlantPhotoAllUpdatablePropertiesEquals(expectedAssetPlantPhoto, getPersistedAssetPlantPhoto(expectedAssetPlantPhoto));
    }
}
