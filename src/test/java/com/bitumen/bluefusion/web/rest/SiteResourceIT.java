package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.SiteAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.Site;
import com.bitumen.bluefusion.repository.SiteRepository;
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
 * Integration tests for the {@link SiteResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class SiteResourceIT {

    private static final Long DEFAULT_SITE_ID = 1L;
    private static final Long UPDATED_SITE_ID = 2L;

    private static final String DEFAULT_SITE_NAME = "AAAAAAAAAA";
    private static final String UPDATED_SITE_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LATITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LATITUDE = "BBBBBBBBBB";

    private static final String DEFAULT_LONGITUDE = "AAAAAAAAAA";
    private static final String UPDATED_LONGITUDE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_SITE_NOTES = "AAAAAAAAAA";
    private static final String UPDATED_SITE_NOTES = "BBBBBBBBBB";

    private static final String DEFAULT_SITE_IMAGE_URL = "AAAAAAAAAA";
    private static final String UPDATED_SITE_IMAGE_URL = "BBBBBBBBBB";

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final String ENTITY_API_URL = "/api/sites";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private SiteRepository siteRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restSiteMockMvc;

    private Site site;

    private Site insertedSite;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createEntity() {
        return new Site()
            .siteId(DEFAULT_SITE_ID)
            .siteName(DEFAULT_SITE_NAME)
            .latitude(DEFAULT_LATITUDE)
            .longitude(DEFAULT_LONGITUDE)
            .isActive(DEFAULT_IS_ACTIVE)
            .siteNotes(DEFAULT_SITE_NOTES)
            .siteImageUrl(DEFAULT_SITE_IMAGE_URL)
            .companyId(DEFAULT_COMPANY_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Site createUpdatedEntity() {
        return new Site()
            .siteId(UPDATED_SITE_ID)
            .siteName(UPDATED_SITE_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .isActive(UPDATED_IS_ACTIVE)
            .siteNotes(UPDATED_SITE_NOTES)
            .siteImageUrl(UPDATED_SITE_IMAGE_URL)
            .companyId(UPDATED_COMPANY_ID);
    }

    @BeforeEach
    void initTest() {
        site = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedSite != null) {
            siteRepository.delete(insertedSite);
            insertedSite = null;
        }
    }

    @Test
    @Transactional
    void createSite() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the Site
        var returnedSite = om.readValue(
            restSiteMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(site)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            Site.class
        );

        // Validate the Site in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertSiteUpdatableFieldsEquals(returnedSite, getPersistedSite(returnedSite));

        insertedSite = returnedSite;
    }

    @Test
    @Transactional
    void createSiteWithExistingId() throws Exception {
        // Create the Site with an existing ID
        site.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restSiteMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(site)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllSites() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get all the siteList
        restSiteMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(site.getId().intValue())))
            .andExpect(jsonPath("$.[*].siteId").value(hasItem(DEFAULT_SITE_ID.intValue())))
            .andExpect(jsonPath("$.[*].siteName").value(hasItem(DEFAULT_SITE_NAME)))
            .andExpect(jsonPath("$.[*].latitude").value(hasItem(DEFAULT_LATITUDE)))
            .andExpect(jsonPath("$.[*].longitude").value(hasItem(DEFAULT_LONGITUDE)))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE)))
            .andExpect(jsonPath("$.[*].siteNotes").value(hasItem(DEFAULT_SITE_NOTES)))
            .andExpect(jsonPath("$.[*].siteImageUrl").value(hasItem(DEFAULT_SITE_IMAGE_URL)))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())));
    }

    @Test
    @Transactional
    void getSite() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        // Get the site
        restSiteMockMvc
            .perform(get(ENTITY_API_URL_ID, site.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(site.getId().intValue()))
            .andExpect(jsonPath("$.siteId").value(DEFAULT_SITE_ID.intValue()))
            .andExpect(jsonPath("$.siteName").value(DEFAULT_SITE_NAME))
            .andExpect(jsonPath("$.latitude").value(DEFAULT_LATITUDE))
            .andExpect(jsonPath("$.longitude").value(DEFAULT_LONGITUDE))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE))
            .andExpect(jsonPath("$.siteNotes").value(DEFAULT_SITE_NOTES))
            .andExpect(jsonPath("$.siteImageUrl").value(DEFAULT_SITE_IMAGE_URL))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingSite() throws Exception {
        // Get the site
        restSiteMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingSite() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the site
        Site updatedSite = siteRepository.findById(site.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedSite are not directly saved in db
        em.detach(updatedSite);
        updatedSite
            .siteId(UPDATED_SITE_ID)
            .siteName(UPDATED_SITE_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .isActive(UPDATED_IS_ACTIVE)
            .siteNotes(UPDATED_SITE_NOTES)
            .siteImageUrl(UPDATED_SITE_IMAGE_URL)
            .companyId(UPDATED_COMPANY_ID);

        restSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedSite.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedSite))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedSiteToMatchAllProperties(updatedSite);
    }

    @Test
    @Transactional
    void putNonExistingSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(put(ENTITY_API_URL_ID, site.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(site)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(site))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(site)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateSiteWithPatch() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the site using partial update
        Site partialUpdatedSite = new Site();
        partialUpdatedSite.setId(site.getId());

        partialUpdatedSite
            .siteName(UPDATED_SITE_NAME)
            .isActive(UPDATED_IS_ACTIVE)
            .siteNotes(UPDATED_SITE_NOTES)
            .siteImageUrl(UPDATED_SITE_IMAGE_URL);

        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSite.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSite))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteUpdatableFieldsEquals(createUpdateProxyForBean(partialUpdatedSite, site), getPersistedSite(site));
    }

    @Test
    @Transactional
    void fullUpdateSiteWithPatch() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the site using partial update
        Site partialUpdatedSite = new Site();
        partialUpdatedSite.setId(site.getId());

        partialUpdatedSite
            .siteId(UPDATED_SITE_ID)
            .siteName(UPDATED_SITE_NAME)
            .latitude(UPDATED_LATITUDE)
            .longitude(UPDATED_LONGITUDE)
            .isActive(UPDATED_IS_ACTIVE)
            .siteNotes(UPDATED_SITE_NOTES)
            .siteImageUrl(UPDATED_SITE_IMAGE_URL)
            .companyId(UPDATED_COMPANY_ID);

        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedSite.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedSite))
            )
            .andExpect(status().isOk());

        // Validate the Site in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertSiteUpdatableFieldsEquals(partialUpdatedSite, getPersistedSite(partialUpdatedSite));
    }

    @Test
    @Transactional
    void patchNonExistingSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(patch(ENTITY_API_URL_ID, site.getId()).contentType("application/merge-patch+json").content(om.writeValueAsBytes(site)))
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(site))
            )
            .andExpect(status().isBadRequest());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamSite() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        site.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restSiteMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(site)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the Site in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteSite() throws Exception {
        // Initialize the database
        insertedSite = siteRepository.saveAndFlush(site);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the site
        restSiteMockMvc
            .perform(delete(ENTITY_API_URL_ID, site.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return siteRepository.count();
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

    protected Site getPersistedSite(Site site) {
        return siteRepository.findById(site.getId()).orElseThrow();
    }

    protected void assertPersistedSiteToMatchAllProperties(Site expectedSite) {
        assertSiteAllPropertiesEquals(expectedSite, getPersistedSite(expectedSite));
    }

    protected void assertPersistedSiteToMatchUpdatableProperties(Site expectedSite) {
        assertSiteAllUpdatablePropertiesEquals(expectedSite, getPersistedSite(expectedSite));
    }
}
