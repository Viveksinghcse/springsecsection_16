package com.viveksinghspringsecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;

@SpringBootApplication
@EnableWebSecurity
@EnableMethodSecurity(proxyTargetClass = true,jsr250Enabled = true,securedEnabled = true)
/* Below two Annotations are   optional here all because everything is inside the main package and if the package of entity and repository is different then we need to mention this */
//@EntityScan("package com.viveksinghspringsecurity.model;")
//@EnableJpaRepositories("package com.viveksinghspringsecurity.repository;")
public class BankBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(BankBackendApplication.class, args);
    }

}
