package com.application;

import com.application.entity.RoleEntity;
import com.application.repository.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

    @Bean
    public CommandLineRunner runner(RoleRepository roleRepository)
    {
       return args -> {
           if (roleRepository.findByRoleName("USER").isEmpty())
           {
               roleRepository.save(RoleEntity.builder().roleName("USER").build()) ;
           }


       } ;
    }

}
