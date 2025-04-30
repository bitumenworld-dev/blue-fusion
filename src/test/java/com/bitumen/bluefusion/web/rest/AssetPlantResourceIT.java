package com.bitumen.bluefusion.web.rest;

import static com.bitumen.bluefusion.domain.AssetPlantAsserts.*;
import static com.bitumen.bluefusion.web.rest.TestUtil.createUpdateProxyForBean;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.bitumen.bluefusion.IntegrationTest;
import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.domain.enumeration.ConsumptionUnit;
import com.bitumen.bluefusion.domain.enumeration.DriverOrOperator;
import com.bitumen.bluefusion.domain.enumeration.FuelType;
import com.bitumen.bluefusion.domain.enumeration.HorseOrTrailer;
import com.bitumen.bluefusion.domain.enumeration.PlantHoursStatus;
import com.bitumen.bluefusion.domain.enumeration.SMRReaderType;
import com.bitumen.bluefusion.repository.AssetPlantRepository;
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
 * Integration tests for the {@link AssetPlantResource} REST controller.
 */
@IntegrationTest
@AutoConfigureMockMvc
@WithMockUser
class AssetPlantResourceIT {

    private static final Long DEFAULT_ASSET_PLANT_ID = 1L;
    private static final Long UPDATED_ASSET_PLANT_ID = 2L;

    private static final String DEFAULT_FLEET_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_FLEET_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_NUMBER_PLATE = "AAAAAAAAAA";
    private static final String UPDATED_NUMBER_PLATE = "BBBBBBBBBB";

    private static final String DEFAULT_FLEET_DESCRIPTION = "AAAAAAAAAA";
    private static final String UPDATED_FLEET_DESCRIPTION = "BBBBBBBBBB";

    private static final Long DEFAULT_OWNER_ID = 1L;
    private static final Long UPDATED_OWNER_ID = 2L;

    private static final String DEFAULT_ACCESSIBLE_BY_COMPANY = "AAAAAAAAAA";
    private static final String UPDATED_ACCESSIBLE_BY_COMPANY = "BBBBBBBBBB";

    private static final DriverOrOperator DEFAULT_DRIVER_OR_OPERATOR = DriverOrOperator.DRIVER;
    private static final DriverOrOperator UPDATED_DRIVER_OR_OPERATOR = DriverOrOperator.OPERATOR;

    private static final Long DEFAULT_PLANT_CATEGORY_ID = 1L;
    private static final Long UPDATED_PLANT_CATEGORY_ID = 2L;

    private static final String DEFAULT_PLANT_SUBCATEGORY_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLANT_SUBCATEGORY_ID = "BBBBBBBBBB";

    private static final Long DEFAULT_MANUFACTURER_ID = 1L;
    private static final Long UPDATED_MANUFACTURER_ID = 2L;

    private static final Long DEFAULT_MODEL_ID = 1L;
    private static final Long UPDATED_MODEL_ID = 2L;

    private static final Integer DEFAULT_YEAR_OF_MANUFACTURE = 1;
    private static final Integer UPDATED_YEAR_OF_MANUFACTURE = 2;

    private static final String DEFAULT_COLOUR = "AAAAAAAAAA";
    private static final String UPDATED_COLOUR = "BBBBBBBBBB";

    private static final HorseOrTrailer DEFAULT_HORSE_OR_TRAILER = HorseOrTrailer.HORSE;
    private static final HorseOrTrailer UPDATED_HORSE_OR_TRAILER = HorseOrTrailer.TRAILER;

    private static final SMRReaderType DEFAULT_SMR_READER_TYPE = SMRReaderType.KILOMETER;
    private static final SMRReaderType UPDATED_SMR_READER_TYPE = SMRReaderType.HOUR;

    private static final Integer DEFAULT_CURRENT_SMR_INDEX = 1;
    private static final Integer UPDATED_CURRENT_SMR_INDEX = 2;

    private static final String DEFAULT_ENGINE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_ENGINE_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_ENGINE_CAPACITY_CC = "AAAAAAAAAA";
    private static final String UPDATED_ENGINE_CAPACITY_CC = "BBBBBBBBBB";

    private static final Long DEFAULT_CURRENT_SITE_ID = 1L;
    private static final Long UPDATED_CURRENT_SITE_ID = 2L;

    private static final Long DEFAULT_CURRENT_CONTRACT_ID = 1L;
    private static final Long UPDATED_CURRENT_CONTRACT_ID = 2L;

    private static final Long DEFAULT_CURRENT_OPERATOR_ID = 1L;
    private static final Long UPDATED_CURRENT_OPERATOR_ID = 2L;

    private static final String DEFAULT_LEDGER_CODE = "AAAAAAAAAA";
    private static final String UPDATED_LEDGER_CODE = "BBBBBBBBBB";

    private static final FuelType DEFAULT_FUEL_TYPE = FuelType.PETROL;
    private static final FuelType UPDATED_FUEL_TYPE = FuelType.DIESEL;

    private static final Float DEFAULT_TANK_CAPACITY_LITRES = 1F;
    private static final Float UPDATED_TANK_CAPACITY_LITRES = 2F;

    private static final ConsumptionUnit DEFAULT_CONSUMPTION_UNIT = ConsumptionUnit.KILOMETERS_PER_LITRE;
    private static final ConsumptionUnit UPDATED_CONSUMPTION_UNIT = ConsumptionUnit.LITRES_PER_HOUR;

    private static final PlantHoursStatus DEFAULT_PLANT_HOURS_STATUS = PlantHoursStatus.OPERATIONAL;
    private static final PlantHoursStatus UPDATED_PLANT_HOURS_STATUS = PlantHoursStatus.BREAK_DOWN;

    private static final Boolean DEFAULT_IS_PRIME_MOVER = false;
    private static final Boolean UPDATED_IS_PRIME_MOVER = true;

    private static final Float DEFAULT_CAPACITY_TONS = 1F;
    private static final Float UPDATED_CAPACITY_TONS = 2F;

    private static final Float DEFAULT_CAPACITY_M_3_LOOSE = 1F;
    private static final Float UPDATED_CAPACITY_M_3_LOOSE = 2F;

    private static final Float DEFAULT_CAPACITY_M_3_TIGHT = 1F;
    private static final Float UPDATED_CAPACITY_M_3_TIGHT = 2F;

    private static final Float DEFAULT_MAXIMUM_CONSUMPTION = 1F;
    private static final Float UPDATED_MAXIMUM_CONSUMPTION = 2F;

    private static final Float DEFAULT_MINIMUM_CONSUMPTION = 1F;
    private static final Float UPDATED_MINIMUM_CONSUMPTION = 2F;

    private static final Float DEFAULT_MAXIMUM_SMR_ON_FULL_TANK = 1F;
    private static final Float UPDATED_MAXIMUM_SMR_ON_FULL_TANK = 2F;

    private static final Boolean DEFAULT_TRACK_CONSUMPTION = false;
    private static final Boolean UPDATED_TRACK_CONSUMPTION = true;

    private static final Boolean DEFAULT_TRACK_SMR_READING = false;
    private static final Boolean UPDATED_TRACK_SMR_READING = true;

    private static final Boolean DEFAULT_TRACK_SERVICE = false;
    private static final Boolean UPDATED_TRACK_SERVICE = true;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final Boolean DEFAULT_REQUEST_WEEKLY_MILEAGE = false;
    private static final Boolean UPDATED_REQUEST_WEEKLY_MILEAGE = true;

    private static final Boolean DEFAULT_SENT = false;
    private static final Boolean UPDATED_SENT = true;

    private static final String DEFAULT_CHASSIS_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_CHASSIS_NUMBER = "BBBBBBBBBB";

    private static final String DEFAULT_CURRENT_LOCATION = "AAAAAAAAAA";
    private static final String UPDATED_CURRENT_LOCATION = "BBBBBBBBBB";

    private static final String ENTITY_API_URL = "/api/asset-plants";
    private static final String ENTITY_API_URL_ID = ENTITY_API_URL + "/{id}";

    private static Random random = new Random();
    private static AtomicLong longCount = new AtomicLong(random.nextInt() + (2 * Integer.MAX_VALUE));

    @Autowired
    private ObjectMapper om;

    @Autowired
    private AssetPlantRepository assetPlantRepository;

    @Autowired
    private EntityManager em;

    @Autowired
    private MockMvc restAssetPlantMockMvc;

    private AssetPlant assetPlant;

    private AssetPlant insertedAssetPlant;

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetPlant createEntity() {
        return new AssetPlant()
            .assetPlantId(DEFAULT_ASSET_PLANT_ID)
            .fleetNumber(DEFAULT_FLEET_NUMBER)
            .numberPlate(DEFAULT_NUMBER_PLATE)
            .fleetDescription(DEFAULT_FLEET_DESCRIPTION)
            .ownerId(DEFAULT_OWNER_ID)
            .accessibleByCompany(DEFAULT_ACCESSIBLE_BY_COMPANY)
            .driverOrOperator(DEFAULT_DRIVER_OR_OPERATOR)
            .plantCategoryId(DEFAULT_PLANT_CATEGORY_ID)
            .plantSubcategoryId(DEFAULT_PLANT_SUBCATEGORY_ID)
            .manufacturerId(DEFAULT_MANUFACTURER_ID)
            .modelId(DEFAULT_MODEL_ID)
            .yearOfManufacture(DEFAULT_YEAR_OF_MANUFACTURE)
            .colour(DEFAULT_COLOUR)
            .horseOrTrailer(DEFAULT_HORSE_OR_TRAILER)
            .smrReaderType(DEFAULT_SMR_READER_TYPE)
            .currentSmrIndex(DEFAULT_CURRENT_SMR_INDEX)
            .engineNumber(DEFAULT_ENGINE_NUMBER)
            .engineCapacityCc(DEFAULT_ENGINE_CAPACITY_CC)
            .currentSiteId(DEFAULT_CURRENT_SITE_ID)
            .currentContractId(DEFAULT_CURRENT_CONTRACT_ID)
            .currentOperatorId(DEFAULT_CURRENT_OPERATOR_ID)
            .ledgerCode(DEFAULT_LEDGER_CODE)
            .fuelType(DEFAULT_FUEL_TYPE)
            .tankCapacityLitres(DEFAULT_TANK_CAPACITY_LITRES)
            .consumptionUnit(DEFAULT_CONSUMPTION_UNIT)
            .plantHoursStatus(DEFAULT_PLANT_HOURS_STATUS)
            .isPrimeMover(DEFAULT_IS_PRIME_MOVER)
            .capacityTons(DEFAULT_CAPACITY_TONS)
            .capacityM3Loose(DEFAULT_CAPACITY_M_3_LOOSE)
            .capacityM3Tight(DEFAULT_CAPACITY_M_3_TIGHT)
            .maximumConsumption(DEFAULT_MAXIMUM_CONSUMPTION)
            .minimumConsumption(DEFAULT_MINIMUM_CONSUMPTION)
            .maximumSmrOnFullTank(DEFAULT_MAXIMUM_SMR_ON_FULL_TANK)
            .trackConsumption(DEFAULT_TRACK_CONSUMPTION)
            .trackSmrReading(DEFAULT_TRACK_SMR_READING)
            .trackService(DEFAULT_TRACK_SERVICE)
            .isDeleted(DEFAULT_IS_DELETED)
            .requestWeeklyMileage(DEFAULT_REQUEST_WEEKLY_MILEAGE)
            .sent(DEFAULT_SENT)
            .chassisNumber(DEFAULT_CHASSIS_NUMBER)
            .currentLocation(DEFAULT_CURRENT_LOCATION);
    }

    /**
     * Create an updated entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static AssetPlant createUpdatedEntity() {
        return new AssetPlant()
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .fleetNumber(UPDATED_FLEET_NUMBER)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .fleetDescription(UPDATED_FLEET_DESCRIPTION)
            .ownerId(UPDATED_OWNER_ID)
            .accessibleByCompany(UPDATED_ACCESSIBLE_BY_COMPANY)
            .driverOrOperator(UPDATED_DRIVER_OR_OPERATOR)
            .plantCategoryId(UPDATED_PLANT_CATEGORY_ID)
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .manufacturerId(UPDATED_MANUFACTURER_ID)
            .modelId(UPDATED_MODEL_ID)
            .yearOfManufacture(UPDATED_YEAR_OF_MANUFACTURE)
            .colour(UPDATED_COLOUR)
            .horseOrTrailer(UPDATED_HORSE_OR_TRAILER)
            .smrReaderType(UPDATED_SMR_READER_TYPE)
            .currentSmrIndex(UPDATED_CURRENT_SMR_INDEX)
            .engineNumber(UPDATED_ENGINE_NUMBER)
            .engineCapacityCc(UPDATED_ENGINE_CAPACITY_CC)
            .currentSiteId(UPDATED_CURRENT_SITE_ID)
            .currentContractId(UPDATED_CURRENT_CONTRACT_ID)
            .currentOperatorId(UPDATED_CURRENT_OPERATOR_ID)
            .ledgerCode(UPDATED_LEDGER_CODE)
            .fuelType(UPDATED_FUEL_TYPE)
            .tankCapacityLitres(UPDATED_TANK_CAPACITY_LITRES)
            .consumptionUnit(UPDATED_CONSUMPTION_UNIT)
            .plantHoursStatus(UPDATED_PLANT_HOURS_STATUS)
            .isPrimeMover(UPDATED_IS_PRIME_MOVER)
            .capacityTons(UPDATED_CAPACITY_TONS)
            .capacityM3Loose(UPDATED_CAPACITY_M_3_LOOSE)
            .capacityM3Tight(UPDATED_CAPACITY_M_3_TIGHT)
            .maximumConsumption(UPDATED_MAXIMUM_CONSUMPTION)
            .minimumConsumption(UPDATED_MINIMUM_CONSUMPTION)
            .maximumSmrOnFullTank(UPDATED_MAXIMUM_SMR_ON_FULL_TANK)
            .trackConsumption(UPDATED_TRACK_CONSUMPTION)
            .trackSmrReading(UPDATED_TRACK_SMR_READING)
            .trackService(UPDATED_TRACK_SERVICE)
            .isDeleted(UPDATED_IS_DELETED)
            .requestWeeklyMileage(UPDATED_REQUEST_WEEKLY_MILEAGE)
            .sent(UPDATED_SENT)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .currentLocation(UPDATED_CURRENT_LOCATION);
    }

    @BeforeEach
    void initTest() {
        assetPlant = createEntity();
    }

    @AfterEach
    void cleanup() {
        if (insertedAssetPlant != null) {
            assetPlantRepository.delete(insertedAssetPlant);
            insertedAssetPlant = null;
        }
    }

    @Test
    @Transactional
    void createAssetPlant() throws Exception {
        long databaseSizeBeforeCreate = getRepositoryCount();
        // Create the AssetPlant
        var returnedAssetPlant = om.readValue(
            restAssetPlantMockMvc
                .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlant)))
                .andExpect(status().isCreated())
                .andReturn()
                .getResponse()
                .getContentAsString(),
            AssetPlant.class
        );

        // Validate the AssetPlant in the database
        assertIncrementedRepositoryCount(databaseSizeBeforeCreate);
        assertAssetPlantUpdatableFieldsEquals(returnedAssetPlant, getPersistedAssetPlant(returnedAssetPlant));

        insertedAssetPlant = returnedAssetPlant;
    }

    @Test
    @Transactional
    void createAssetPlantWithExistingId() throws Exception {
        // Create the AssetPlant with an existing ID
        assetPlant.setId(1L);

        long databaseSizeBeforeCreate = getRepositoryCount();

        // An entity with an existing ID cannot be created, so this API call must fail
        restAssetPlantMockMvc
            .perform(post(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlant)))
            .andExpect(status().isBadRequest());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    void getAllAssetPlants() throws Exception {
        // Initialize the database
        insertedAssetPlant = assetPlantRepository.saveAndFlush(assetPlant);

        // Get all the assetPlantList
        restAssetPlantMockMvc
            .perform(get(ENTITY_API_URL + "?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(assetPlant.getId().intValue())))
            .andExpect(jsonPath("$.[*].assetPlantId").value(hasItem(DEFAULT_ASSET_PLANT_ID.intValue())))
            .andExpect(jsonPath("$.[*].fleetNumber").value(hasItem(DEFAULT_FLEET_NUMBER)))
            .andExpect(jsonPath("$.[*].numberPlate").value(hasItem(DEFAULT_NUMBER_PLATE)))
            .andExpect(jsonPath("$.[*].fleetDescription").value(hasItem(DEFAULT_FLEET_DESCRIPTION)))
            .andExpect(jsonPath("$.[*].ownerId").value(hasItem(DEFAULT_OWNER_ID.intValue())))
            .andExpect(jsonPath("$.[*].accessibleByCompany").value(hasItem(DEFAULT_ACCESSIBLE_BY_COMPANY)))
            .andExpect(jsonPath("$.[*].driverOrOperator").value(hasItem(DEFAULT_DRIVER_OR_OPERATOR.toString())))
            .andExpect(jsonPath("$.[*].plantCategoryId").value(hasItem(DEFAULT_PLANT_CATEGORY_ID.intValue())))
            .andExpect(jsonPath("$.[*].plantSubcategoryId").value(hasItem(DEFAULT_PLANT_SUBCATEGORY_ID)))
            .andExpect(jsonPath("$.[*].manufacturerId").value(hasItem(DEFAULT_MANUFACTURER_ID.intValue())))
            .andExpect(jsonPath("$.[*].modelId").value(hasItem(DEFAULT_MODEL_ID.intValue())))
            .andExpect(jsonPath("$.[*].yearOfManufacture").value(hasItem(DEFAULT_YEAR_OF_MANUFACTURE)))
            .andExpect(jsonPath("$.[*].colour").value(hasItem(DEFAULT_COLOUR)))
            .andExpect(jsonPath("$.[*].horseOrTrailer").value(hasItem(DEFAULT_HORSE_OR_TRAILER.toString())))
            .andExpect(jsonPath("$.[*].smrReaderType").value(hasItem(DEFAULT_SMR_READER_TYPE.toString())))
            .andExpect(jsonPath("$.[*].currentSmrIndex").value(hasItem(DEFAULT_CURRENT_SMR_INDEX)))
            .andExpect(jsonPath("$.[*].engineNumber").value(hasItem(DEFAULT_ENGINE_NUMBER)))
            .andExpect(jsonPath("$.[*].engineCapacityCc").value(hasItem(DEFAULT_ENGINE_CAPACITY_CC)))
            .andExpect(jsonPath("$.[*].currentSiteId").value(hasItem(DEFAULT_CURRENT_SITE_ID.intValue())))
            .andExpect(jsonPath("$.[*].currentContractId").value(hasItem(DEFAULT_CURRENT_CONTRACT_ID.intValue())))
            .andExpect(jsonPath("$.[*].currentOperatorId").value(hasItem(DEFAULT_CURRENT_OPERATOR_ID.intValue())))
            .andExpect(jsonPath("$.[*].ledgerCode").value(hasItem(DEFAULT_LEDGER_CODE)))
            .andExpect(jsonPath("$.[*].fuelType").value(hasItem(DEFAULT_FUEL_TYPE.toString())))
            .andExpect(jsonPath("$.[*].tankCapacityLitres").value(hasItem(DEFAULT_TANK_CAPACITY_LITRES.doubleValue())))
            .andExpect(jsonPath("$.[*].consumptionUnit").value(hasItem(DEFAULT_CONSUMPTION_UNIT.toString())))
            .andExpect(jsonPath("$.[*].plantHoursStatus").value(hasItem(DEFAULT_PLANT_HOURS_STATUS.toString())))
            .andExpect(jsonPath("$.[*].isPrimeMover").value(hasItem(DEFAULT_IS_PRIME_MOVER)))
            .andExpect(jsonPath("$.[*].capacityTons").value(hasItem(DEFAULT_CAPACITY_TONS.doubleValue())))
            .andExpect(jsonPath("$.[*].capacityM3Loose").value(hasItem(DEFAULT_CAPACITY_M_3_LOOSE.doubleValue())))
            .andExpect(jsonPath("$.[*].capacityM3Tight").value(hasItem(DEFAULT_CAPACITY_M_3_TIGHT.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumConsumption").value(hasItem(DEFAULT_MAXIMUM_CONSUMPTION.doubleValue())))
            .andExpect(jsonPath("$.[*].minimumConsumption").value(hasItem(DEFAULT_MINIMUM_CONSUMPTION.doubleValue())))
            .andExpect(jsonPath("$.[*].maximumSmrOnFullTank").value(hasItem(DEFAULT_MAXIMUM_SMR_ON_FULL_TANK.doubleValue())))
            .andExpect(jsonPath("$.[*].trackConsumption").value(hasItem(DEFAULT_TRACK_CONSUMPTION)))
            .andExpect(jsonPath("$.[*].trackSmrReading").value(hasItem(DEFAULT_TRACK_SMR_READING)))
            .andExpect(jsonPath("$.[*].trackService").value(hasItem(DEFAULT_TRACK_SERVICE)))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED)))
            .andExpect(jsonPath("$.[*].requestWeeklyMileage").value(hasItem(DEFAULT_REQUEST_WEEKLY_MILEAGE)))
            .andExpect(jsonPath("$.[*].sent").value(hasItem(DEFAULT_SENT)))
            .andExpect(jsonPath("$.[*].chassisNumber").value(hasItem(DEFAULT_CHASSIS_NUMBER)))
            .andExpect(jsonPath("$.[*].currentLocation").value(hasItem(DEFAULT_CURRENT_LOCATION)));
    }

    @Test
    @Transactional
    void getAssetPlant() throws Exception {
        // Initialize the database
        insertedAssetPlant = assetPlantRepository.saveAndFlush(assetPlant);

        // Get the assetPlant
        restAssetPlantMockMvc
            .perform(get(ENTITY_API_URL_ID, assetPlant.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
            .andExpect(jsonPath("$.id").value(assetPlant.getId().intValue()))
            .andExpect(jsonPath("$.assetPlantId").value(DEFAULT_ASSET_PLANT_ID.intValue()))
            .andExpect(jsonPath("$.fleetNumber").value(DEFAULT_FLEET_NUMBER))
            .andExpect(jsonPath("$.numberPlate").value(DEFAULT_NUMBER_PLATE))
            .andExpect(jsonPath("$.fleetDescription").value(DEFAULT_FLEET_DESCRIPTION))
            .andExpect(jsonPath("$.ownerId").value(DEFAULT_OWNER_ID.intValue()))
            .andExpect(jsonPath("$.accessibleByCompany").value(DEFAULT_ACCESSIBLE_BY_COMPANY))
            .andExpect(jsonPath("$.driverOrOperator").value(DEFAULT_DRIVER_OR_OPERATOR.toString()))
            .andExpect(jsonPath("$.plantCategoryId").value(DEFAULT_PLANT_CATEGORY_ID.intValue()))
            .andExpect(jsonPath("$.plantSubcategoryId").value(DEFAULT_PLANT_SUBCATEGORY_ID))
            .andExpect(jsonPath("$.manufacturerId").value(DEFAULT_MANUFACTURER_ID.intValue()))
            .andExpect(jsonPath("$.modelId").value(DEFAULT_MODEL_ID.intValue()))
            .andExpect(jsonPath("$.yearOfManufacture").value(DEFAULT_YEAR_OF_MANUFACTURE))
            .andExpect(jsonPath("$.colour").value(DEFAULT_COLOUR))
            .andExpect(jsonPath("$.horseOrTrailer").value(DEFAULT_HORSE_OR_TRAILER.toString()))
            .andExpect(jsonPath("$.smrReaderType").value(DEFAULT_SMR_READER_TYPE.toString()))
            .andExpect(jsonPath("$.currentSmrIndex").value(DEFAULT_CURRENT_SMR_INDEX))
            .andExpect(jsonPath("$.engineNumber").value(DEFAULT_ENGINE_NUMBER))
            .andExpect(jsonPath("$.engineCapacityCc").value(DEFAULT_ENGINE_CAPACITY_CC))
            .andExpect(jsonPath("$.currentSiteId").value(DEFAULT_CURRENT_SITE_ID.intValue()))
            .andExpect(jsonPath("$.currentContractId").value(DEFAULT_CURRENT_CONTRACT_ID.intValue()))
            .andExpect(jsonPath("$.currentOperatorId").value(DEFAULT_CURRENT_OPERATOR_ID.intValue()))
            .andExpect(jsonPath("$.ledgerCode").value(DEFAULT_LEDGER_CODE))
            .andExpect(jsonPath("$.fuelType").value(DEFAULT_FUEL_TYPE.toString()))
            .andExpect(jsonPath("$.tankCapacityLitres").value(DEFAULT_TANK_CAPACITY_LITRES.doubleValue()))
            .andExpect(jsonPath("$.consumptionUnit").value(DEFAULT_CONSUMPTION_UNIT.toString()))
            .andExpect(jsonPath("$.plantHoursStatus").value(DEFAULT_PLANT_HOURS_STATUS.toString()))
            .andExpect(jsonPath("$.isPrimeMover").value(DEFAULT_IS_PRIME_MOVER))
            .andExpect(jsonPath("$.capacityTons").value(DEFAULT_CAPACITY_TONS.doubleValue()))
            .andExpect(jsonPath("$.capacityM3Loose").value(DEFAULT_CAPACITY_M_3_LOOSE.doubleValue()))
            .andExpect(jsonPath("$.capacityM3Tight").value(DEFAULT_CAPACITY_M_3_TIGHT.doubleValue()))
            .andExpect(jsonPath("$.maximumConsumption").value(DEFAULT_MAXIMUM_CONSUMPTION.doubleValue()))
            .andExpect(jsonPath("$.minimumConsumption").value(DEFAULT_MINIMUM_CONSUMPTION.doubleValue()))
            .andExpect(jsonPath("$.maximumSmrOnFullTank").value(DEFAULT_MAXIMUM_SMR_ON_FULL_TANK.doubleValue()))
            .andExpect(jsonPath("$.trackConsumption").value(DEFAULT_TRACK_CONSUMPTION))
            .andExpect(jsonPath("$.trackSmrReading").value(DEFAULT_TRACK_SMR_READING))
            .andExpect(jsonPath("$.trackService").value(DEFAULT_TRACK_SERVICE))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED))
            .andExpect(jsonPath("$.requestWeeklyMileage").value(DEFAULT_REQUEST_WEEKLY_MILEAGE))
            .andExpect(jsonPath("$.sent").value(DEFAULT_SENT))
            .andExpect(jsonPath("$.chassisNumber").value(DEFAULT_CHASSIS_NUMBER))
            .andExpect(jsonPath("$.currentLocation").value(DEFAULT_CURRENT_LOCATION));
    }

    @Test
    @Transactional
    void getNonExistingAssetPlant() throws Exception {
        // Get the assetPlant
        restAssetPlantMockMvc.perform(get(ENTITY_API_URL_ID, Long.MAX_VALUE)).andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    void putExistingAssetPlant() throws Exception {
        // Initialize the database
        insertedAssetPlant = assetPlantRepository.saveAndFlush(assetPlant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlant
        AssetPlant updatedAssetPlant = assetPlantRepository.findById(assetPlant.getId()).orElseThrow();
        // Disconnect from session so that the updates on updatedAssetPlant are not directly saved in db
        em.detach(updatedAssetPlant);
        updatedAssetPlant
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .fleetNumber(UPDATED_FLEET_NUMBER)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .fleetDescription(UPDATED_FLEET_DESCRIPTION)
            .ownerId(UPDATED_OWNER_ID)
            .accessibleByCompany(UPDATED_ACCESSIBLE_BY_COMPANY)
            .driverOrOperator(UPDATED_DRIVER_OR_OPERATOR)
            .plantCategoryId(UPDATED_PLANT_CATEGORY_ID)
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .manufacturerId(UPDATED_MANUFACTURER_ID)
            .modelId(UPDATED_MODEL_ID)
            .yearOfManufacture(UPDATED_YEAR_OF_MANUFACTURE)
            .colour(UPDATED_COLOUR)
            .horseOrTrailer(UPDATED_HORSE_OR_TRAILER)
            .smrReaderType(UPDATED_SMR_READER_TYPE)
            .currentSmrIndex(UPDATED_CURRENT_SMR_INDEX)
            .engineNumber(UPDATED_ENGINE_NUMBER)
            .engineCapacityCc(UPDATED_ENGINE_CAPACITY_CC)
            .currentSiteId(UPDATED_CURRENT_SITE_ID)
            .currentContractId(UPDATED_CURRENT_CONTRACT_ID)
            .currentOperatorId(UPDATED_CURRENT_OPERATOR_ID)
            .ledgerCode(UPDATED_LEDGER_CODE)
            .fuelType(UPDATED_FUEL_TYPE)
            .tankCapacityLitres(UPDATED_TANK_CAPACITY_LITRES)
            .consumptionUnit(UPDATED_CONSUMPTION_UNIT)
            .plantHoursStatus(UPDATED_PLANT_HOURS_STATUS)
            .isPrimeMover(UPDATED_IS_PRIME_MOVER)
            .capacityTons(UPDATED_CAPACITY_TONS)
            .capacityM3Loose(UPDATED_CAPACITY_M_3_LOOSE)
            .capacityM3Tight(UPDATED_CAPACITY_M_3_TIGHT)
            .maximumConsumption(UPDATED_MAXIMUM_CONSUMPTION)
            .minimumConsumption(UPDATED_MINIMUM_CONSUMPTION)
            .maximumSmrOnFullTank(UPDATED_MAXIMUM_SMR_ON_FULL_TANK)
            .trackConsumption(UPDATED_TRACK_CONSUMPTION)
            .trackSmrReading(UPDATED_TRACK_SMR_READING)
            .trackService(UPDATED_TRACK_SERVICE)
            .isDeleted(UPDATED_IS_DELETED)
            .requestWeeklyMileage(UPDATED_REQUEST_WEEKLY_MILEAGE)
            .sent(UPDATED_SENT)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .currentLocation(UPDATED_CURRENT_LOCATION);

        restAssetPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, updatedAssetPlant.getId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(updatedAssetPlant))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertPersistedAssetPlantToMatchAllProperties(updatedAssetPlant);
    }

    @Test
    @Transactional
    void putNonExistingAssetPlant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, assetPlant.getId()).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithIdMismatchAssetPlant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantMockMvc
            .perform(
                put(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(om.writeValueAsBytes(assetPlant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void putWithMissingIdPathParamAssetPlant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantMockMvc
            .perform(put(ENTITY_API_URL).contentType(MediaType.APPLICATION_JSON).content(om.writeValueAsBytes(assetPlant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void partialUpdateAssetPlantWithPatch() throws Exception {
        // Initialize the database
        insertedAssetPlant = assetPlantRepository.saveAndFlush(assetPlant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlant using partial update
        AssetPlant partialUpdatedAssetPlant = new AssetPlant();
        partialUpdatedAssetPlant.setId(assetPlant.getId());

        partialUpdatedAssetPlant
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .fleetDescription(UPDATED_FLEET_DESCRIPTION)
            .accessibleByCompany(UPDATED_ACCESSIBLE_BY_COMPANY)
            .driverOrOperator(UPDATED_DRIVER_OR_OPERATOR)
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .horseOrTrailer(UPDATED_HORSE_OR_TRAILER)
            .smrReaderType(UPDATED_SMR_READER_TYPE)
            .currentSmrIndex(UPDATED_CURRENT_SMR_INDEX)
            .engineNumber(UPDATED_ENGINE_NUMBER)
            .capacityM3Tight(UPDATED_CAPACITY_M_3_TIGHT)
            .maximumConsumption(UPDATED_MAXIMUM_CONSUMPTION)
            .minimumConsumption(UPDATED_MINIMUM_CONSUMPTION)
            .maximumSmrOnFullTank(UPDATED_MAXIMUM_SMR_ON_FULL_TANK)
            .isDeleted(UPDATED_IS_DELETED)
            .requestWeeklyMileage(UPDATED_REQUEST_WEEKLY_MILEAGE)
            .sent(UPDATED_SENT)
            .chassisNumber(UPDATED_CHASSIS_NUMBER);

        restAssetPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetPlant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetPlant))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetPlantUpdatableFieldsEquals(
            createUpdateProxyForBean(partialUpdatedAssetPlant, assetPlant),
            getPersistedAssetPlant(assetPlant)
        );
    }

    @Test
    @Transactional
    void fullUpdateAssetPlantWithPatch() throws Exception {
        // Initialize the database
        insertedAssetPlant = assetPlantRepository.saveAndFlush(assetPlant);

        long databaseSizeBeforeUpdate = getRepositoryCount();

        // Update the assetPlant using partial update
        AssetPlant partialUpdatedAssetPlant = new AssetPlant();
        partialUpdatedAssetPlant.setId(assetPlant.getId());

        partialUpdatedAssetPlant
            .assetPlantId(UPDATED_ASSET_PLANT_ID)
            .fleetNumber(UPDATED_FLEET_NUMBER)
            .numberPlate(UPDATED_NUMBER_PLATE)
            .fleetDescription(UPDATED_FLEET_DESCRIPTION)
            .ownerId(UPDATED_OWNER_ID)
            .accessibleByCompany(UPDATED_ACCESSIBLE_BY_COMPANY)
            .driverOrOperator(UPDATED_DRIVER_OR_OPERATOR)
            .plantCategoryId(UPDATED_PLANT_CATEGORY_ID)
            .plantSubcategoryId(UPDATED_PLANT_SUBCATEGORY_ID)
            .manufacturerId(UPDATED_MANUFACTURER_ID)
            .modelId(UPDATED_MODEL_ID)
            .yearOfManufacture(UPDATED_YEAR_OF_MANUFACTURE)
            .colour(UPDATED_COLOUR)
            .horseOrTrailer(UPDATED_HORSE_OR_TRAILER)
            .smrReaderType(UPDATED_SMR_READER_TYPE)
            .currentSmrIndex(UPDATED_CURRENT_SMR_INDEX)
            .engineNumber(UPDATED_ENGINE_NUMBER)
            .engineCapacityCc(UPDATED_ENGINE_CAPACITY_CC)
            .currentSiteId(UPDATED_CURRENT_SITE_ID)
            .currentContractId(UPDATED_CURRENT_CONTRACT_ID)
            .currentOperatorId(UPDATED_CURRENT_OPERATOR_ID)
            .ledgerCode(UPDATED_LEDGER_CODE)
            .fuelType(UPDATED_FUEL_TYPE)
            .tankCapacityLitres(UPDATED_TANK_CAPACITY_LITRES)
            .consumptionUnit(UPDATED_CONSUMPTION_UNIT)
            .plantHoursStatus(UPDATED_PLANT_HOURS_STATUS)
            .isPrimeMover(UPDATED_IS_PRIME_MOVER)
            .capacityTons(UPDATED_CAPACITY_TONS)
            .capacityM3Loose(UPDATED_CAPACITY_M_3_LOOSE)
            .capacityM3Tight(UPDATED_CAPACITY_M_3_TIGHT)
            .maximumConsumption(UPDATED_MAXIMUM_CONSUMPTION)
            .minimumConsumption(UPDATED_MINIMUM_CONSUMPTION)
            .maximumSmrOnFullTank(UPDATED_MAXIMUM_SMR_ON_FULL_TANK)
            .trackConsumption(UPDATED_TRACK_CONSUMPTION)
            .trackSmrReading(UPDATED_TRACK_SMR_READING)
            .trackService(UPDATED_TRACK_SERVICE)
            .isDeleted(UPDATED_IS_DELETED)
            .requestWeeklyMileage(UPDATED_REQUEST_WEEKLY_MILEAGE)
            .sent(UPDATED_SENT)
            .chassisNumber(UPDATED_CHASSIS_NUMBER)
            .currentLocation(UPDATED_CURRENT_LOCATION);

        restAssetPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, partialUpdatedAssetPlant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(partialUpdatedAssetPlant))
            )
            .andExpect(status().isOk());

        // Validate the AssetPlant in the database

        assertSameRepositoryCount(databaseSizeBeforeUpdate);
        assertAssetPlantUpdatableFieldsEquals(partialUpdatedAssetPlant, getPersistedAssetPlant(partialUpdatedAssetPlant));
    }

    @Test
    @Transactional
    void patchNonExistingAssetPlant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlant.setId(longCount.incrementAndGet());

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restAssetPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, assetPlant.getId())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetPlant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithIdMismatchAssetPlant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantMockMvc
            .perform(
                patch(ENTITY_API_URL_ID, longCount.incrementAndGet())
                    .contentType("application/merge-patch+json")
                    .content(om.writeValueAsBytes(assetPlant))
            )
            .andExpect(status().isBadRequest());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void patchWithMissingIdPathParamAssetPlant() throws Exception {
        long databaseSizeBeforeUpdate = getRepositoryCount();
        assetPlant.setId(longCount.incrementAndGet());

        // If url ID doesn't match entity ID, it will throw BadRequestAlertException
        restAssetPlantMockMvc
            .perform(patch(ENTITY_API_URL).contentType("application/merge-patch+json").content(om.writeValueAsBytes(assetPlant)))
            .andExpect(status().isMethodNotAllowed());

        // Validate the AssetPlant in the database
        assertSameRepositoryCount(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    void deleteAssetPlant() throws Exception {
        // Initialize the database
        insertedAssetPlant = assetPlantRepository.saveAndFlush(assetPlant);

        long databaseSizeBeforeDelete = getRepositoryCount();

        // Delete the assetPlant
        restAssetPlantMockMvc
            .perform(delete(ENTITY_API_URL_ID, assetPlant.getId()).accept(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent());

        // Validate the database contains one less item
        assertDecrementedRepositoryCount(databaseSizeBeforeDelete);
    }

    protected long getRepositoryCount() {
        return assetPlantRepository.count();
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

    protected AssetPlant getPersistedAssetPlant(AssetPlant assetPlant) {
        return assetPlantRepository.findById(assetPlant.getId()).orElseThrow();
    }

    protected void assertPersistedAssetPlantToMatchAllProperties(AssetPlant expectedAssetPlant) {
        assertAssetPlantAllPropertiesEquals(expectedAssetPlant, getPersistedAssetPlant(expectedAssetPlant));
    }

    protected void assertPersistedAssetPlantToMatchUpdatableProperties(AssetPlant expectedAssetPlant) {
        assertAssetPlantAllUpdatablePropertiesEquals(expectedAssetPlant, getPersistedAssetPlant(expectedAssetPlant));
    }
}
