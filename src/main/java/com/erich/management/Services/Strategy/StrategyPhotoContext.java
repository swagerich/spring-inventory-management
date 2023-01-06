package com.erich.management.Services.Strategy;

import com.erich.management.Exception.Enum.ErrorCodes;
import com.erich.management.Exception.InvalidOperationException;
import com.flickr4java.flickr.FlickrException;
import lombok.Setter;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

@Service
public class StrategyPhotoContext {


    private Strategy strategy;
    private final BeanFactory beanFactory;

    @Setter
    private String context;

    @Autowired
    public StrategyPhotoContext(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public Object savePhoto(String context, Long id, InputStream photo, String title) throws FlickrException {
        determinateContext(context);
        return strategy.savePhoto(id, photo, title);
    }

    private void determinateContext(String context) {
        final String beanName = context + "Strategy";
        switch (context) {
            case "article" -> strategy = beanFactory.getBean(beanName, SaveArticlePhoto.class);
            case "client" -> strategy = beanFactory.getBean(beanName, SaveClientPhoto.class);
            case "supplier" -> strategy = beanFactory.getBean(beanName, SaveSupplierPhoto.class);
            case "enterprise" -> strategy = beanFactory.getBean(beanName, SaveEnterprisePhoto.class);
            case "user" -> strategy = beanFactory.getBean(beanName, SaveUserPhoto.class);
            default -> throw new InvalidOperationException("Contexto Desconocido ,no se puede registrar la foto ", ErrorCodes.UNKNOWN_CONTEXT);
        }
    }
}
