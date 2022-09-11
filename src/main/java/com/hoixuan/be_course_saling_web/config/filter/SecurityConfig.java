package com.hoixuan.be_course_saling_web.config.filter;


import com.hoixuan.be_course_saling_web.service.AppUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    AppUserService appUserService;

    @Autowired
    JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean(BeanIds.AUTHENTICATION_MANAGER)
    @Override
    public AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests().antMatchers("/login", "/register/**","/changePassword/**","/api/**","/socket/**","/chatroom/**","/admin/**", "/users/**","/instructors/**").permitAll()
                .and().authorizeRequests().antMatchers("/user/**","/course","/wallet","/lesson").hasAnyRole("ADMIN","USER")
                .and().authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN");


        http.authorizeRequests().antMatchers("/login","/register").permitAll()
                .and().authorizeRequests().antMatchers("/user/**","/course","/wallet","/lesson","/bill").hasAnyRole("ADMIN","USER")
                .and().authorizeRequests().antMatchers("/admin/**","/bill").hasRole("ADMIN")

                .and().csrf().disable();
        http.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .exceptionHandling();
        http.cors().configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues());
    }

    // xắc thực
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(appUserService).passwordEncoder(NoOpPasswordEncoder.getInstance());
    }


}
