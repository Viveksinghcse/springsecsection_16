package com.viveksinghspringsecurity.config;

import com.viveksinghspringsecurity.exceptionhandling.CustomAccessDeniedHandler;
import com.viveksinghspringsecurity.filter.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.csrf.CsrfTokenRequestAttributeHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@Profile("prod")
public class ProjectSecurityProdConfig {
    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
/*
        http.authorizeHttpRequests((requests) -> requests.anyRequest().authenticated());
        http.authorizeHttpRequests((requests) -> requests.anyRequest().permitAll());
        http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
*/
        CsrfTokenRequestAttributeHandler csrfTokenRequestAttributeHandler = new CsrfTokenRequestAttributeHandler();

        JwtAuthenticationConverter jwtAuthenticationConverter = new JwtAuthenticationConverter();
        jwtAuthenticationConverter.setJwtGrantedAuthoritiesConverter(new KeycloakRoleConverter());
//        http.securityContext(contextConfig -> contextConfig.requireExplicitSave(false));
        http.sessionManagement(sessionConfig -> sessionConfig.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http.cors(corsConfig -> corsConfig.configurationSource(new CorsConfigurationSource() {
            @Override
            public CorsConfiguration getCorsConfiguration(HttpServletRequest request) {
                CorsConfiguration config = new CorsConfiguration();
                config.setAllowedOrigins(Collections.singletonList("http://localhost:4200"));
                config.setAllowedMethods(Collections.singletonList("*"));
                config.setAllowCredentials(true);
                config.setAllowedHeaders(Collections.singletonList("*"));
                config.setExposedHeaders(Arrays.asList("Authorization"));
                config.setMaxAge(3600L);
                return config;
            }
        }));

        http.csrf(csrfConfig -> csrfConfig.csrfTokenRequestHandler(csrfTokenRequestAttributeHandler)
                        .ignoringRequestMatchers("/contact", "/register")
                        .csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()))
                .addFilterAfter(new CsrfCookieFilter(), BasicAuthenticationFilter.class);
        http.requiresChannel(rcc -> rcc.anyRequest().requiresSecure());
        //http.sessionManagement(smc-> smc.invalidSessionUrl("/invalidSession").maximumSessions(1).maxSessionsPreventsLogin(true));

        // http.csrf((csrfConfig)-> csrfConfig.disable());
        http.authorizeHttpRequests((requests) -> requests
//                .requestMatchers("/myAccount").hasAuthority("VIEWACCOUNT")
//                .requestMatchers( "/myBalance").hasAnyAuthority("VIEWBALANCE","VIEWACCOUNT")
//                .requestMatchers( "/myCards").hasAuthority("VIEWCARDS")
//                .requestMatchers( "/myLoans").hasAuthority("VIEWLOANS")
//                .requestMatchers("/user").authenticated()
                .requestMatchers("/myAccount").hasRole("USER")
                .requestMatchers( "/myBalance").hasAnyRole("USER","ADMIN")
                .requestMatchers( "/myCards").hasRole("USER")
                .requestMatchers( "/myLoans").authenticated()
                .requestMatchers("/user").authenticated()
                .requestMatchers("/contact", "/notices", "/error", "/register" ).permitAll());
        http.authorizeHttpRequests((requests) -> requests.anyRequest().denyAll());
        http.oauth2ResourceServer(rsc->
                rsc.jwt(jwtConfigurer -> jwtConfigurer.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
//        http.formLogin(flc-> flc.disable());
//        http.httpBasic(hbc->hbc.disable());
        return http.build();
    }


}

