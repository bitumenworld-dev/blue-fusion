package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.FuelTransactionHeaderAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.FuelTransactionHeader;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import com.bitumen.bluefusion.repository.FuelTransactionHeaderRepository;
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
 * Integration tests for the {@link FuelTransactionHeaderResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class FuelTransactionHeaderResourceIT {

    private static final Long DEFAULT_FUEL_TRANSACTION_HEADER_ID = 1L;
    private static final Long UPDATED_FUEL_TRANSACTION_HEADER_ID = 2L;

    private static final Long DEFAULT_COMPANY_ID = 1L;
    private static final Long UPDATED_COMPANY_ID = 2L;

    private static final Long DEFAULT_SUPPLIER_ID = 1L;
    private static final Long UPDATED_SUPPLIER_ID = 2L;

    private static final Long DEFAULT_TRANSACTION_TYPE_ID = 1L;
    private static final Long UPDATED_TRANSACTION_TYPE_ID = 2L;

    private static final FuelType DEFAULT_FUEL_TYPE = FuelType.PETROL;
    private static final FuelType UPDATED_FUEL_TYPE = FuelType.DIESEL;

    private static final String DEFAULT_ORDER_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ORDER_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_DELIVERY_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_DELIVERY_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_GRV_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_GRV_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_INVOICE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_INVOICE_NUMBER = "BBBBBBBBBB";

    private static final Float DEFAULT_PRICE_PER_LITRE = 1F;
    private static final Float UPDATED_PRICE_PER_LITRE = 2F;

    private static final String DEFAULT_NOTE = "AAAAAAAAAA";
    private static final String UPDATED_NOTE = "BBBBBBBBBB";

    private static final String DEFAULT_REGISTRATION_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_REGISTRATION_NUMBER = "BBBBBBBBBB";

    private static final Long DEFAULT_ATTENDEE_ID = 1L;
    private static final Long UPDATED_ATTENDEE_ID = 2L;

    private static final Long DEFAULT_OPERATOR_ID = 1L;
    private static final Long UPDATED_OPERATOR_ID = 2L;

    private static final String ENTITY_API_URL = "/api/fuel-transaction-headers";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private FuelTransactionHeaderRepository fuelTransactionHeaderRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restFuelTransactionHeaderMockMvc;

    private FuelTransactionHeader fuelTransactionHeader;

    private FuelTransactionHeader insertedFuelTransactionHeader;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTransactionHeader createEntity() {
        return new FuelTransactionHeader()
            .fuelTransactionHeaderId(DEFAULT_FUEL_TRANSACTION_HEADER_ID)
            .companyId(DEFAULT_COMPANY_ID)
            .supplierId(DEFAULT_SUPPLIER_ID)
            .transactionTypeId(DEFAULT_TRANSACTION_TYPE_ID)
            .fuelType(DEFAULT_FUEL_TYPE)
            .orderNumber(DEFAULT_ORDER_NUMBER)
            .deliveryNote(DEFAULT_DELIVERY_NOTE)
            .grvNumber(DEFAULT_GRV_NUMBER)
            .invoiceNumber(DEFAULT_INVOICE_NUMBER)
            .pricePerLitre(DEFAULT_PRICE_PER_LITRE)
            .note(DEFAULT_NOTE)
            .registrationNumber(DEFAULT_REGISTRATION_NUMBER)
            .attendeeId(DEFAULT_ATTENDEE_ID)
            .operatorId(DEFAULT_OPERATOR_ID);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static FuelTransactionHeader createUpdatedEntity() {
        return new FuelTransactionHeader()
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .companyId(UPDATED_COMPANY_ID)
            .supplierId(UPDATED_SUPPLIER_ID)
            .transactionTypeId(UPDATED_TRANSACTION_TYPE_ID)
            .fuelType(UPDATED_FUEL_TYPE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .deliveryNote(UPDATED_DELIVERY_NOTE)
            .grvNumber(UPDATED_GRV_NUMBER)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .pricePerLitre(UPDATED_PRICE_PER_LITRE)
            .note(UPDATED_NOTE)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .attendeeId(UPDATED_ATTENDEE_ID)
            .operatorId(UPDATED_OPERATOR_ID);
    }

    @BeforeEach
    void initTest() {
        fuelTransactionHeader = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedFuelTransactionHeader != null) {
            fuelTransactionHeaderRepository.delete(insertedFuelTransactionHeader);
            insertedFuelTransactionHeader = null;
        }
    }

    @Test
    @Transactional
    void createFuelTransactionHeader() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the FuelTransactionHeader
        var returnedFuelTransactionHeader = om.readValue(
            restFuelTransactionHeaderMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionHeader)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            FuelTransactionHeader.class
        );

        // Validate the FuelTransactionHeader in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertFuelTransactionHeaderUpdatableFieldsEquals(
            returnedFuelTransactionHeader,
            getPersistedFuelTransactionHeader(returnedFuelTransactionHeader)
        );

        insertedFuelTransactionHeader = returnedFuelTransactionHeader;
    }

    @Test
    @Transactional
    void createFuelTransactionHeaderWithExistingId() throws Exception {
        // Create the FuelTransactionHeader with an existing ID
        fuelTransactionHeader.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restFuelTransactionHeaderMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionHeader)))
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllFuelTransactionHeaders() throws Exception {
        // Initialize the database
        insertedFuelTransactionHeader = fuelTransactionHeaderRepository.saveAndFlush(fuelTransactionHeader);

        // Get all the fuelTransactionHeaderList
        restFuelTransactionHeaderMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(fuelTransactionHeader.getId().intValue())))
            .andExpect(jsonPath("$.[*].fuelTransactionHeaderId").value(hasItem(DEFAULT_FUEL_TRANSACTION_HEADER_ID.intValue())))
            .andExpect(jsonPath("$.[*].companyId").value(hasItem(DEFAULT_COMPANY_ID.intValue())))
            .andExpect(jsonPath("$.[*].supplierId").value(hasItem(DEFAULT_SUPPLIER_ID.intValue())))
            .andExpect(jsonPath("$.[*].transactionTypeId").value(hasItem(DEFAULT_TRANSACTION_TYPE_ID.intValue())))
            .andExpect(jsonPath("$.[*].fuelType").value(hasItem(DEFAULT_FUEL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].orderNumber").value(hasItem(DEFAULT_ORDER_NUMBER)))
            .andExpect(jsonPath("$.[*].deliveryNote").value(hasItem(DEFAULT_DELIVERY_NOTE)))
            .andExpect(jsonPath("$.[*].grvNumber").value(hasItem(DEFAULT_GRV_NUMBER)))
            .andExpect(jsonPath("$.[*].invoiceNumber").value(hasItem(DEFAULT_INVOICE_NUMBER)))
            .andExpect(jsonPath("$.[*].pricePerLitre").value(hasItem(DEFAULT_PRICE_PER_LITRE.doubleValue())))
            .andExpect(jsonPath("$.[*].note").value(hasItem(DEFAULT_NOTE)))
            .andExpect(jsonPath("$.[*].registrationNumber").value(hasItem(DEFAULT_REGISTRATION_NUMBER)))
            .andExpect(jsonPath("$.[*].attendeeId").value(hasItem(DEFAULT_ATTENDEE_ID.intValue())))
            .andExpect(jsonPath("$.[*].operatorId").value(hasItem(DEFAULT_OPERATOR_ID.intValue())));
    }

    @Test
    @Transactional
    void getFuelTransactionHeader() throws Exception {
        // Initialize the database
        insertedFuelTransactionHeader = fuelTransactionHeaderRepository.saveAndFlush(fuelTransactionHeader);

        // Get the fuelTransactionHeader
        restFuelTransactionHeaderMockMvc
            .perform(get(ENTITY_API_URL_ID, fuelTransactionHeader.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(fuelTransactionHeader.getId().intValue()))
            .andExpect(jsonPath("$.fuelTransactionHeaderId").value(DEFAULT_FUEL_TRANSACTION_HEADER_ID.intValue()))
            .andExpect(jsonPath("$.companyId").value(DEFAULT_COMPANY_ID.intValue()))
            .andExpect(jsonPath("$.supplierId").value(DEFAULT_SUPPLIER_ID.intValue()))
            .andExpect(jsonPath("$.transactionTypeId").value(DEFAULT_TRANSACTION_TYPE_ID.intValue()))
            .andExpect(jsonPath("$.fuelType").value(DEFAULT_FUEL_TYPE.toString()))
            .andExpect(jsonPath("$.orderNumber").value(DEFAULT_ORDER_NUMBER))
            .andExpect(jsonPath("$.deliveryNote").value(DEFAULT_DELIVERY_NOTE))
            .andExpect(jsonPath("$.grvNumber").value(DEFAULT_GRV_NUMBER))
            .andExpect(jsonPath("$.invoiceNumber").value(DEFAULT_INVOICE_NUMBER))
            .andExpect(jsonPath("$.pricePerLitre").value(DEFAULT_PRICE_PER_LITRE.doubleValue()))
            .andExpect(jsonPath("$.note").value(DEFAULT_NOTE))
            .andExpect(jsonPath("$.registrationNumber").value(DEFAULT_REGISTRATION_NUMBER))
            .andExpect(jsonPath("$.attendeeId").value(DEFAULT_ATTENDEE_ID.intValue()))
            .andExpect(jsonPath("$.operatorId").value(DEFAULT_OPERATOR_ID.intValue()));
    }

    @Test
    @Transactional
    void getNonExistingFuelTransactionHeader() throws Exception {
        // Get the fuelTransactionHeader
        restFuelTransactionHeaderMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingFuelTransactionHeader() throws Exception {
        // Initialize the database
        insertedFuelTransactionHeader = fuelTransactionHeaderRepository.saveAndFlush(fuelTransactionHeader);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionHeader
        FuelTransactionHeader updatedFuelTransactionHeader = fuelTransactionHeaderRepository
            .findById(fuelTransactionHeader.getId())
            .orElseThrow();
        // Disconnect from session so that the updates on updatedFuelTransactionHeader are not directly saved in db
        em.detach(updatedFuelTransactionHeader);
        updatedFuelTransactionHeader
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .companyId(UPDATED_COMPANY_ID)
            .supplierId(UPDATED_SUPPLIER_ID)
            .transactionTypeId(UPDATED_TRANSACTION_TYPE_ID)
            .fuelType(UPDATED_FUEL_TYPE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .deliveryNote(UPDATED_DELIVERY_NOTE)
            .grvNumber(UPDATED_GRV_NUMBER)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .pricePerLitre(UPDATED_PRICE_PER_LITRE)
            .note(UPDATED_NOTE)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .attendeeId(UPDATED_ATTENDEE_ID)
            .operatorId(UPDATED_OPERATOR_ID);

        restFuelTransactionHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedFuelTransactionHeader.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedFuelTransactionHeader))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedFuelTransactionHeaderToMatchAllProperties(updatedFuelTransactionHeader);
    }

    @Test
    @Transactional
    void putNonExistingFuelTransactionHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionHeader.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTransactionHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, fuelTransactionHeader.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTransactionHeader))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchFuelTransactionHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionHeader.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionHeaderMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(fuelTransactionHeader))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamFuelTransactionHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionHeader.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionHeaderMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(fuelTransactionHeader)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateFuelTransactionHeaderWithPatch() throws Exception {
        // Initialize the database
        insertedFuelTransactionHeader = fuelTransactionHeaderRepository.saveAndFlush(fuelTransactionHeader);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionHeader using partial update
        FuelTransactionHeader partialUpdatedFuelTransactionHeader = new FuelTransactionHeader();
        partialUpdatedFuelTransactionHeader.setId(fuelTransactionHeader.getId());

        partialUpdatedFuelTransactionHeader
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .companyId(UPDATED_COMPANY_ID)
            .supplierId(UPDATED_SUPPLIER_ID)
            .transactionTypeId(UPDATED_TRANSACTION_TYPE_ID)
            .fuelType(UPDATED_FUEL_TYPE)
            .grvNumber(UPDATED_GRV_NUMBER)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .pricePerLitre(UPDATED_PRICE_PER_LITRE)
            .note(UPDATED_NOTE)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER);

        restFuelTransactionHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTransactionHeader.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelTransactionHeader))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionHeader in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTransactionHeaderUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedFuelTransactionHeader, fuelTransactionHeader),
            getPersistedFuelTransactionHeader(fuelTransactionHeader)
        );
    }

    @Test
    @Transactional
    void fullUpdateFuelTransactionHeaderWithPatch() throws Exception {
        // Initialize the database
        insertedFuelTransactionHeader = fuelTransactionHeaderRepository.saveAndFlush(fuelTransactionHeader);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the fuelTransactionHeader using partial update
        FuelTransactionHeader partialUpdatedFuelTransactionHeader = new FuelTransactionHeader();
        partialUpdatedFuelTransactionHeader.setId(fuelTransactionHeader.getId());

        partialUpdatedFuelTransactionHeader
            .fuelTransactionHeaderId(UPDATED_FUEL_TRANSACTION_HEADER_ID)
            .companyId(UPDATED_COMPANY_ID)
            .supplierId(UPDATED_SUPPLIER_ID)
            .transactionTypeId(UPDATED_TRANSACTION_TYPE_ID)
            .fuelType(UPDATED_FUEL_TYPE)
            .orderNumber(UPDATED_ORDER_NUMBER)
            .deliveryNote(UPDATED_DELIVERY_NOTE)
            .grvNumber(UPDATED_GRV_NUMBER)
            .invoiceNumber(UPDATED_INVOICE_NUMBER)
            .pricePerLitre(UPDATED_PRICE_PER_LITRE)
            .note(UPDATED_NOTE)
            .registrationNumber(UPDATED_REGISTRATION_NUMBER)
            .attendeeId(UPDATED_ATTENDEE_ID)
            .operatorId(UPDATED_OPERATOR_ID);

        restFuelTransactionHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedFuelTransactionHeader.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedFuelTransactionHeader))
            )
            .andExpect(status().isOk());

        // Validate the FuelTransactionHeader in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertFuelTransactionHeaderUpdatableFieldsEquals(
            partialUpdatedFuelTransactionHeader,
            getPersistedFuelTransactionHeader(partialUpdatedFuelTransactionHeader)
        );
    }

    @Test
    @Transactional
    void patchNonExistingFuelTransactionHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionHeader.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restFuelTransactionHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, fuelTransactionHeader.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTransactionHeader))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchFuelTransactionHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionHeader.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionHeaderMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(fuelTransactionHeader))
            )
            .andExpect(status().isBadRequest());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamFuelTransactionHeader() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        fuelTransactionHeader.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restFuelTransactionHeaderMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(fuelTransactionHeader)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the FuelTransactionHeader in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteFuelTransactionHeader() throws Exception {
        // Initialize the database
        insertedFuelTransactionHeader = fuelTransactionHeaderRepository.saveAndFlush(fuelTransactionHeader);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the fuelTransactionHeader
        restFuelTransactionHeaderMockMvc
            .perform(delete(ENTITY_API_URL_ID, fuelTransactionHeader.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return fuelTransactionHeaderRepository.count();
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

    protected FuelTransactionHeader getPersistedFuelTransactionHeader(FuelTransactionHeader fuelTransactionHeader) {
        return fuelTransactionHeaderRepository.findById(fuelTransactionHeader.getId()).orElseThrow();
    }

    protected void assertPersistedFuelTransactionHeaderToMatchAllProperties(FuelTransactionHeader expectedFuelTransactionHeader) {
        assertFuelTransactionHeaderAllPropertiesEquals(
            expectedFuelTransactionHeader,
            getPersistedFuelTransactionHeader(expectedFuelTransactionHeader)
        );
    }

    protected void assertPersistedFuelTransactionHeaderToMatchUpdatableProperties(FuelTransactionHeader expectedFuelTransactionHeader) {
        assertFuelTransactionHeaderAllUpdatablePropertiesEquals(
            expectedFuelTransactionHeader,
            getPersistedFuelTransactionHeader(expectedFuelTransactionHeader)
        );
    }
}
