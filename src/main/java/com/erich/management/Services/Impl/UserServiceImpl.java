package com.erich.management.Services.Impl;

import com.erich.management.Config.SecurityConfig;
import com.erich.management.Dto.ChangeThePasswordUserDto;
import com.erich.management.Dto.UserDto;
import com.erich.management.Entity.User;
import com.erich.management.Exception.EntityNotFoundException;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidEntityException;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Repository.UserRepository;
import com.erich.management.Services.UserService;
import com.erich.management.Validators.UserValidator;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepo;

    private final SecurityConfig securityConfig;

    @Override
    public UserDto save(UserDto userDto) {
        List<String> errors = UserValidator.validate(userDto);
        if (!errors.isEmpty()) {
            throw new InvalidEntityException("", ErrorCodes.USER_NOT_VALID);
        }
        Optional<User> user = userRepo.findUserByEmail(userDto.getEmail());
        if (user.isPresent()) {
            throw new InvalidEntityException("There is already another user with the same email", ErrorCodes.USER_ALREADY_EXISTS,
                    Collections.singletonList("Another user with the same email already exists in the database"));
        }

        userDto.setPassword(securityConfig.passwordEncoder().encode(userDto.getPassword()));

        return UserDto.fromEntity(userRepo.save(UserDto.toEntity(userDto)));
    }

    @Override
    public UserDto findById(Long id) {
        if (id == null) {
            log.error("User ID is null");
            return null;
        }
        return userRepo.findById(id)
                .map(user -> UserDto.fromEntity(user))
                .orElseThrow(() -> new EntityNotFoundException("No user with id = " + id + " was found in the database", ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public List<UserDto> findAll() {
        return Streamable.of(userRepo.findAll())
                .stream()
                .map(user -> UserDto.fromEntity(user))
                .toList();
    }

    @Override
    public void delete(Long id) {
        if (id == null) {
            log.error("user ID is null");
            return;
        }
        userRepo.deleteById(id);

    }

    @Override
    public UserDto findByEmail(String email) {
        if (email == null) {
            log.error("UserEmail is NULL");
            return null;
        }
        return userRepo.findUserByEmail(email)
                .map(user -> UserDto.fromEntity(user))
                .orElseThrow(() -> new EntityNotFoundException("User with email not found", ErrorCodes.USER_NOT_FOUND));
    }

    @Override
    public UserDto changeThePassword(ChangeThePasswordUserDto password) {
        validate(password);
        Optional<User> userOptional = userRepo.findById(password.getId());
        if (userOptional.isEmpty()) {
            throw new EntityNotFoundException("No user with id found" + password.getId(), ErrorCodes.USER_NOT_VALID);
        }
        User user = userOptional.get();
        user.setPassword(securityConfig.passwordEncoder().encode(password.getPassword()));

        return UserDto.fromEntity(userRepo.save(user));
    }

    private void validate(ChangeThePasswordUserDto pass) {

        if (pass == null) {
            log.warn("Cannot change password with NULL object");
            throw new EntityNotFoundException("No information has been provided to change the password.", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (pass.getId() == null) {
            log.warn("Cannot change password with NULL ID");
            throw new EntityNotFoundException("null user id:: cannot change password.", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!StringUtils.hasLength(pass.getPassword()) || !StringUtils.hasLength(pass.getConfirmPassword())) {
            log.warn("Cannot change password with NULL password");
            throw new EntityNotFoundException("Null user password:: cannot change password", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }
        if (!pass.getPassword().equals(pass.getConfirmPassword())) {
            log.warn("Cannot change password with two different passwords");
            throw new InvalidOperationException("Invalid user passwords:: Cannot change password", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
        }

        /*switch (pass) {
            case ChangeThePasswordUserDto ignored && pass.getPassword() == null -> {
                log.warn("Cannot change password with NULL object");
                throw new EntityNotFoundException("No information has been provided to change the password.", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
            }
            case ChangeThePasswordUserDto ignored && pass.getId() == null -> {
                log.warn("Cannot change password with NULL ID");
                throw new EntityNotFoundException("null user id:: cannot change password.", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
            }
            case ChangeThePasswordUserDto ignored && (!StringUtils.hasLength(pass.getPassword()) || !StringUtils.hasLength(pass.getConfirmPassword()))-> {
                log.warn("Cannot change password with NULL password");
                throw new EntityNotFoundException("Null user password:: cannot change password", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
            }
            case ChangeThePasswordUserDto ignored && pass.getPassword().equals(pass.getConfirmPassword()) ->{
                log.warn("Cannot change password with two different passwords");
                throw new InvalidOperationException("Invalid user passwords:: Cannot change password", ErrorCodes.USER_CHANGE_PASSWORD_OBJECT_NOT_VALID);
            }
            default -> throw new IllegalStateException("Unexpected value: " + pass);
        }*/
    }

}
