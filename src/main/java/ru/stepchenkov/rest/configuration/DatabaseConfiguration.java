package ru.stepchenkov.rest.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"ru.stepchenkov.rest.entity"})
public class DatabaseConfiguration {

}
