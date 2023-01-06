package com.erich.management.Controller;

import com.erich.management.Controller.Api.PhotoApi;
import com.erich.management.Services.Strategy.StrategyPhotoContext;
import com.flickr4java.flickr.FlickrException;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;


@RestController
public class PhotoController implements PhotoApi {

    private final StrategyPhotoContext strategyPhotoContext;

    public PhotoController(StrategyPhotoContext strategyPhotoContext) {
        this.strategyPhotoContext = strategyPhotoContext;
    }

    @Override
    public Object savePhoto(String context, Long id, MultipartFile photo, String title) throws FlickrException, IOException {
        return strategyPhotoContext.savePhoto(context,id,photo.getInputStream(),title);
    }
}
