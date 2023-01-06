package com.erich.management.Services.Strategy;

import com.erich.management.Dto.SupplierDto;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Services.FlickrService;
import com.erich.management.Services.SupplierService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.io.InputStream;

@Slf4j
@Service("supplierStrategy")
public class SaveSupplierPhoto implements Strategy<SupplierDto>{

    private final SupplierService supplierService;
    private final FlickrService flickrService;

    public SaveSupplierPhoto(SupplierService supplierService, FlickrService flickrService) {
        this.supplierService = supplierService;
        this.flickrService = flickrService;
    }

    @Override
    public SupplierDto savePhoto(Long id,InputStream photo, String titulo) throws FlickrException {
        SupplierDto supplierDto = supplierService.findById(id);
        String save = flickrService.savePhoto(photo, titulo);
        if(!StringUtils.hasLength(save)){
            throw new InvalidOperationException("Error al guardar la foto del proveedor", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        supplierDto.setPhoto(save);
        return supplierService.save(supplierDto);
    }
}
