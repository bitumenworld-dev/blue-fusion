package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.WorkshopAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.Workshop;
import com.bitumen.bluefusion.repository.WorkshopRepository;
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
 * Integration tests for the {@link WorkshopResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class WorkshopResourceIT {

    private static final Long DEFAULT_WORKSHOP_ID = 1L;
    private static final Long UPDATED_WORKSHOP_ID = 2L;

    private static final Long DEFAULT_SITE_ID = 1L;
    private static final Long UPDATED_SITE_ID = 2L;

    private static final String DEFAULT_WORKSHOP_NAME = "AAAAAAAAAA";
    private static final String UPDATED_WORKSHOP_NAME = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/workshops";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private WorkshopRepository workshopRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restWorkshopMockMvc;

    private Workshop workshop;

    private Workshop insertedWorkshop;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workshop createEntity() {
        return new Workshop().workshopId(DEFAULT_WORKSHOP_ID).siteId(DEFAULT_SITE_ID).workshopName(DEFAULT_WORKSHOP_NAME);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Workshop createUpdatedEntity() {
        return new Workshop().workshopId(UPDATED_WORKSHOP_ID).siteId(UPDATED_SITE_ID).workshopName(UPDATED_WORKSHOP_NAME);
    }

    @BeforeEach
    void initTest() {
        workshop = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedWorkshop != null) {
            workshopRepository.delete(insertedWorkshop);
            insertedWorkshop = null;
        }
    }

    @Test
    @Transactional
    void createWorkshop() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Workshop
        var returnedWorkshop = om.readValue(
            restWorkshopMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workshop)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Workshop.class
        );

        // Validate the Workshop in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertWorkshopUpdatableFieldsEquals(returnedWorkshop, getPersistedWorkshop(returnedWorkshop));

        insertedWorkshop = returnedWorkshop;
    }

    @Test
    @Transactional
    void createWorkshopWithExistingId() throws Exception {
        // Create the Workshop with an existing ID
        workshop.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restWorkshopMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workshop)))
            .andExpect(status().isBadRequest());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllWorkshops() throws Exception {
        // Initialize the database
        insertedWorkshop = workshopRepository.saveAndFlush(workshop);

        // Get all the workshopList
        restWorkshopMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(workshop.getId().intValue())))
            .andExpect(jsonPath("$.[*].workshopId").value(hasItem(DEFAULT_WORKSHOP_ID.intValue())))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID.intValue())))
            .andExpect(jsonPath("$.[*].workshopName").value(hasItem(DEFAULT_WORKSHOP_NAME)));
    }

    @Test
    @Transactional
    void getWorkshop() throws Exception {
        // Initialize the database
        insertedWorkshop = workshopRepository.saveAndFlush(workshop);

        // Get the workshop
        restWorkshopMockMvc
            .perform(get(ENTITY_API_URL_ID, workshop.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(workshop.getId().intValue()))
            .andExpect(jsonPath("$.workshopId").value(DEFAULT_WORKSHOP_ID.intValue()))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID.intValue()))
            .andExpect(jsonPath("$.workshopName").value(DEFAULT_WORKSHOP_NAME));
    }

    @Test
    @Transactional
    void getNonExistingWorkshop() throws Exception {
        // Get the workshop
        restWorkshopMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingWorkshop() throws Exception {
        // Initialize the database
        insertedWorkshop = workshopRepository.saveAndFlush(workshop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workshop
        Workshop updatedWorkshop = workshopRepository.findById(workshop.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedWorkshop are not directly saved in db
        em.detach(updatedWorkshop);
        updatedWorkshop.workshopId(UPDATED_WORKSHOP_ID).siteId(UPDATED_SITE_ID).workshopName(UPDATED_WORKSHOP_NAME);

        restWorkshopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedWorkshop.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedWorkshop))
            )
            .andExpect(status().isOk());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedWorkshopToMatchAllProperties(updatedWorkshop);
    }

    @Test
    @Transactional
    void putNonExistingWorkshop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkshopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, workshop.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workshop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchWorkshop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(workshop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamWorkshop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(workshop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateWorkshopWithPatch() throws Exception {
        // Initialize the database
        insertedWorkshop = workshopRepository.saveAndFlush(workshop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workshop using partial update
        Workshop partialUpdatedWorkshop = new Workshop();
        partialUpdatedWorkshop.setId(workshop.getId());

        partialUpdatedWorkshop.workshopId(UPDATED_WORKSHOP_ID);

        restWorkshopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkshop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkshop))
            )
            .andExpect(status().isOk());

        // Validate the Workshop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkshopUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedWorkshop, workshop), getPersistedWorkshop(workshop));
    }

    @Test
    @Transactional
    void fullUpdateWorkshopWithPatch() throws Exception {
        // Initialize the database
        insertedWorkshop = workshopRepository.saveAndFlush(workshop);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the workshop using partial update
        Workshop partialUpdatedWorkshop = new Workshop();
        partialUpdatedWorkshop.setId(workshop.getId());

        partialUpdatedWorkshop.workshopId(UPDATED_WORKSHOP_ID).siteId(UPDATED_SITE_ID).workshopName(UPDATED_WORKSHOP_NAME);

        restWorkshopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedWorkshop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedWorkshop))
            )
            .andExpect(status().isOk());

        // Validate the Workshop in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertWorkshopUpdatableFieldsEquals(partialUpdatedWorkshop, getPersistedWorkshop(partialUpdatedWorkshop));
    }

    @Test
    @Transactional
    void patchNonExistingWorkshop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshop.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restWorkshopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, workshop.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workshop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchWorkshop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(workshop))
            )
            .andExpect(status().isBadRequest());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamWorkshop() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        workshop.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restWorkshopMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(workshop)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Workshop in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteWorkshop() throws Exception {
        // Initialize the database
        insertedWorkshop = workshopRepository.saveAndFlush(workshop);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the workshop
        restWorkshopMockMvc
            .perform(delete(ENTITY_API_URL_ID, workshop.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return workshopRepository.count();
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

    protected Workshop getPersistedWorkshop(Workshop workshop) {
        return workshopRepository.findById(workshop.getId()).orElseThrow();
    }

    protected void assertPersistedWorkshopToMatchAllProperties(Workshop expectedWorkshop) {
        assertWorkshopAllPropertiesEquals(expectedWorkshop, getPersistedWorkshop(expectedWorkshop));
    }

    protected void assertPersistedWorkshopToMatchUpdatableProperties(Workshop expectedWorkshop) {
        assertWorkshopAllUpdatablePropertiesEquals(expectedWorkshop, getPersistedWorkshop(expectedWorkshop));
    }
}
