package com.erich.management.Services.Impl;

import com.erich.management.Dto.EnterpriseDto;
import com.erich.management.Dto.RolesDto;
import com.erich.management.Dto.UserDto;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Repository.EnterpriseRepository;
import com.erich.management.Repository.RolesRepository;
import com.erich.management.Services.EnterpriseService;
import com.erich.management.Services.UserService;
import com.erich.management.Validators.EnterpriseValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class EnterpriseServiceImpl implements EnterpriseService {

    private final EnterpriseRepository enterpriseRepo;
    private final UserService userService;
    private final RolesRepository rolesRepo;

    @Override
    public EnterpriseDto save(EnterpriseDto enterpriseDto) {
        List<String> errors = EnterpriseValidator.validator(enterpriseDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("The company is invalid", ErrorCodes.ENTERPRISE_NOT_VALID);
        }
        return EnterpriseDto.fromEntity(enterpriseRepo.save(EnterpriseDto.toEntity(enterpriseDto)));

        /*EnterpriseDto saveEnterprise = EnterpriseDto.fromEntity(enterpriseRepo.save(EnterpriseDto.toEntity(enterpriseDto)));
        UserDto userDto = fromEnterprise(saveEnterprise);
        UserDto saveDto = userService.save(userDto);
        RolesDto rolesDto = RolesDto.builder()
                .rolesName("ROLE_ADMIN")
                .user(saveDto)
                .build();
        rolesRepo.save(RolesDto.toEntity(rolesDto));

        return saveEnterprise;*/
    }

    @Override
    public EnterpriseDto findById(Long id) {
        return enterpriseRepo.findById(id)
                .map(enterprise -> EnterpriseDto.fromEntity(enterprise))
                .orElseThrow(() -> new EntityNotFoundException("No company with ID = " + id + " was found in the database", ErrorCodes.ENTERPRISE_NOT_FOUND));
    }

    @Override
    public List<EnterpriseDto> findAll() {
        return Streamable.of(enterpriseRepo.findAll())
                .stream()
                .map(enterprise -> EnterpriseDto.fromEntity(enterprise))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("Enterprise ID  is null");
            return;
        }
        enterpriseRepo.deleteById(id);
    }

    private UserDto fromEnterprise(EnterpriseDto dto) {
        return UserDto.builder()
                .address(dto.getAddress())
                .name(dto.getName())
                .lastName(dto.getTaxCode())
                .email(dto.getEmail())
                .password(generateRandomPasswordEncryp())
                .enterprise(dto)
                .dateOfBirth(Instant.now())
                .photo(dto.getPhoto())
                .build();
    }

    private String generateRandomPasswordEncryp() {
        return "som3R@nd0mP@$$word";
    }
}
