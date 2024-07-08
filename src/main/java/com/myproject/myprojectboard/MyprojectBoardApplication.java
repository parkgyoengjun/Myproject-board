package com.myproject.myprojectboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationPropertiesScan
@SpringBootApplication
public class MyprojectBoardApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyprojectBoardApplication.class, args);
    }

}
