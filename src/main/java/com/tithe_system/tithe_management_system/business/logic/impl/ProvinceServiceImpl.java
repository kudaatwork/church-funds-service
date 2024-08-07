package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AssemblyServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.DistrictServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.ProvinceServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.RegionServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.ProvinceService;
import com.tithe_system.tithe_management_system.business.validations.api.ProvinceServiceValidator;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.District;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Province;
import com.tithe_system.tithe_management_system.domain.Region;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.DistrictRepository;
import com.tithe_system.tithe_management_system.repository.ProvinceRepository;
import com.tithe_system.tithe_management_system.repository.RegionRepository;
import com.tithe_system.tithe_management_system.repository.specification.ProvinceSpecification;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.dtos.DistrictDto;
import com.tithe_system.tithe_management_system.utils.dtos.ProvinceDto;
import com.tithe_system.tithe_management_system.utils.dtos.RegionDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.CreateProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.ProvinceMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.responses.ProvinceResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
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

public class ProvinceServiceImpl implements ProvinceService {

    private final ProvinceServiceValidator provinceServiceValidator;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final DistrictRepository districtRepository;
    private final AssemblyRepository assemblyRepository;
    private final ModelMapper modelMapper;
    private final ProvinceServiceAuditable provinceServiceAuditable;
    private final RegionServiceAuditable regionServiceAuditable;
    private final DistrictServiceAuditable districtServiceAuditable;
    private final AssemblyServiceAuditable assemblyServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;

    public ProvinceServiceImpl(ProvinceServiceValidator provinceServiceValidator, ProvinceRepository provinceRepository,
                               RegionRepository regionRepository, DistrictRepository districtRepository,
                               AssemblyRepository assemblyRepository, ModelMapper modelMapper, ProvinceServiceAuditable
                                       provinceServiceAuditable, RegionServiceAuditable regionServiceAuditable, DistrictServiceAuditable districtServiceAuditable, AssemblyServiceAuditable assemblyServiceAuditable, ApplicationMessagesService applicationMessagesService) {
        this.provinceServiceValidator = provinceServiceValidator;
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
        this.districtRepository = districtRepository;
        this.assemblyRepository = assemblyRepository;
        this.modelMapper = modelMapper;
        this.provinceServiceAuditable = provinceServiceAuditable;
        this.regionServiceAuditable = regionServiceAuditable;
        this.districtServiceAuditable = districtServiceAuditable;
        this.assemblyServiceAuditable = assemblyServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
    }

    @Override
    public ProvinceResponse create(CreateProvinceRequest createProvinceRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = provinceServiceValidator.isRequestValidForCreation(createProvinceRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_PROVINCE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                createProvinceRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByNameAndEntityStatusNot(
                createProvinceRequest.getName(), EntityStatus.DELETED);

        if (provinceRetrieved.isPresent()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Province provinceToBeSaved = modelMapper.map(createProvinceRequest, Province.class);
        provinceToBeSaved.setRegion(regionRetrieved.get());

        Province provinceSaved = provinceServiceAuditable.create(provinceToBeSaved, locale, username);

        ProvinceDto provinceDtoReturned = modelMapper.map(provinceSaved, ProvinceDto.class);
        RegionDto regionDto = modelMapper.map(regionRetrieved.get(), RegionDto.class);
        provinceDtoReturned.setRegionDto(regionDto);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(201, true, message, provinceDtoReturned, null,
                null);
    }

    @Override
    public ProvinceResponse edit(EditProvinceRequest editProvinceRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = provinceServiceValidator.isRequestValidForEditing(editProvinceRequest);

        if(!isRequestValid){

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_EDIT_PROVINCE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                editProvinceRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(
                editProvinceRequest.getId(), EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Province provinceToBeEdited = provinceRetrieved.get();

        Optional<Province> checkForDuplicateProvince = provinceRepository.findByNameAndEntityStatusNot(
                editProvinceRequest.getName(), EntityStatus.DELETED);

        if (checkForDuplicateProvince.isPresent()) {

            if (!checkForDuplicateProvince.get().getId().equals(editProvinceRequest.getId())) {

                message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_ALREADY_EXISTS.getCode(),
                        new String[]{}, locale);

                return buildProvinceResponse(400, false, message, null,
                        null, null);
            }
        }

        provinceToBeEdited.setName(editProvinceRequest.getName());

        Province provinceEdited = provinceServiceAuditable.edit(provinceToBeEdited, locale, username);

        ProvinceDto provinceDtoReturned = modelMapper.map(provinceEdited, ProvinceDto.class);
        RegionDto regionDto = modelMapper.map(regionRetrieved.get(), RegionDto.class);
        provinceDtoReturned.setRegionDto(regionDto);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(201, true, message, provinceDtoReturned, null,
                null);
    }

    @Override
    public ProvinceResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = provinceServiceValidator.isIdValid(id);

        if (!isIdValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_PROVINCE_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);
            return buildProvinceResponse(404, false, message, null, null,
                    null);
        }

        Province provinceToBeDeleted = provinceRetrieved.get();
        provinceToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        provinceToBeDeleted.setName(provinceToBeDeleted.getName().replace(" ", "_") + "_" + LocalDateTime.now());

        List<District> districtsListToBeDeleted = new ArrayList<>();
        List<Assembly> assembliesListToBeDeleted = new ArrayList<>();

        List<District> districtsRetrieved = districtRepository.findByProvinceIdAndEntityStatusNot(provinceToBeDeleted.getId(),
                EntityStatus.DELETED);

        List<Assembly> assembliesRetrieved = assemblyRepository.findByProvinceIdAndEntityStatusNot(provinceToBeDeleted.getId(),
                EntityStatus.DELETED);

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

        Province provinceDeleted = provinceServiceAuditable.delete(provinceToBeDeleted, locale);

        List<District> districtListDeleted = districtServiceAuditable.deleteAll(districtsListToBeDeleted, locale);
        List<Assembly> assembliesListDeleted = assemblyServiceAuditable.deleteAll(assembliesListToBeDeleted, locale);

        ProvinceDto provinceDtoReturned = modelMapper.map(provinceDeleted, ProvinceDto.class);

        List<DistrictDto> districtDtoList = modelMapper.map(districtListDeleted, new TypeToken<List<DistrictDto>>(){}.getType());
        List<AssemblyDto> assemblyDtoList = modelMapper.map(assembliesListDeleted, new TypeToken<List<AssemblyDto>>(){}.getType());

        provinceDtoReturned.setDistrictDtoList(districtDtoList);
        provinceDtoReturned.setAssemblyDtoList(assemblyDtoList);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(200, true, message, provinceDtoReturned, null,
                null);
    }

    @Override
    public ProvinceResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = provinceServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_PROVINCE_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(404, false, message, null, null,
                    null);
        }

        Province provinceReturned = provinceRetrieved.get();

        ProvinceDto provinceDto = modelMapper.map(provinceReturned, ProvinceDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(200, true, message, provinceDto, null,
                null);
    }

    @Override
    public ProvinceResponse findByRegionId(Long id, Locale locale, int page, int size) {

        String message = "";

        final Pageable pageable = PageRequest.of(page, size);

        boolean isIdValid = provinceServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_REGION_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Page<Province> provincePage = provinceRepository.findByRegionIdAndEntityStatusNot(id, EntityStatus.DELETED,
                pageable);

        Page<ProvinceDto> provinceDtoPage = convertProvinceEntityToProvinceDto(provincePage);

        if(provincePage.getContent().isEmpty()){

            message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildProvinceResponse(404, false, message, null, null,
                    provinceDtoPage);
        }

        message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildProvinceResponse(200, true, message, null,
                null, provinceDtoPage);
    }

    @Override
    public ProvinceResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<Province> provinceList = provinceRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(provinceList.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildProvinceResponse(404, false, message, null,
                    null, null);
        }

        List<ProvinceDto> provinceDtoList = modelMapper.map(provinceList, new TypeToken<List<ProvinceDto>>(){}.getType());

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildProvinceResponse(200, true, message, null,
                provinceDtoList, null);
    }

    @Override
    public ProvinceResponse findByMultipleFilters(ProvinceMultipleFiltersRequest provinceMultipleFiltersRequest, Locale locale,
                                                  String username) {

        String message = "";

        Specification<Province> spec = null;
        spec = addToSpec(spec, ProvinceSpecification::deleted);

        boolean isRequestValid = provinceServiceValidator.isRequestValidToRetrieveProvincesByMultipleFilters(
                provinceMultipleFiltersRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(
                    I18Code.MESSAGE_INVALID_REGIONS_MULTIPLE_FILTERS_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildProvinceResponse(400, false, message,null, null,
                    null);
        }

        Pageable pageable = PageRequest.of(provinceMultipleFiltersRequest.getPage(),
                provinceMultipleFiltersRequest.getSize());

        boolean isSearchValueValid = provinceServiceValidator.isStringValid(provinceMultipleFiltersRequest.getSearchValue());

        if (isSearchValueValid) {

            spec = addToSpec(provinceMultipleFiltersRequest.getSearchValue(), spec, ProvinceSpecification::any);
        }

        Page<Province> result = provinceRepository.findAll(spec, pageable);

        if (result.getContent().isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildProvinceResponse(404, false, message,null, null,
                    null);
        }

        Page<ProvinceDto> provinceDtoPage = convertProvinceEntityToProvinceDto(result);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildProvinceResponse(200, true, message,null,
                null, provinceDtoPage);
    }

    private Specification<Province> addToSpec(Specification<Province> spec,
                                            Function<EntityStatus, Specification<Province>> predicateMethod) {
        Specification<Province> localSpec = Specification.where(predicateMethod.apply(EntityStatus.DELETED));
        spec = (spec == null) ? localSpec : spec.and(localSpec);
        return spec;
    }

    private Specification<Province> addToSpec(final String aString, Specification<Province> spec, Function<String,
            Specification<Province>> predicateMethod) {
        if (aString != null && !aString.isEmpty()) {
            Specification<Province> localSpec = Specification.where(predicateMethod.apply(aString));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private Page<ProvinceDto> convertProvinceEntityToProvinceDto(Page<Province> provincePage){

        List<Province> provinceList = provincePage.getContent();
        List<ProvinceDto> provinceDtoList = new ArrayList<>();

        for (Province province : provincePage) {

            ProvinceDto provinceDto = modelMapper.map(province, ProvinceDto.class);
            provinceDtoList.add(provinceDto);
        }

        int page = provincePage.getNumber();
        int size = provincePage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageableProvinces = PageRequest.of(page, size);

        return new PageImpl<ProvinceDto>(provinceDtoList, pageableProvinces, provincePage.getTotalElements());
    }

    private ProvinceResponse buildProvinceResponse(int statusCode, boolean isSuccess, String message,
                                                   ProvinceDto provinceDto, List<ProvinceDto> provinceDtoList,
                                                   Page<ProvinceDto> provinceDtoPage){

        ProvinceResponse provinceResponse = new ProvinceResponse();
        provinceResponse.setStatusCode(statusCode);
        provinceResponse.setSuccess(isSuccess);
        provinceResponse.setMessage(message);
        provinceResponse.setProvinceDto(provinceDto);
        provinceResponse.setProvinceDtoList(provinceDtoList);
        provinceResponse.setProvinceDtoPage(provinceDtoPage);

        return provinceResponse;

    }
}
