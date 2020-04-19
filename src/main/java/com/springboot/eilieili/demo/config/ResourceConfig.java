package com.springboot.eilieili.demo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static com.springboot.eilieili.demo.common.Constants.*;

@Configuration
public class ResourceConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/pic/**").addResourceLocations("file:" + PIC_RESOURCE_PATH);
        registry.addResourceHandler("/video/**").addResourceLocations("file:"+ VIDEO_RESOURCE_PATH);
    }
}
