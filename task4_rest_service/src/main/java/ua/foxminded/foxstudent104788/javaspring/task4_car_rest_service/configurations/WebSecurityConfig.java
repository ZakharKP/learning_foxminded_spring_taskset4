package ua.foxminded.foxstudent104788.javaspring.task4_car_rest_service.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration @EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig {

    @Bean
    public SecurityFilterChain config(HttpSecurity httpSecurity) throws Exception {
	return httpSecurity.authorizeRequests()
		      .mvcMatchers(HttpMethod.GET, "/**").permitAll() 
		      .anyRequest()
		      .authenticated()
		      .and().cors()
		      .and().oauth2ResourceServer()
		      .jwt().and().and().build();

    }

    @Bean
    public PasswordEncoder passwordEncoder() {
	return new BCryptPasswordEncoder();
    }

}