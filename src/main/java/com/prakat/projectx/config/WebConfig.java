package com.prakat.projectx.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.util.StdDateFormat;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

import java.util.List;

@Configuration
@EnableWebMvc
public class WebConfig implements  WebMvcConfigurer {

    @Override

    public void addCorsMappings(CorsRegistry registry) {

        registry.addMapping("/**");

    }


    @Override

    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        converters.add(new StringHttpMessageConverter());

        WebMvcConfigurer.super.configureMessageConverters(converters);

        converters.add(new MappingJackson2HttpMessageConverter(

                new Jackson2ObjectMapperBuilder()

                        .dateFormat(new StdDateFormat())

                        .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)

                        .build()));

    }

}