package com.erich.management.Services.Strategy;

import com.erich.management.Dto.UserDto;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Services.FlickrService;
import com.erich.management.Services.UserService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Slf4j
@Service("userStrategy")
public class SaveUserPhoto implements Strategy<UserDto>{
    private final UserService userService;
    private final FlickrService flickrService;

    public SaveUserPhoto(UserService userService, FlickrService flickrService) {
        this.userService = userService;
        this.flickrService = flickrService;
    }

    @Override
    public UserDto savePhoto(Long id,InputStream photo, String titulo) throws FlickrException {
        UserDto userDto = userService.findById(id);
        String save = flickrService.savePhoto(photo, titulo);
        if(!StringUtils.hasLength(save)){
            throw new InvalidOperationException("Error al guardar la foto del User", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        userDto.setPhoto(save);
        return userService.save(userDto);
    }
}
