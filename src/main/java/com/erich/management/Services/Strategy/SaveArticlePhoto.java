package com.erich.management.Services.Strategy;

import com.erich.management.Dto.ArticleDto;
import com.erich.management.Entity.Article;
import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidOperationException;
import com.erich.management.Services.ArticleService;
import com.erich.management.Services.FlickrService;
import com.flickr4java.flickr.FlickrException;
import lombok.extern.log4j.Log4j;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.io.InputStream;

@Service("articleStrategy")
@Slf4j
public class SaveArticlePhoto implements Strategy<ArticleDto> {

    private final FlickrService flickrService;
    private final ArticleService articleService;

    public SaveArticlePhoto(FlickrService flickrService, ArticleService articleService) {
        this.flickrService = flickrService;
        this.articleService = articleService;
    }

    @Override
    public ArticleDto savePhoto(Long id, InputStream photo, String titulo) throws FlickrException {

        ArticleDto articleDto = articleService.findById(id);
        String save = flickrService.savePhoto(photo, titulo);
        if (!StringUtils.hasLength(save)) {
            throw new InvalidOperationException("Error al guardar la foto del artículo", ErrorCodes.UPDATE_PHOTO_EXCEPTION);
        }
        articleDto.setPhoto(save);

        return articleService.save(articleDto);
    }
}
