package com.sparta.hhztclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class HhztCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhztCloneApplication.class, args);
    }

}
