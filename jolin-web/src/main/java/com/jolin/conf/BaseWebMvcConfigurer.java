package com.jolin.conf;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.io.File;

@Component
public class BaseWebMvcConfigurer implements WebMvcConfigurer {

    public static String secondLevelDirectory = "FileStorageController";
    public static String secondLevelImgDirectory = "FileStorageControllerImg";
    public static String secondLevelImgUrl = "file/img";



    //Files can be accessed through urls
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String mImagesPath = "/d";
        registry.addResourceHandler("/" + secondLevelImgUrl + "/**").addResourceLocations("file:" + mImagesPath + secondLevelImgDirectory + File.separator);
    }
}
