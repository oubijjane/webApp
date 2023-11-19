package com.personal.project.webApp;

import com.personal.project.webApp.controller.WebConfig;
import com.personal.project.webApp.security.SecurityConfigs;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

@SpringBootTest
@ContextConfiguration(classes = {SecurityConfigs.class})
@AutoConfigureMockMvc
class WebAppApplicationTests {


}
