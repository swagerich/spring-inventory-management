package com.erich.management.Services.Strategy;

import com.erich.management.Dto.ClientDto;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Services.ClientService;
import com.erich.management.Services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("clientStrategy")
@Slf4j
public class SaveClientPhoto implements Strategy<ClientDto> {

    private final ClientService clientService;
    private final FlickrService flickrService;

    public SaveClientPhoto(ClientService clientService, FlickrService flickrService) {
        this.clientService = clientService;
        this.flickrService = flickrService;
    }

    @Override
    public ClientDto savePhoto(Long id, InputStream photo, String titulo) throws FlickrException {
        ClientDto clientDto = clientService.findById(id);
        String save = flickrService.savePhoto(photo, titulo);
        if (!StringUtils.hasLength(save)) {
            log.error("Ocurrio un error!!!");
            throw new InvalidOperationException("Error al guardar la foto del Cliente", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        clientDto.setPhoto(save);
        return clientService.save(clientDto);
    }
}
