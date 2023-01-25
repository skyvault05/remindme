package com.skyvault05.remindme.config.security;

import com.skyvault05.remindme.utils.enums.UserRole;
import com.skyvault05.remindme.config.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    @Value("${frontEndUrl}")
    private String frontEndUrl;
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable()
                .cors().configurationSource(corsConfigurationSource())
                .and()
                .csrf().disable()
                    .authorizeRequests()
                    .antMatchers("/", "/login").permitAll()
                    .antMatchers("/api/v1/**").hasRole(UserRole.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
//                        .defaultSuccessUrl("http://18.183.67.247:3000/", true)
                        .defaultSuccessUrl("http://localhost:3000/", true)
                            .userInfoEndpoint()
                                .userService(customOAuth2UserService);
    }

    //CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://18.183.67.247:3000");

        configuration.addAllowedHeader("Origin");
        configuration.addAllowedHeader("Accept");
        configuration.addAllowedHeader("X-Requested-With");
        configuration.addAllowedHeader("Content-Type");
        configuration.addAllowedHeader("Access-Control-Request-Method");
        configuration.addAllowedHeader("Access-Control-Request-Headers");
        configuration.addAllowedHeader("Authorization");

        configuration.addAllowedMethod("GET");
        configuration.addAllowedMethod("POST");
        configuration.addAllowedMethod("DELETE");

        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
