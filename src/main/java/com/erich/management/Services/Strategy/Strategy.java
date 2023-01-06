package com.erich.management.Services.Strategy;

import com.flickr4java.flickr.FlickrException;

import java.io.File;
import java.io.InputStream;

public interface Strategy<T> {

    T savePhoto(Long id, InputStream photo, String titulo) throws FlickrException;
}
