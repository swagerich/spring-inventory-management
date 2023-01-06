package com.erich.management.Services.Strategy;

import com.erich.management.Dto.EnterpriseDto;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Services.EnterpriseService;
import com.erich.management.Services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.io.InputStream;

@Service("enterpriseStrategy")
@Slf4j
public class SaveEnterprisePhoto implements Strategy<EnterpriseDto>{

    private final EnterpriseService enterpriseService;

    private final FlickrService flickrService;

    public SaveEnterprisePhoto(EnterpriseService enterpriseService, FlickrService flickrService) {
        this.enterpriseService = enterpriseService;
        this.flickrService = flickrService;
    }

    @Override
    public EnterpriseDto savePhoto(Long id,InputStream photo, String titulo) throws FlickrException {
        EnterpriseDto enterpriseDto = enterpriseService.findById(id);
        String save = flickrService.savePhoto(photo,titulo);
        if(!StringUtils.hasLength(save)){
            throw new InvalidOperationException("Error al guardar la foto de la empresa", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        enterpriseDto.setPhoto(save);
        return enterpriseService.save(enterpriseDto);
    }
}
