package com.flasblog.back.cucumber;

import io.cucumber.junit.CucumberOptions;
import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.boot.test.context.SpringBootTest;

@CucumberContextConfiguration
@SpringBootTest(classes = org.openqa.selenium.grid.config.Config.class)
public class Config {}
