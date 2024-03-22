package com.sparta.hhztclone;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class HhztCloneApplication {

    public static void main(String[] args) {
        SpringApplication.run(HhztCloneApplication.class, args);
    }

}
