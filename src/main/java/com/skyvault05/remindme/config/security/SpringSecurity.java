package com.skyvault05.remindme.config.security;

import com.skyvault05.remindme.utils.enums.UserRole;
import com.skyvault05.remindme.config.security.service.CustomOAuth2UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    private final CustomOAuth2UserService customOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .headers().frameOptions().disable() //임시 비활성화
                .and()
                    .authorizeRequests()
                    .antMatchers("/", "/login").permitAll()
                    .antMatchers("/api/v1/**").hasRole(UserRole.USER.name())
                    .anyRequest().authenticated()
                .and()
                    .logout()
                        .logoutSuccessUrl("/")
                .and()
                    .oauth2Login()
                        .defaultSuccessUrl("http://localhost:3000/", true)
                            .userInfoEndpoint()
                                .userService(customOAuth2UserService);
    }
}
