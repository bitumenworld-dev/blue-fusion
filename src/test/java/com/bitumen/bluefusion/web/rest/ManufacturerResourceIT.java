package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.ManufacturerAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.Manufacturer;
import com.bitumen.bluefusion.repository.ManufacturerRepository;
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
 * Integration tests for the {@link ManufacturerResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class ManufacturerResourceIT {

    private static final Long DEFAULT_MANUFACTURER_ID = 1L;
    private static final Long UPDATED_MANUFACTURER_ID = 2L;

    private static final String DEFAULT_MANUFACTURER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_MANUFACTURER_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/manufacturers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private ManufacturerRepository manufacturerRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restManufacturerMockMvc;

    private Manufacturer manufacturer;

    private Manufacturer insertedManufacturer;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manufacturer createEntity() {
        return new Manufacturer().manufacturerId(DEFAULT_MANUFACTURER_ID).manufacturerName(DEFAULT_MANUFACTURER_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Manufacturer createUpdatedEntity() {
        return new Manufacturer().manufacturerId(UPDATED_MANUFACTURER_ID).manufacturerName(UPDATED_MANUFACTURER_NAME);
    }

    @BeforeEach
    void initTest() {
        manufacturer = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedManufacturer != null) {
            manufacturerRepository.delete(insertedManufacturer);
            insertedManufacturer = null;
        }
    }

    @Test
    @Transactional
    void createManufacturer() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Manufacturer
        var returnedManufacturer = om.readValue(
            restManufacturerMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(manufacturer)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Manufacturer.class
        );

        // Validate the Manufacturer in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertManufacturerUpdatableFieldsEquals(returnedManufacturer, getPersistedManufacturer(returnedManufacturer));

        insertedManufacturer = returnedManufacturer;
    }

    @Test
    @Transactional
    void createManufacturerWithExistingId() throws Exception {
        // Create the Manufacturer with an existing ID
        manufacturer.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restManufacturerMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(manufacturer)))
            .andExpect(status().isBadRequest());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllManufacturers() throws Exception {
        // Initialize the database
        insertedManufacturer = manufacturerRepository.saveAndFlush(manufacturer);

        // Get all the manufacturerList
        restManufacturerMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(manufacturer.getId().intValue())))
            .andExpect(jsonPath("$.[*].manufacturerId").value(hasItem(DEFAULT_MANUFACTURER_ID.intValue())))
            .andExpect(jsonPath("$.[*].manufacturerName").value(hasItem(DEFAULT_MANUFACTURER_NAME)));
    }

    @Test
    @Transactional
    void getManufacturer() throws Exception {
        // Initialize the database
        insertedManufacturer = manufacturerRepository.saveAndFlush(manufacturer);

        // Get the manufacturer
        restManufacturerMockMvc
            .perform(get(ENTITY_API_URL_ID, manufacturer.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(manufacturer.getId().intValue()))
            .andExpect(jsonPath("$.manufacturerId").value(DEFAULT_MANUFACTURER_ID.intValue()))
            .andExpect(jsonPath("$.manufacturerName").value(DEFAULT_MANUFACTURER_NAME));
    }

    @Test
    @Transactional
    void getNonExistingManufacturer() throws Exception {
        // Get the manufacturer
        restManufacturerMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingManufacturer() throws Exception {
        // Initialize the database
        insertedManufacturer = manufacturerRepository.saveAndFlush(manufacturer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the manufacturer
        Manufacturer updatedManufacturer = manufacturerRepository.findById(manufacturer.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedManufacturer are not directly saved in db
        em.detach(updatedManufacturer);
        updatedManufacturer.manufacturerId(UPDATED_MANUFACTURER_ID).manufacturerName(UPDATED_MANUFACTURER_NAME);

        restManufacturerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedManufacturer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedManufacturer))
            )
            .andExpect(status().isOk());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedManufacturerToMatchAllProperties(updatedManufacturer);
    }

    @Test
    @Transactional
    void putNonExistingManufacturer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManufacturerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, manufacturer.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(manufacturer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchManufacturer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(manufacturer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamManufacturer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(manufacturer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateManufacturerWithPatch() throws Exception {
        // Initialize the database
        insertedManufacturer = manufacturerRepository.saveAndFlush(manufacturer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the manufacturer using partial update
        Manufacturer partialUpdatedManufacturer = new Manufacturer();
        partialUpdatedManufacturer.setId(manufacturer.getId());

        partialUpdatedManufacturer.manufacturerId(UPDATED_MANUFACTURER_ID);

        restManufacturerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManufacturer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedManufacturer))
            )
            .andExpect(status().isOk());

        // Validate the Manufacturer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertManufacturerUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedManufacturer, manufacturer),
            getPersistedManufacturer(manufacturer)
        );
    }

    @Test
    @Transactional
    void fullUpdateManufacturerWithPatch() throws Exception {
        // Initialize the database
        insertedManufacturer = manufacturerRepository.saveAndFlush(manufacturer);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the manufacturer using partial update
        Manufacturer partialUpdatedManufacturer = new Manufacturer();
        partialUpdatedManufacturer.setId(manufacturer.getId());

        partialUpdatedManufacturer.manufacturerId(UPDATED_MANUFACTURER_ID).manufacturerName(UPDATED_MANUFACTURER_NAME);

        restManufacturerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedManufacturer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedManufacturer))
            )
            .andExpect(status().isOk());

        // Validate the Manufacturer in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertManufacturerUpdatableFieldsEquals(partialUpdatedManufacturer, getPersistedManufacturer(partialUpdatedManufacturer));
    }

    @Test
    @Transactional
    void patchNonExistingManufacturer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturer.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restManufacturerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, manufacturer.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(manufacturer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchManufacturer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(manufacturer))
            )
            .andExpect(status().isBadRequest());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamManufacturer() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        manufacturer.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restManufacturerMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(manufacturer)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Manufacturer in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteManufacturer() throws Exception {
        // Initialize the database
        insertedManufacturer = manufacturerRepository.saveAndFlush(manufacturer);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the manufacturer
        restManufacturerMockMvc
            .perform(delete(ENTITY_API_URL_ID, manufacturer.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return manufacturerRepository.count();
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

    protected Manufacturer getPersistedManufacturer(Manufacturer manufacturer) {
        return manufacturerRepository.findById(manufacturer.getId()).orElseThrow();
    }

    protected void assertPersistedManufacturerToMatchAllProperties(Manufacturer expectedManufacturer) {
        assertManufacturerAllPropertiesEquals(expectedManufacturer, getPersistedManufacturer(expectedManufacturer));
    }

    protected void assertPersistedManufacturerToMatchUpdatableProperties(Manufacturer expectedManufacturer) {
        assertManufacturerAllUpdatablePropertiesEquals(expectedManufacturer, getPersistedManufacturer(expectedManufacturer));
    }
}
