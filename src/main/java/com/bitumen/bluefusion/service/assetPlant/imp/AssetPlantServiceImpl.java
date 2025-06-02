package com.bitumen.bluefusion.service.assetPlant.imp;

import com.bitumen.bluefusion.domain.AssetPlant;
import com.bitumen.bluefusion.domain.Company;
import com.bitumen.bluefusion.domain.ContractDivision;
import com.bitumen.bluefusion.domain.Employee;
import com.bitumen.bluefusion.repository.AssetPlantRepository;
import com.bitumen.bluefusion.repository.CompanyRepository;
import com.bitumen.bluefusion.repository.ContractDivisionRepository;
import com.bitumen.bluefusion.repository.EmployeeRepository;
import com.bitumen.bluefusion.service.assetPlant.AssetPlantService;
import com.bitumen.bluefusion.service.assetPlant.dto.*;
import com.bitumen.bluefusion.service.exceptions.RecordNotFoundException;
import jakarta.mail.MethodNotSupportedException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class AssetPlantServiceImpl implements AssetPlantService {

    private final AssetPlantRepository assetPlantRepository;
    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;
    private final ContractDivisionRepository contractDivisionRepository;
    private final AssetPlantToAssetPlantResponseMapper assetPlantToAssetPlantResponseMapper;
    private final AssetPlantEntityResolver entityResolver;

    @Override
    public AssetPlantResponse save(AssetPlantRequest assetPlantRequest) {
        log.debug("Creating new asset plant with fleet number: {}", assetPlantRequest.fleetNumber());

        AssetPlantEntities entities = entityResolver.resolveEntitiesForRequest(assetPlantRequest);
        AssetPlant assetPlant = buildAssetPlant(assetPlantRequest, entities);

        assetPlant = assetPlantRepository.save(assetPlant);

        return assetPlantToAssetPlantResponseMapper.map(assetPlant);
    }

    @Override
    public AssetPlantResponse update(Long id, AssetPlantRequest assetPlantRequest) {
        log.debug("Updating asset plant with ID: {}", id);

        AssetPlant assetPlant = findAssetPlantById(id);
        AssetPlantEntities entities = entityResolver.resolveEntitiesForRequest(assetPlantRequest);

        updateAssetPlantFromRequest(assetPlant, assetPlantRequest, entities);
        assetPlant = assetPlantRepository.save(assetPlant);

        log.info("Successfully updated asset plant with ID: {}", id);
        return assetPlantToAssetPlantResponseMapper.map(assetPlant);
    }

    @Override
    public AssetPlantResponse partialUpdate(Long id, AssetPlantRequest assetPlantRequest) throws MethodNotSupportedException {
        throw new MethodNotSupportedException("Partial update method not yet supported");
    }

    @Override
    @Transactional(readOnly = true)
    public Page<AssetPlantResponse> findAll(Pageable pageable, AssetPlantFilterCriteria criteria) {
        log.debug("Finding asset plants with criteria: {}", criteria);

        AssetPlantEntities entities = entityResolver.resolveEntitiesForCriteria(criteria);
        Specification<AssetPlant> specification = buildSpecification(criteria, entities);

        Page<AssetPlant> assetPlants = assetPlantRepository.findAll(specification, pageable);
        log.debug("Found {} asset plants", assetPlants.getTotalElements());

        return assetPlants.map(assetPlantToAssetPlantResponseMapper::map);
    }

    @Override
    @Transactional(readOnly = true)
    public AssetPlantResponse findOne(Long id) {
        log.debug("Finding asset plant with ID: {}", id);

        return assetPlantRepository
            .findById(id)
            .map(assetPlantToAssetPlantResponseMapper::map)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Asset plant with id %d not found", id)));
    }

    @Override
    public AssetPlantResponse setCurrentOperator(Long assetPlantId, Long operatorId) {
        log.debug("Setting operator {} for asset plant {}", operatorId, assetPlantId);

        AssetPlant assetPlant = findAssetPlantById(assetPlantId);
        Employee operator = employeeRepository
            .findById(operatorId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Employee with id %d not found", operatorId)));

        assetPlant.setCurrentOperator(operator);
        assetPlant = assetPlantRepository.save(assetPlant);

        log.info("Successfully set operator {} for asset plant {}", operatorId, assetPlantId);
        return assetPlantToAssetPlantResponseMapper.map(assetPlant);
    }

    @Override
    public AssetPlantResponse setAccessibleByCompany(Long assetPlantId, Long accessibleByCompanyId) {
        log.debug("Adding company {} to accessible companies for asset plant {}", accessibleByCompanyId, assetPlantId);

        AssetPlant assetPlant = findAssetPlantById(assetPlantId);
        Company company = companyRepository
            .findById(accessibleByCompanyId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Company with id %d not found", accessibleByCompanyId)));

        assetPlant.getAccessibleByCompany().add(company);
        assetPlant = assetPlantRepository.save(assetPlant);

        log.info("Successfully added company {} to asset plant {}", accessibleByCompanyId, assetPlantId);
        return assetPlantToAssetPlantResponseMapper.map(assetPlant);
    }

    @Override
    public AssetPlantResponse setCurrentContractDivision(Long assetPlantId, Long contractDivisionId) {
        log.debug("Setting contract division {} for asset plant {}", contractDivisionId, assetPlantId);

        AssetPlant assetPlant = findAssetPlantById(assetPlantId);
        ContractDivision contractDivision = contractDivisionRepository
            .findById(contractDivisionId)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Contract division with id %d not found", contractDivisionId)));

        assetPlant.setCurrentContract(contractDivision);
        assetPlant = assetPlantRepository.save(assetPlant);

        log.info("Successfully set contract division {} for asset plant {}", contractDivisionId, assetPlantId);
        return assetPlantToAssetPlantResponseMapper.map(assetPlant);
    }

    @Override
    public void delete(Long id) {
        log.debug("Deleting asset plant with ID: {}", id);

        AssetPlant assetPlant = findAssetPlantById(id);
        assetPlantRepository.delete(assetPlant);

        log.info("Successfully deleted asset plant with ID: {}", id);
    }

    // Private helper methods
    private AssetPlant findAssetPlantById(Long id) {
        return assetPlantRepository
            .findById(id)
            .orElseThrow(() -> new RecordNotFoundException(String.format("Asset plant with id %d not found", id)));
    }

    private AssetPlant buildAssetPlant(AssetPlantRequest request, AssetPlantEntities entities) {
        return AssetPlant.builder()
            .make(entities.getMake())
            .model(entities.getMakeModel())
            .plantCategory(entities.getPlantCategory())
            .plantSubcategory(entities.getPlantSubcategory())
            .owner(entities.getCompany())
            .chassisNumber(request.chassisNumber())
            .colour(request.colour())
            .capacityM3Loose(request.capacityM3Loose())
            .capacityM3Tight(request.capacityM3Tight())
            .capacityTons(request.capacityTons())
            .currentSmrIndex(1)
            .engineCapacityCC(request.engineCapacityCC())
            .engineNumber(request.engineNumber())
            .fleetDescription(request.fleetDescription())
            .fleetNumber(request.fleetNumber())
            .fuelType(request.fuelType())
            .horseOrTrailer(request.horseOrTrailer())
            .driverOrOperator(request.driverOrOperator())
            .isPrimeMover(request.isPrimeMover())
            .ledgerCode(request.ledgerCode())
            .maximumConsumption(request.maximumConsumption())
            .maximumSmrOnFullTank(request.maximumSmrOnFullTank())
            .minimumConsumption(request.minimumConsumption())
            .requestWeeklyMileage(request.requestWeeklyMileage())
            .numberPlate(request.numberPlate())
            .smrReaderType(request.smrReaderType())
            .trackConsumption(request.trackConsumption())
            .trackSmrReading(request.trackSmrReading())
            .tankCapacityLitres(request.tankCapacityLitres())
            .yearOfManufacture(request.yearOfManufacture())
            .build();
    }

    private void updateAssetPlantFromRequest(AssetPlant assetPlant, AssetPlantRequest request, AssetPlantEntities entities) {
        assetPlant.setMake(entities.getMake());
        assetPlant.setModel(entities.getMakeModel());
        assetPlant.setPlantCategory(entities.getPlantCategory());
        assetPlant.setPlantSubcategory(entities.getPlantSubcategory());
        assetPlant.setOwner(entities.getCompany());
        assetPlant.setChassisNumber(request.chassisNumber());
        assetPlant.setColour(request.colour());
        assetPlant.setCapacityM3Loose(request.capacityM3Loose());
        assetPlant.setCapacityM3Tight(request.capacityM3Tight());
        assetPlant.setCapacityTons(request.capacityTons());
        assetPlant.setEngineCapacityCC(request.engineCapacityCC());
        assetPlant.setEngineNumber(request.engineNumber());
        assetPlant.setFleetDescription(request.fleetDescription());
        assetPlant.setFleetNumber(request.fleetNumber());
        assetPlant.setFuelType(request.fuelType());
        assetPlant.setHorseOrTrailer(request.horseOrTrailer());
        assetPlant.setDriverOrOperator(request.driverOrOperator());
        assetPlant.setIsPrimeMover(request.isPrimeMover());
        assetPlant.setLedgerCode(request.ledgerCode());
        assetPlant.setMaximumConsumption(request.maximumConsumption());
        assetPlant.setMaximumSmrOnFullTank(request.maximumSmrOnFullTank());
        assetPlant.setMinimumConsumption(request.minimumConsumption());
        assetPlant.setRequestWeeklyMileage(request.requestWeeklyMileage());
        assetPlant.setNumberPlate(request.numberPlate());
        assetPlant.setSmrReaderType(request.smrReaderType());
        assetPlant.setTrackConsumption(request.trackConsumption());
        assetPlant.setTrackSmrReading(request.trackSmrReading());
        assetPlant.setTankCapacityLitres(request.tankCapacityLitres());
        assetPlant.setYearOfManufacture(request.yearOfManufacture());
    }

    private Specification<AssetPlant> buildSpecification(AssetPlantFilterCriteria criteria, AssetPlantEntities entities) {
        return Specification.where(AssetPlantSpec.withFleetNumberLike(criteria.fleetNumber()))
            .and(AssetPlantSpec.withTrackConsumption(criteria.trackConsumption()))
            .and(AssetPlantSpec.withTrackSmrReading(criteria.trackSmrReading()))
            .and(AssetPlantSpec.withCompany(entities.getCompany()))
            .and(AssetPlantSpec.withMake(entities.getMake()))
            .and(AssetPlantSpec.withModel(entities.getMakeModel()))
            .and(AssetPlantSpec.withPlantCategory(entities.getPlantCategory()))
            .and(AssetPlantSpec.withPlantSubCategory(entities.getPlantSubcategory()));
    }
}
