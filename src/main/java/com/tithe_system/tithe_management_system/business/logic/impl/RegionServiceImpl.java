package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AssemblyServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.DistrictServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.ProvinceServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.RegionServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.RegionService;
import com.tithe_system.tithe_management_system.business.validations.api.RegionServiceValidator;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.District;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Province;
import com.tithe_system.tithe_management_system.domain.Region;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.DistrictRepository;
import com.tithe_system.tithe_management_system.repository.ProvinceRepository;
import com.tithe_system.tithe_management_system.repository.RegionRepository;
import com.tithe_system.tithe_management_system.repository.specification.AssemblySpecification;
import com.tithe_system.tithe_management_system.repository.specification.RegionSpecification;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.dtos.DistrictDto;
import com.tithe_system.tithe_management_system.utils.dtos.ProvinceDto;
import com.tithe_system.tithe_management_system.utils.dtos.RegionDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.CreateRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditRegionRequest;
import com.tithe_system.tithe_management_system.utils.requests.RegionMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
import com.tithe_system.tithe_management_system.utils.responses.RegionResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.Function;

public class RegionServiceImpl implements RegionService {
    private final RegionServiceValidator regionServiceValidator;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final AssemblyRepository assemblyRepository;
    private final ProvinceRepository provinceRepository;
    private final ModelMapper modelMapper;
    private final RegionServiceAuditable regionServiceAuditable;
    private final DistrictServiceAuditable districtServiceAuditable;
    private final AssemblyServiceAuditable assemblyServiceAuditable;
    private final ProvinceServiceAuditable provinceServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;

    public RegionServiceImpl(RegionServiceValidator regionServiceValidator, RegionRepository regionRepository,
                             DistrictRepository districtRepository, AssemblyRepository assemblyRepository, ProvinceRepository provinceRepository,
                             ModelMapper modelMapper, RegionServiceAuditable regionServiceAuditable,
                             DistrictServiceAuditable districtServiceAuditable, AssemblyServiceAuditable assemblyServiceAuditable, ProvinceServiceAuditable provinceServiceAuditable,
                             ApplicationMessagesService applicationMessagesService) {
        this.regionServiceValidator = regionServiceValidator;
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
        this.assemblyRepository = assemblyRepository;
        this.provinceRepository = provinceRepository;
        this.modelMapper = modelMapper;
        this.regionServiceAuditable = regionServiceAuditable;
        this.districtServiceAuditable = districtServiceAuditable;
        this.assemblyServiceAuditable = assemblyServiceAuditable;
        this.provinceServiceAuditable = provinceServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
    }

    @Override
    public RegionResponse create(CreateRegionRequest createRegionRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = regionServiceValidator.isRequestValidForCreation(createRegionRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_REGION_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildRegionResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByNameAndEntityStatusNot(
                createRegionRequest.getName(), EntityStatus.DELETED);

        if (regionRetrieved.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildRegionResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Region regionToBeSaved = modelMapper.map(createRegionRequest, Region.class);

        Region regionSaved = regionServiceAuditable.create(regionToBeSaved, locale, username);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RegionDto regionDtoReturned = modelMapper.map(regionSaved, RegionDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildRegionResponse(201, true, message, regionDtoReturned, null,
                null);
    }

    @Override
    public RegionResponse edit(EditRegionRequest editRegionRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = regionServiceValidator.isRequestValidForEditing(editRegionRequest);

        if(!isRequestValid){

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_EDIT_REGION_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildRegionResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                editRegionRequest.getId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildRegionResponse(400, false, message, null, null,
                    null);
        }

        Region regionToBeEdited = regionRetrieved.get();

        Optional<Region> checkForDuplicateRegion = regionRepository.findByNameAndEntityStatusNot(
                    editRegionRequest.getName(), EntityStatus.DELETED);

        if (checkForDuplicateRegion.isPresent()) {

            if (!checkForDuplicateRegion.get().getId().equals(editRegionRequest.getId())) {

                message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_ALREADY_EXISTS.getCode(),
                            new String[]{}, locale);

                    return buildRegionResponse(400, false, message, null,
                            null, null);
            }
        }

        regionToBeEdited.setName(editRegionRequest.getName());

        Region regionEdited = regionServiceAuditable.edit(regionToBeEdited, locale, username);

        RegionDto regionDtoReturned = modelMapper.map(regionEdited, RegionDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildRegionResponse(201, true, message, regionDtoReturned, null,
                null);
    }

    @Override
    public RegionResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = regionServiceValidator.isIdValid(id);

        if (!isIdValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_REGION_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildRegionResponse(400, false,    message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildRegionResponse(404, false, message, null, null,
                    null);
        }

        Region regionToBeDeleted = regionRetrieved.get();
        regionToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        regionToBeDeleted.setName(regionToBeDeleted.getName().replace(" ", "_") + "_" + LocalDateTime.now());

        List<Province> provincesListToBeDeleted = new ArrayList<>();
        List<District> districtsListToBeDeleted = new ArrayList<>();
        List<Assembly> assembliesListToBeDeleted = new ArrayList<>();

        List<Province> provincesRetrieved = provinceRepository.findByRegionIdAndEntityStatusNot(regionToBeDeleted.getId(),
                EntityStatus.DELETED);

        List<District> districtsRetrieved = districtRepository.findByRegionIdAndEntityStatusNot(regionToBeDeleted.getId(),
                EntityStatus.DELETED);

        List<Assembly> assembliesRetrieved = assemblyRepository.findByRegionIdAndEntityStatusNot(regionToBeDeleted.getId(),
                EntityStatus.DELETED);

        if (!provincesRetrieved.isEmpty()) {

            for (Province province: provincesRetrieved) {

                province.setEntityStatus(EntityStatus.DELETED);
                province.setName(province.getName().replace(" ", "_") + "_" + LocalDateTime.now());

                provincesListToBeDeleted.add(province);
            }
        }

        if (!districtsRetrieved.isEmpty()) {

            for (District district: districtsRetrieved) {

                district.setEntityStatus(EntityStatus.DELETED);
                district.setName(district.getName().replace(" ", "_") + "_" + LocalDateTime.now());

                districtsListToBeDeleted.add(district);
            }
        }

        if (!assembliesRetrieved.isEmpty()) {

            for (Assembly assembly: assembliesRetrieved) {

                assembly.setEntityStatus(EntityStatus.DELETED);
                assembly.setName(assembly.getName().replace(" ", "_") + "_" + LocalDateTime.now());

                assembliesListToBeDeleted.add(assembly);
            }
        }

        Region regionDeleted = regionServiceAuditable.delete(regionToBeDeleted, locale);

        List<Province> provinceListDeleted = provinceServiceAuditable.deleteAll(provincesListToBeDeleted, locale);
        List<District> districtListDeleted = districtServiceAuditable.deleteAll(districtsListToBeDeleted, locale);
        List<Assembly> assembliesListDeleted = assemblyServiceAuditable.deleteAll(assembliesListToBeDeleted, locale);

        RegionDto regionDtoReturned = modelMapper.map(regionDeleted, RegionDto.class);

        List<ProvinceDto> provinceDtoList = modelMapper.map(provinceListDeleted, new TypeToken<List<ProvinceDto>>(){}.getType());
        List<DistrictDto> districtDtoList = modelMapper.map(districtListDeleted, new TypeToken<List<DistrictDto>>(){}.getType());
        List<AssemblyDto> assemblyDtoList = modelMapper.map(assembliesListDeleted, new TypeToken<List<AssemblyDto>>(){}.getType());

        regionDtoReturned.setDistrictDtoList(districtDtoList);
        regionDtoReturned.setAssemblyDtoList(assemblyDtoList);
        regionDtoReturned.setProvinceDtoList(provinceDtoList);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildRegionResponse(200, true, message, regionDtoReturned, null,
                null);
    }

    @Override
    public RegionResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = regionServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_REGION_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildRegionResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildRegionResponse(404, false, message, null, null,
                    null);
        }

        Region regionReturned = regionRetrieved.get();

        RegionDto regionDto = modelMapper.map(regionReturned, RegionDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildRegionResponse(200, true, message, regionDto, null,
                null);
    }

    @Override
    public RegionResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<Region> regionList = regionRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(regionList.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildRegionResponse(404, false, message, null,
                    null, null);
        }

        List<RegionDto> regionDtoList = modelMapper.map(regionList, new TypeToken<List<RegionDto>>(){}.getType());

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildRegionResponse(200, true, message, null,
                regionDtoList, null);
    }

    @Override
    public RegionResponse findByMultipleFilters(RegionMultipleFiltersRequest regionMultipleFiltersRequest, Locale locale,
                                                  String username) {

        String message = "";

        Specification<Region> spec = null;
        spec = addToSpec(spec, RegionSpecification::deleted);

        boolean isRequestValid = regionServiceValidator.isRequestValidToRetrieveAssembliesByMultipleFilters(
                regionMultipleFiltersRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(
                    I18Code.MESSAGE_INVALID_REGIONS_MULTIPLE_FILTERS_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildRegionResponse(400, false, message,null, null,
                    null);
        }

        Pageable pageable = PageRequest.of(regionMultipleFiltersRequest.getPage(),
                regionMultipleFiltersRequest.getSize());

        boolean isSearchValueValid = regionServiceValidator.isStringValid(regionMultipleFiltersRequest.getSearchValue());

        if (isSearchValueValid) {

            spec = addToSpec(regionMultipleFiltersRequest.getSearchValue(), spec, RegionSpecification::any);
        }

        Page<Region> result = regionRepository.findAll(spec, pageable);

        if (result.getContent().isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildRegionResponse(404, false, message,null, null,
                    null);
        }

        Page<RegionDto> regionDtoPage = convertRegionEntityToRegionDto(result);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildRegionResponse(200, true, message,null,
                null, regionDtoPage);
    }

    private Specification<Region> addToSpec(Specification<Region> spec,
                                              Function<EntityStatus, Specification<Region>> predicateMethod) {
        Specification<Region> localSpec = Specification.where(predicateMethod.apply(EntityStatus.DELETED));
        spec = (spec == null) ? localSpec : spec.and(localSpec);
        return spec;
    }

    private Specification<Region> addToSpec(final String aString, Specification<Region> spec, Function<String,
            Specification<Region>> predicateMethod) {
        if (aString != null && !aString.isEmpty()) {
            Specification<Region> localSpec = Specification.where(predicateMethod.apply(aString));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Page<RegionDto> convertRegionEntityToRegionDto(Page<Region> regionPage){

        List<Region> regionList = regionPage.getContent();
        List<RegionDto> regionDtoList = new ArrayList<>();

        for (Region region : regionPage) {

            RegionDto regionDto = modelMapper.map(region, RegionDto.class);
            regionDtoList.add(regionDto);
        }

        int page = regionPage.getNumber();
        int size = regionPage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageableProvinces = PageRequest.of(page, size);

        return new PageImpl<RegionDto>(regionDtoList, pageableProvinces, regionPage.getTotalElements());
    }

    private RegionResponse buildRegionResponse(int statusCode, boolean isSuccess, String message,
                                                   RegionDto regionDto, List<RegionDto> regionDtoList,
                                                   Page<RegionDto> regionDtoPage){

        RegionResponse regionResponse = new RegionResponse();
        regionResponse.setStatusCode(statusCode);
        regionResponse.setSuccess(isSuccess);
        regionResponse.setMessage(message);
        regionResponse.setRegionDto(regionDto);
        regionResponse.setRegionDtoList(regionDtoList);
        regionResponse.setRegionDtoPage(regionDtoPage);

        return regionResponse;

    }
}
