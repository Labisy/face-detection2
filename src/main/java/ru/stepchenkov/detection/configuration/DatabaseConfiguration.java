package ru.stepchenkov.detection.configuration;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@EntityScan(basePackages = {"ru.stepchenkov.detection.entity"})
public class DatabaseConfiguration {
}
