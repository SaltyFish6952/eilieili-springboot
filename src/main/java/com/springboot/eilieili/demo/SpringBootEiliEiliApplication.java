package com.springboot.eilieili.demo;

import com.springboot.eilieili.demo.jwtAuthorization.Audience;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({Audience.class})
@Slf4j
public class SpringBootEiliEiliApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEiliEiliApplication.class, args);

    }

}
