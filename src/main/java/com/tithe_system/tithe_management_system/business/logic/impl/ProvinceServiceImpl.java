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
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.dtos.DistrictDto;
import com.tithe_system.tithe_management_system.utils.dtos.ProvinceDto;
import com.tithe_system.tithe_management_system.utils.dtos.RegionDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.CreateProvinceRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditProvinceRequest;
import com.tithe_system.tithe_management_system.utils.responses.ProvinceResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;

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

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_CREATE_PROVINCE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                createProvinceRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByNameAndEntityStatusNot(
                createProvinceRequest.getName(), EntityStatus.DELETED);

        if (provinceRetrieved.isPresent()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_ALREADY_EXISTS.getCode(), new String[]{},
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

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(201, true, message, provinceDtoReturned, null,
                null);
    }

    @Override
    public ProvinceResponse edit(EditProvinceRequest editProvinceRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = provinceServiceValidator.isRequestValidForEditing(editProvinceRequest);

        if(!isRequestValid){

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_EDIT_PROVINCE_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                editProvinceRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(
                editProvinceRequest.getId(), EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Province provinceToBeEdited = provinceRetrieved.get();

        Optional<Province> checkForDuplicateProvince = provinceRepository.findByNameAndEntityStatusNot(
                editProvinceRequest.getName(), EntityStatus.DELETED);

        if (checkForDuplicateProvince.isPresent()) {

            if (!checkForDuplicateProvince.get().getId().equals(editProvinceRequest.getId())) {

                message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_ALREADY_EXISTS.getCode(),
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

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(201, true, message, provinceDtoReturned, null,
                null);
    }

    @Override
    public ProvinceResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = provinceServiceValidator.isIdValid(id);

        if (!isIdValid) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_INVALID_PROVINCE_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);
            return buildProvinceResponse(404, false, message, null, null,
                    null);
        }

        Province provinceToBeDeleted = provinceRetrieved.get();
        provinceToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        provinceToBeDeleted.setName(provinceToBeDeleted.getName().replace(" ", "_") + LocalDateTime.now());

        List<District> districtsRetrieved = districtRepository.findByProvinceIdAndEntityStatusNot(provinceToBeDeleted.getId(),
                EntityStatus.DELETED);

        List<Assembly> assembliesRetrieved = assemblyRepository.findByProvinceIdAndEntityStatusNot(provinceToBeDeleted.getId(),
                EntityStatus.DELETED);

        Province provinceDeleted = provinceServiceAuditable.delete(provinceToBeDeleted, locale);

        List<District> districtListDeleted = districtServiceAuditable.deleteAll(districtsRetrieved, locale);
        List<Assembly> assembliesListDeleted = assemblyServiceAuditable.deleteAll(assembliesRetrieved, locale);

        ProvinceDto provinceDtoReturned = modelMapper.map(provinceDeleted, ProvinceDto.class);

        List<DistrictDto> districtDtoList = modelMapper.map(districtListDeleted, new TypeToken<List<DistrictDto>>(){}.getType());
        List<AssemblyDto> assemblyDtoList = modelMapper.map(assembliesListDeleted, new TypeToken<List<AssemblyDto>>(){}.getType());

        provinceDtoReturned.setDistrictDtoList(districtDtoList);
        provinceDtoReturned.setAssemblyDtoList(assemblyDtoList);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(200, true, message, provinceDtoReturned, null,
                null);
    }

    @Override
    public ProvinceResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = provinceServiceValidator.isIdValid(id);

        if(!isIdValid) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_INVALID_PROVINCE_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);

            return buildProvinceResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildProvinceResponse(404, false, message, null, null,
                    null);
        }

        Province provinceReturned = provinceRetrieved.get();

        ProvinceDto provinceDto = modelMapper.map(provinceReturned, ProvinceDto.class);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildProvinceResponse(200, true, message, provinceDto, null,
                null);
    }

    @Override
    public ProvinceResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<Province> provinceList = provinceRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(provinceList.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]
                    {}, locale);

            return buildProvinceResponse(404, false, message, null,
                    null, null);
        }

        List<ProvinceDto> provinceDtoList = modelMapper.map(provinceList, new TypeToken<List<ProvinceDto>>(){}.getType());

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildProvinceResponse(200, true, message, null,
                provinceDtoList, null);
    }

    @Override
    public ProvinceResponse findAllAsPages(int page, int size, Locale locale, String username) {

        String message ="";

        final Pageable pageable = PageRequest.of(page, size);

        Page<Province> provincePage = provinceRepository.findByEntityStatusNot(EntityStatus.DELETED, pageable);

        Page<ProvinceDto> provinceDtoPage = convertProvinceEntityToProvinceDto(provincePage);

        if(provincePage.getContent().isEmpty()){

            message =  applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildProvinceResponse(404, false, message, null, null,
                    provinceDtoPage);
        }

        message =  applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildProvinceResponse(200, true, message, null,
                null, provinceDtoPage);
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
