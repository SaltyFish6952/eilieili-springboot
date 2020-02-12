package com.springboot.eilieili.demo;

import com.springboot.eilieili.demo.JwtAuthorization.Audience;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties({Audience.class})
public class SpringBootEiliEiliApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootEiliEiliApplication.class, args);
    }

}
