package com.tithe_system.tithe_management_system.business.logic.impl;

import com.tithe_system.tithe_management_system.business.auditables.api.AccountServiceAuditable;
import com.tithe_system.tithe_management_system.business.auditables.api.AssemblyServiceAuditable;
import com.tithe_system.tithe_management_system.business.logic.api.AccountService;
import com.tithe_system.tithe_management_system.business.logic.api.AssemblyService;
import com.tithe_system.tithe_management_system.business.validations.api.AssemblyServiceValidator;
import com.tithe_system.tithe_management_system.domain.Account;
import com.tithe_system.tithe_management_system.domain.Assembly;
import com.tithe_system.tithe_management_system.domain.Currency;
import com.tithe_system.tithe_management_system.domain.District;
import com.tithe_system.tithe_management_system.domain.EntityStatus;
import com.tithe_system.tithe_management_system.domain.Province;
import com.tithe_system.tithe_management_system.domain.Region;
import com.tithe_system.tithe_management_system.domain.UserAccount;
import com.tithe_system.tithe_management_system.repository.AccountRepository;
import com.tithe_system.tithe_management_system.repository.AssemblyRepository;
import com.tithe_system.tithe_management_system.repository.DistrictRepository;
import com.tithe_system.tithe_management_system.repository.ProvinceRepository;
import com.tithe_system.tithe_management_system.repository.RegionRepository;
import com.tithe_system.tithe_management_system.repository.UserAccountRepository;
import com.tithe_system.tithe_management_system.repository.specification.AccountSpecification;
import com.tithe_system.tithe_management_system.repository.specification.AssemblySpecification;
import com.tithe_system.tithe_management_system.utils.dtos.AccountDto;
import com.tithe_system.tithe_management_system.utils.dtos.AssemblyDto;
import com.tithe_system.tithe_management_system.utils.dtos.DistrictDto;
import com.tithe_system.tithe_management_system.utils.dtos.ProvinceDto;
import com.tithe_system.tithe_management_system.utils.dtos.RegionDto;
import com.tithe_system.tithe_management_system.utils.enums.I18Code;
import com.tithe_system.tithe_management_system.utils.i18.api.ApplicationMessagesService;
import com.tithe_system.tithe_management_system.utils.requests.AssemblyMultipleFiltersRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAccountRequest;
import com.tithe_system.tithe_management_system.utils.requests.CreateAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.requests.EditAssemblyRequest;
import com.tithe_system.tithe_management_system.utils.responses.AccountResponse;
import com.tithe_system.tithe_management_system.utils.responses.AssemblyResponse;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.modelmapper.convention.MatchingStrategies;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

public class AssemblyServiceImpl implements AssemblyService {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private final AssemblyServiceValidator assemblyServiceValidator;
    private final AssemblyRepository assemblyRepository;
    private final DistrictRepository districtRepository;
    private final ProvinceRepository provinceRepository;
    private final RegionRepository regionRepository;
    private final UserAccountRepository userAccountRepository;
    private final AccountRepository accountRepository;
    private final ModelMapper modelMapper;
    private final AssemblyServiceAuditable assemblyServiceAuditable;
    private final AccountServiceAuditable accountServiceAuditable;
    private final ApplicationMessagesService applicationMessagesService;
    private final AccountService accountService;

    public AssemblyServiceImpl(AssemblyServiceValidator assemblyServiceValidator, AssemblyRepository assemblyRepository,
                               DistrictRepository districtRepository, ProvinceRepository provinceRepository,
                               RegionRepository regionRepository, UserAccountRepository userAccountRepository, AccountRepository accountRepository, ModelMapper modelMapper, AssemblyServiceAuditable
                                       assemblyServiceAuditable, AccountServiceAuditable accountServiceAuditable, ApplicationMessagesService applicationMessagesService, AccountService accountService) {
        this.assemblyServiceValidator = assemblyServiceValidator;
        this.assemblyRepository = assemblyRepository;
        this.districtRepository = districtRepository;
        this.provinceRepository = provinceRepository;
        this.regionRepository = regionRepository;
        this.userAccountRepository = userAccountRepository;
        this.accountRepository = accountRepository;
        this.modelMapper = modelMapper;
        this.assemblyServiceAuditable = assemblyServiceAuditable;
        this.accountServiceAuditable = accountServiceAuditable;
        this.applicationMessagesService = applicationMessagesService;
        this.accountService = accountService;
    }

    @Override
    public AssemblyResponse create(CreateAssemblyRequest createAssemblyRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = assemblyServiceValidator.isRequestValidForCreation(createAssemblyRequest);

        if (!isRequestValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_CREATE_ASSEMBLY_INVALID_REQUEST.getCode(), new String[]{},
                    locale);

            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(
                createAssemblyRequest.getProvinceId(), EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                createAssemblyRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<District> districtRetrieved = districtRepository.findByIdAndEntityStatusNot(
                createAssemblyRequest.getDistrictId(), EntityStatus.DELETED);

        if (districtRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_DISTRICT_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByNameAndEntityStatusNot(
                createAssemblyRequest.getName(), EntityStatus.DELETED);

        if (assemblyRetrieved.isPresent()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_ALREADY_EXISTS.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        ProvinceDto provinceDto = modelMapper.map(provinceRetrieved.get(), ProvinceDto.class);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        RegionDto regionDto = modelMapper.map(regionRetrieved.get(), RegionDto.class);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        DistrictDto districtDto = modelMapper.map(districtRetrieved.get(), DistrictDto.class);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Assembly assemblyToBeSaved = modelMapper.map(createAssemblyRequest, Assembly.class);

        assemblyToBeSaved.setRegion(regionRetrieved.get());
        assemblyToBeSaved.setDistrict(districtRetrieved.get());
        assemblyToBeSaved.setProvince(provinceRetrieved.get());

        Assembly assemblySaved = assemblyServiceAuditable.create(assemblyToBeSaved, locale, username);

        CreateAccountRequest createUsdAccountRequest = buildCreateUsdAccountRequest(assemblySaved);

        logger.info("Incoming request to create a USD account : {}", createUsdAccountRequest);

        AccountResponse usdAccountResponse = accountService.createAccount(createUsdAccountRequest, username, locale);

        logger.info("Outgoing response after creating a USD account : {}", usdAccountResponse);

        CreateAccountRequest createLocalCurrencyAccountRequest = buildCreateLocalCurrencyAccountRequest(assemblySaved);

        logger.info("Incoming request to create a local currency account : {}", createLocalCurrencyAccountRequest);

        AccountResponse localCurrencyAccountResponse = accountService.createAccount(createLocalCurrencyAccountRequest, username, locale);

        logger.info("Outgoing response after creating a local currency account : {}", localCurrencyAccountResponse);

        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        AssemblyDto assemblyDtoReturned = modelMapper.map(assemblySaved, AssemblyDto.class);
        assemblyDtoReturned.setRegionDto(regionDto);
        assemblyDtoReturned.setProvinceDto(provinceDto);
        assemblyDtoReturned.setDistrictDto(districtDto);
        assemblyDtoReturned.setUsdAccountDto(usdAccountResponse.getAccountDto());
        assemblyDtoReturned.setLocalCurrencyAccountDto(localCurrencyAccountResponse.getAccountDto());

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_CREATED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAssemblyResponse(201, true, message, assemblyDtoReturned, null,
                null);
    }

    @Override
    public AssemblyResponse edit(EditAssemblyRequest editAssemblyRequest, String username, Locale locale) {

        String message = "";

        boolean isRequestValid = assemblyServiceValidator.isRequestValidForEditing(editAssemblyRequest);

        if(!isRequestValid){
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_EDIT_ASSEMBLY_INVALID_REQUEST.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Province> provinceRetrieved = provinceRepository.findByIdAndEntityStatusNot(
                editAssemblyRequest.getProvinceId(), EntityStatus.DELETED);

        if (provinceRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Region> regionRetrieved = regionRepository.findByIdAndEntityStatusNot(
                editAssemblyRequest.getRegionId(), EntityStatus.DELETED);

        if (regionRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_REGION_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<District> districtRetrieved = districtRepository.findByIdAndEntityStatusNot(
                editAssemblyRequest.getDistrictId(), EntityStatus.DELETED);

        if (districtRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_DISTRICT_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<UserAccount> userAccountRetrieved = userAccountRepository.findByIdAndEntityStatusNot(
                editAssemblyRequest.getUserAccountId(), EntityStatus.DELETED);

        if (userAccountRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_USER_ACCOUNT_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(
                editAssemblyRequest.getId(), EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Assembly assemblyToBeEdited = assemblyRetrieved.get();

        if (editAssemblyRequest.getName() != null) {

            Optional<Assembly> checkForDuplicateAssembly = assemblyRepository.findByNameAndEntityStatusNot(
                    editAssemblyRequest.getName(), EntityStatus.DELETED);

            if (checkForDuplicateAssembly.isPresent()) {

                if (!checkForDuplicateAssembly.get().getId().equals(editAssemblyRequest.getId())) {

                    message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_PROVINCE_ALREADY_EXISTS.getCode(),
                            new String[]{}, locale);

                    return buildAssemblyResponse(400, false, message, null,
                            null, null);
                }
            }

            assemblyToBeEdited.setName(editAssemblyRequest.getName());
        }

        if (editAssemblyRequest.getAddress() != null) {
            assemblyToBeEdited.setAddress(editAssemblyRequest.getAddress());
        }

        if (editAssemblyRequest.getContactPhoneNumber() != null) {
            assemblyToBeEdited.setContactPhoneNumber(editAssemblyRequest.getContactPhoneNumber());
        }

        if (editAssemblyRequest.getContactEmail() != null) {
            assemblyToBeEdited.setContactEmail(editAssemblyRequest.getContactEmail());
        }

        if (editAssemblyRequest.getProvinceId() != null) {
            assemblyToBeEdited.setProvince(provinceRetrieved.get());
        }

        if (editAssemblyRequest.getDistrictId() != null) {
            assemblyToBeEdited.setDistrict(districtRetrieved.get());
        }

        if (editAssemblyRequest.getRegionId() != null) {
            assemblyToBeEdited.setRegion(regionRetrieved.get());
        }

        Assembly assemblyEdited = assemblyServiceAuditable.edit(assemblyToBeEdited, locale, username);

        ProvinceDto provinceDto = modelMapper.map(provinceRetrieved.get(), ProvinceDto.class);

        RegionDto regionDto = modelMapper.map(regionRetrieved.get(), RegionDto.class);

        DistrictDto districtDto = modelMapper.map(districtRetrieved.get(), DistrictDto.class);

        AssemblyDto assemblyDtoReturned = modelMapper.map(assemblyEdited, AssemblyDto.class);
        assemblyDtoReturned.setProvinceDto(provinceDto);
        assemblyDtoReturned.setRegionDto(regionDto);
        assemblyDtoReturned.setDistrictDto(districtDto);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_EDITED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAssemblyResponse(201, true, message, assemblyDtoReturned, null,
                null);
    }

    @Override
    public AssemblyResponse delete(Long id, Locale locale) {

        String message = "";

        boolean isIdValid = assemblyServiceValidator.isIdValid(id);

        if (!isIdValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_ASSEMBLY_ID_SUPPLIED.getCode(), new String[]{},
                    locale);

            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_DOES_NOT_EXIST.getCode(), new String[]{},
                    locale);

            return buildAssemblyResponse(404, false, message, null, null,
                    null);
        }

        Assembly assemblyToBeDeleted = assemblyRetrieved.get();
        assemblyToBeDeleted.setEntityStatus(EntityStatus.DELETED);
        assemblyToBeDeleted.setName(assemblyToBeDeleted.getName().replace(" ", "_") + LocalDateTime.now());

        Optional<Account> accountRetrieved = accountRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        AccountDto accountDto = new AccountDto();

        if (!accountRetrieved.isEmpty()) {

            Account accountDeleted = accountServiceAuditable.delete(accountRetrieved.get(), locale);
            accountDto = modelMapper.map(accountDeleted, AccountDto.class);
        }

        Assembly assemblyDeleted = assemblyServiceAuditable.delete(assemblyToBeDeleted, locale);

        AssemblyDto assemblyDtoReturned = modelMapper.map(assemblyDeleted, AssemblyDto.class);
        //assemblyDtoReturned.setAccountDto(accountDto);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_DELETED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAssemblyResponse(200, true, message, assemblyDtoReturned, null,
                null);
    }

    @Override
    public AssemblyResponse findById(Long id, Locale locale) {
        String message = "";

        boolean isIdValid = assemblyServiceValidator.isIdValid(id);

        if(!isIdValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_ASSEMBLY_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Optional<Assembly> assemblyRetrieved = assemblyRepository.findByIdAndEntityStatusNot(id, EntityStatus.DELETED);

        if (assemblyRetrieved.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]{},
                    locale);
            return buildAssemblyResponse(404, false, message, null, null,
                    null);
        }

        Assembly assemblyReturned = assemblyRetrieved.get();

        AssemblyDto assemblyDto = modelMapper.map(assemblyReturned, AssemblyDto.class);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_RETRIEVED_SUCCESSFULLY.getCode(), new String[]{},
                locale);

        return buildAssemblyResponse(200, true, message, assemblyDto, null,
                null);
    }

    @Override
    public AssemblyResponse findByDistrictId(Long id, Locale locale, int page, int size) {

        String message = "";

        final Pageable pageable = PageRequest.of(page, size);

        boolean isIdValid = assemblyServiceValidator.isIdValid(id);

        if(!isIdValid) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_ASSEMBLY_ID_SUPPLIED.getCode(), new String[]
                    {}, locale);
            return buildAssemblyResponse(400, false, message, null, null,
                    null);
        }

        Page<Assembly> assemblyPage = assemblyRepository.findByDistrictIdAndEntityStatusNot(id, EntityStatus.DELETED,
                pageable);

        Page<AssemblyDto> assemblyDtoPage = convertAssemblyEntityToAssemblyDto(assemblyPage);

        if(assemblyPage.getContent().isEmpty()){
            message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildAssemblyResponse(404, false, message, null, null,
                    assemblyDtoPage);
        }

        message =  applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildAssemblyResponse(200, true, message, null,
                null, assemblyDtoPage);
    }

    @Override
    public AssemblyResponse findAllAsAList(String username, Locale locale) {

        String message = "";

        List<Assembly> assemblyList = assemblyRepository.findByEntityStatusNot(EntityStatus.DELETED);

        if(assemblyList.isEmpty()) {
            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(), new String[]
                    {}, locale);
            return buildAssemblyResponse(404, false, message, null,
                    null, null);
        }

        List<AssemblyDto> assemblyDtoList = modelMapper.map(assemblyList, new TypeToken<List<AssemblyDto>>(){}.getType());

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildAssemblyResponse(200, true, message, null,
                assemblyDtoList, null);
    }

    @Override
    public AssemblyResponse findByMultipleFilters(AssemblyMultipleFiltersRequest assemblyMultipleFiltersRequest, Locale locale, String username) {

        String message = "";

        Specification<Assembly> spec = null;
        spec = addToSpec(spec, AssemblySpecification::deleted);

        boolean isRequestValid = assemblyServiceValidator.isRequestValidToRetrieveAssembliesByMultipleFilters(
                assemblyMultipleFiltersRequest);

        if (!isRequestValid) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_INVALID_ASSEMBLIES_MULTIPLE_FILTERS_REQUEST.getCode(),
                    new String[]{}, locale);

            return buildAssemblyResponse(400, false, message,null, null,
                    null);
        }

        Pageable pageable = PageRequest.of(assemblyMultipleFiltersRequest.getPage(),
                assemblyMultipleFiltersRequest.getSize());

        boolean isNameValid = assemblyServiceValidator.isStringValid(assemblyMultipleFiltersRequest.getName());

        if (isNameValid) {

            spec = addToSpec(assemblyMultipleFiltersRequest.getName(), spec, AssemblySpecification::nameLike);
        }

        boolean isContactPhoneNumberValid =
                assemblyServiceValidator.isStringValid(assemblyMultipleFiltersRequest.getContactPhoneNumber());

        if (isContactPhoneNumberValid) {

            spec = addToSpec(assemblyMultipleFiltersRequest.getName(), spec, AssemblySpecification::contactPhoneNumberLike);
        }

        boolean isContactEmailValid =
                assemblyServiceValidator.isStringValid(assemblyMultipleFiltersRequest.getContactEmail());

        if (isContactEmailValid) {

            spec = addToSpec(assemblyMultipleFiltersRequest.getName(), spec, AssemblySpecification::contactEmailLike);
        }

        boolean isSearchValueValid = assemblyServiceValidator.isStringValid(assemblyMultipleFiltersRequest.getSearchValue());

        if (isSearchValueValid) {

            spec = addToSpec(assemblyMultipleFiltersRequest.getSearchValue(), spec, AssemblySpecification::any);
        }

        Page<Assembly> result = assemblyRepository.findAll(spec, pageable);

        if (result.getContent().isEmpty()) {

            message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_NOT_FOUND.getCode(),
                    new String[]{}, locale);

            return buildAssemblyResponse(404, false, message,null, null,
                    null);
        }

        Page<AssemblyDto> assemblyDtoPage = convertAssemblyEntityToAssemblyDto(result);

        message = applicationMessagesService.getApplicationMessage(I18Code.MESSAGE_ASSEMBLY_RETRIEVED_SUCCESSFULLY.getCode(),
                new String[]{}, locale);

        return buildAssemblyResponse(200, true, message,null,
                null, assemblyDtoPage);
    }

    private Specification<Assembly> addToSpec(Specification<Assembly> spec,
                                             Function<EntityStatus, Specification<Assembly>> predicateMethod) {
        Specification<Assembly> localSpec = Specification.where(predicateMethod.apply(EntityStatus.DELETED));
        spec = (spec == null) ? localSpec : spec.and(localSpec);
        return spec;
    }

    private Specification<Assembly> addToSpec(final String aString, Specification<Assembly> spec, Function<String,
            Specification<Assembly>> predicateMethod) {
        if (aString != null && !aString.isEmpty()) {
            Specification<Assembly> localSpec = Specification.where(predicateMethod.apply(aString));
            spec = (spec == null) ? localSpec : spec.and(localSpec);
            return spec;
        }
        return spec;
    }

    private static CreateAccountRequest buildCreateUsdAccountRequest(Assembly assemblySaved) {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAssemblyId(assemblySaved.getId());
        createAccountRequest.setCurrency(Currency.USD.getCurrency());
        createAccountRequest.setName(assemblySaved.getName());

        return createAccountRequest;
    }

    private static CreateAccountRequest buildCreateLocalCurrencyAccountRequest(Assembly assemblySaved) {

        CreateAccountRequest createAccountRequest = new CreateAccountRequest();
        createAccountRequest.setAssemblyId(assemblySaved.getId());
        createAccountRequest.setCurrency(Currency.ZIG.getCurrency());
        createAccountRequest.setName(assemblySaved.getName());

        return createAccountRequest;
    }

    private Page<AssemblyDto> convertAssemblyEntityToAssemblyDto(Page<Assembly> assemblyPage){

        List<Assembly> assemblyList = assemblyPage.getContent();
        List<AssemblyDto> assemblyDtoList = new ArrayList<>();

        for (Assembly assembly : assemblyPage) {
            AssemblyDto assemblyDto = modelMapper.map(assembly, AssemblyDto.class);
            assemblyDtoList.add(assemblyDto);
        }

        int page = assemblyPage.getNumber();
        int size = assemblyPage.getSize();

        size = size <= 0 ? 10 : size;

        Pageable pageableAssemblies = PageRequest.of(page, size);

        return new PageImpl<AssemblyDto>(assemblyDtoList, pageableAssemblies, assemblyPage.getTotalElements());
    }

    private AssemblyResponse buildAssemblyResponse(int statusCode, boolean isSuccess, String message,
                                                   AssemblyDto assemblyDto, List<AssemblyDto> assemblyDtoList,
                                                   Page<AssemblyDto> assemblyDtoPage){

        AssemblyResponse assemblyResponse = new AssemblyResponse();
        assemblyResponse.setStatusCode(statusCode);
        assemblyResponse.setSuccess(isSuccess);
        assemblyResponse.setMessage(message);
        assemblyResponse.setAssemblyDto(assemblyDto);
        assemblyResponse.setAssemblyDtoList(assemblyDtoList);
        assemblyResponse.setAssemblyDtoPage(assemblyDtoPage);

        return assemblyResponse;
    }
}
