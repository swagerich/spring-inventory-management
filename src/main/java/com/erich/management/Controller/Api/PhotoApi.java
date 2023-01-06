package com.erich.management.Controller.Api;

import com.flickr4java.flickr.FlickrException;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.annotation.MultipartConfig;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static com.erich.management.Utils.Constants.Path.APP_ROOT;

@Tag(name = APP_ROOT + "/photos")
public interface PhotoApi {

    @PostMapping(value = APP_ROOT + "/photos/{id}/{title}/{context}")
    Object savePhoto(@PathVariable String context, @PathVariable Long id, @RequestPart(name = "file") MultipartFile photo, @PathVariable String title) throws FlickrException, IOException;
}
