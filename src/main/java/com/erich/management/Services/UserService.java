package com.erich.management.Services;

import com.erich.management.Dto.ChangeThePasswordUserDto;
import com.erich.management.Dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto save(UserDto userDto);

    UserDto findById(Long id);

    List<UserDto> findAll();

    void delete(Long id);

    UserDto findByEmail(String email);

    UserDto changeThePassword(ChangeThePasswordUserDto password);

}
