package com.skyvault05.remindme.config.security;

import com.skyvault05.remindme.config.security.filter.JwtAuthenticationFilter;
import com.skyvault05.remindme.config.security.handler.JwtAccessDeniedHandler;
import com.skyvault05.remindme.config.security.repository.CookieAuthorizationRequestRepository;
import com.skyvault05.remindme.utils.enums.UserRole;
import com.skyvault05.remindme.config.security.service.CustomOAuth2UserService;
import com.skyvault05.remindme.utils.exceptions.JwtAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;

@RequiredArgsConstructor
@EnableWebSecurity
public class SpringSecurity extends WebSecurityConfigurerAdapter {
    @Value("${frontEnd.Url}")
    private String frontEndUrl;
    private final CustomOAuth2UserService customOAuth2UserService;
    private final CookieAuthorizationRequestRepository cookieAuthorizationRequestRepository;
    private final AuthenticationSuccessHandler authenticationSuccessHandler;
    private final AuthenticationFailureHandler authenticationFailureHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Override
    @SuppressWarnings(value = "unchecked")
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/oauth2/**", "/auth/**").permitAll()
                .antMatchers("/", "/login").permitAll()
                .antMatchers("/api/v1/**").hasRole(UserRole.USER.name())
                .anyRequest().authenticated();

        http.cors()                     // CORS on
                .and()
                .csrf().disable()           // CSRF off
                .httpBasic().disable()      // Basic Auth off
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);    // Session off

        http.formLogin().disable()
                .oauth2Login()
                    .authorizationEndpoint()
                        .authorizationRequestRepository(cookieAuthorizationRequestRepository)
                    .and()
                    .userInfoEndpoint()
                        .userService(customOAuth2UserService)
                    .and()
                        .successHandler(authenticationSuccessHandler)
                        .failureHandler(authenticationFailureHandler);

        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)	// 401
                .accessDeniedHandler(jwtAccessDeniedHandler);		// 403

        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);
    }

    //Swagger 허용을 위한 uri 무시.
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring()
                .mvcMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

    //CORS 허용 적용
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.addAllowedOrigin("http://ec2-18-183-67-247.ap-northeast-1.compute.amazonaws.com:3000");
        configuration.setAllowedMethods(Arrays.asList("HEAD","POST","GET","DELETE","PUT"));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
}
