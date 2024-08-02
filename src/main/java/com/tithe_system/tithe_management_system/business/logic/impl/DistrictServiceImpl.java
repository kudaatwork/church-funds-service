package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AssemblyServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.DistrictServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.DistrictService;
import com.tithe_system.tithe_management_system.business.validations.api.DistrictServiceValidator;
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
import com.tithe_system.tithe_management_system.utils.requests.CreateDistrictRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditDistrictRequest;
import com.tithe_system.tithe_management_system.utils.responses.DistrictResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
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

public class DistrictServiceImpl implements DistrictService {
    private final DistrictServiceValidator districtServiceValidator;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final AssemblyRepository assemblyRepository;
    private final ModelMapper modelMapper;
    private final DistrictServiceAuditable districtServiceAuditable;
    private final AssemblyServiceAuditable assemblyServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;

    public DistrictServiceImpl(DistrictServiceValidator districtServiceValidator, DistrictRepository districtRepository, ProvinceRepository provinceRepository, RegionRepository regionRepository, AssemblyRepository assemblyRepository, ModelMapper modelMapper, DistrictServiceAuditable districtServiceAuditable, AssemblyServiceAuditable assemblyServiceAuditable, ApplicationMessagesService applicationMessagesService) {
        this.districtServiceValidator = districtServiceValidator;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
        this.assemblyRepository = assemblyRepository;
        this.modelMapper = modelMapper;
        this.districtServiceAuditable = districtServiceAuditable;
        this.assemblyServiceAuditable = assemblyServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
    }


    @Override
    public DistrictResponse create(CreateDistrictRequest createDistrictRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = districtServiceValidator.isRequestValidForCreation(createDistrictRequest);

        if (!isRequestValid) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_CREATE_DISTRICT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(
                createDistrictRequest.getProvinceId(), EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                createDistrictRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<District> districtRetrieved = districtRepository.findByNameAndEntityStatusNot(
                createDistrictRequest.getName(), EntityStatus.DELETED);

        if (districtRetrieved.isPresent()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        District districtToBeSaved = modelMapper.map(createDistrictRequest, District.class);
        districtToBeSaved.setProvince(provinceRetrieved.get());
        districtToBeSaved.setRegion(regionRetrieved.get());

        District districtSaved = districtServiceAuditable.create(districtToBeSaved, locale, username);

        ProvinceDto provinceDto = modelMapper.map(provinceRetrieved.get(), ProvinceDto.class);

        RegionDto regionDto = modelMapper.map(regionRetrieved.get(), RegionDto.class);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DistrictDto districtDtoReturned = modelMapper.map(districtSaved, DistrictDto.class);
        districtDtoReturned.setRegionDto(regionDto);
        districtDtoReturned.setProvinceDto(provinceDto);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildDistrictResponse(201, true, message, districtDtoReturned, null,
                null);
    }

    @Override
    public DistrictResponse edit(EditDistrictRequest editDistrictRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = districtServiceValidator.isRequestValidForEditing(editDistrictRequest);

        if(!isRequestValid){

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_EDIT_DISTRICT_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(
                editDistrictRequest.getProvinceId(), EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                editDistrictRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<District> districtRetrieved = districtRepository.findByIdAndEntityStatusNot(
                editDistrictRequest.getId(), EntityStatus.DELETED);

        if (districtRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        District districtToBeEdited = districtRetrieved.get();

        if (Objects.equals(districtToBeEdited.getId(), editDistrictRequest.getId()) &&
                Objects.equals(districtToBeEdited.getName().toLowerCase(), editDistrictRequest.getName().toLowerCase())) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null,
                    null, null);
        }
        else {

            districtToBeEdited.setName(editDistrictRequest.getName());
        }

        District districtEdited = districtServiceAuditable.edit(districtToBeEdited, locale, username);

        ProvinceDto provinceDto = modelMapper.map(provinceRetrieved.get(), ProvinceDto.class);

        RegionDto regionDto = modelMapper.map(regionRetrieved.get(), RegionDto.class);

        DistrictDto districtDtoReturned = modelMapper.map(districtEdited, DistrictDto.class);
        districtDtoReturned.setProvinceDto(provinceDto);
        districtDtoReturned.setRegionDto(regionDto);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildDistrictResponse(201, true, message, districtDtoReturned, null,
                null);
    }

    @Override
    public DistrictResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = districtServiceValidator.isIdValid(id);

        if (!isIdValid) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_INVALID_DISTRICT_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<District> districtRetrieved = districtRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (districtRetrieved.isEmpty()) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);
            return buildDistrictResponse(404, false, message, null, null,
                    null);
        }

        District districtToBeDeleted = districtRetrieved.get();
        districtToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        districtToBeDeleted.setName(districtToBeDeleted.getName().replace(" ", "_") + "_" + LocalDateTime.now());

        List<Assembly> assembliesListToBeDeleted = new ArrayList<>();

        List<Assembly> assembliesRetrieved = assemblyRepository.findByDistrictIdAndEntityStatusNot(districtToBeDeleted.getId(),
                EntityStatus.DELETED);

        if (!assembliesRetrieved.isEmpty()) {

            for (Assembly assembly: assembliesRetrieved) {

                assembly.setEntityStatus(EntityStatus.DELETED);
                assembly.setName(assembly.getName().replace(" ", "_") + "_" + LocalDateTime.now());

                assembliesListToBeDeleted.add(assembly);
            }
        }

        District districtDeleted = districtServiceAuditable.delete(districtToBeDeleted, locale);

        List<Assembly> assembliesListDeleted = assemblyServiceAuditable.deleteAll(assembliesListToBeDeleted, locale);

        DistrictDto districtDtoReturned = modelMapper.map(districtDeleted, DistrictDto.class);

        List<AssemblyDto> assemblyDtoList = modelMapper.map(assembliesListDeleted, new TypeToken<List<AssemblyDto>>(){}.getType());

        districtDtoReturned.setAssemblyDtoList(assemblyDtoList);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildDistrictResponse(200, true, message, districtDtoReturned, null,
                null);
    }

    @Override
    public DistrictResponse findById(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = districtServiceValidator.isIdValid(id);

        if(!isIdValid) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_INVALID_DISTRICT_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);
            return buildDistrictResponse(400, false, message, null, null,
                    null);
        }

        Optional<District> districtRetrieved = districtRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (districtRetrieved.isEmpty()) {

            message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_NOT_FOUND.getCode(), new String[]{},
                    locale);

            return buildDistrictResponse(404, false, message, null, null,
                    null);
        }

        District districtReturned = districtRetrieved.get();

        DistrictDto districtDto = modelMapper.map(districtReturned, DistrictDto.class);

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildDistrictResponse(200, true, message, districtDto, null,
                null);
    }

    @Override
    public DistrictResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<District> districtList = districtRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(districtList.isEmpty()) {
            message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_NOT_FOUND.getCode(), new String[]
                    {}, locale);
            return buildDistrictResponse(404, false, message, null,
                    null, null);
        }

        List<DistrictDto> districtDtoList = modelMapper.map(districtList, new TypeToken<List<DistrictDto>>(){}.getType());

        message = applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildDistrictResponse(200, true, message, null,
                districtDtoList, null);
    }

    @Override
    public DistrictResponse findAllAsPages(int page, int size, Locale locale, String username) {

        String message ="";

        final Pageable pageable = PageRequest.of(page, size);

        Page<District> districtPage = districtRepository.findByEntityStatusNot(EntityStatus.DELETED, pageable);

        Page<DistrictDto> districtDtoPage = convertDistrictEntityToDistrictDto(districtPage);

        if(districtPage.getContent().isEmpty()){

            message =  applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildDistrictResponse(404, false, message, null, null,
                    districtDtoPage);
        }

        message =  applicationMessagesService.getMessage(I18Code.MESSAGE_DISTRICT_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildDistrictResponse(200, true, message, null,
                null, districtDtoPage);
    }

    private Page<DistrictDto> convertDistrictEntityToDistrictDto(Page<District> districtPage){

        List<District> districtList = districtPage.getContent();
        List<DistrictDto> districtDtoList = new ArrayList<>();

        for (District district : districtPage) {

            DistrictDto districtDto = modelMapper.map(district, DistrictDto.class);
            districtDtoList.add(districtDto);
        }

        int page = districtPage.getNumber();
        int size = districtPage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageableDistricts = PageRequest.of(page, size);

        return new PageImpl<DistrictDto>(districtDtoList, pageableDistricts, districtPage.getTotalElements());
    }

    private DistrictResponse buildDistrictResponse(int statusCode, boolean isSuccess, String message,
                                                   DistrictDto districtDto, List<DistrictDto> districtDtoList,
                                                   Page<DistrictDto> districtDtoPage){

        DistrictResponse districtResponse = new DistrictResponse();
        districtResponse.setStatusCode(statusCode);
        districtResponse.setSuccess(isSuccess);
        districtResponse.setMessage(message);
        districtResponse.setDistrictDto(districtDto);
        districtResponse.setDistrictDtoList(districtDtoList);
        districtResponse.setDistrictDtoPage(districtDtoPage);

        return districtResponse;

    }
}
