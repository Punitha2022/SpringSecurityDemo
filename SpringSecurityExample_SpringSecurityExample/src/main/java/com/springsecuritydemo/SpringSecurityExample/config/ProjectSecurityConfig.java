package com.springsecuritydemo.SpringSecurityExample.config;

import static org.springframework.security.config.Customizer.withDefaults;

import java.io.IOException;
import java.net.URI;
import java.util.Arrays;
import java.util.Collections;

import javax.sql.DataSource;


import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;

import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.cors.CorsConfigurationSource;


@Configuration
@EnableWebSecurity
public class ProjectSecurityConfig {
//	@Bean
//	public FilterRegistrationBean corsFilter() {
//		UrlBasedCorsConfigurationSource source=new UrlBasedCorsConfigurationSource();
//		CorsConfiguration config=new CorsConfiguration();
//		config.setAllowCredentials(true);
//		config.addAllowedOrigin("http://localhost:3000");
//		config.setAllowedHeaders(Collections.singletonList("*"));
//		config.setAllowedMethods(Collections.singletonList("*"));
//		config.setMaxAge(3600L);
//		source.registerCorsConfiguration("/**", config);
//		//source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean bean=new FilterRegistrationBean(new CorsFilter(source));
//		return bean;
//	}
	//private SecurityContextRepository repo=new HttpSessionSecurityContextRepository();
	@Bean
	SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
		// cross site Request Forgery
		
		http.authorizeHttpRequests((requests) -> {
		requests
				.requestMatchers("/public/**").permitAll()
				.requestMatchers("/product/allproducts").permitAll()
				.requestMatchers("/product/{id}").permitAll()
				.requestMatchers(HttpMethod.OPTIONS).permitAll()
				.requestMatchers("/logoutsuccess").hasAnyAuthority("Admin","User")
				.requestMatchers("/product/**").hasAuthority("Admin");
		        
			// .and().csrf().disable();

		});
		http.csrf((csrf) -> csrf.disable());
		http.formLogin(withDefaults());
		http.httpBasic(withDefaults());
		return http.build();
	}

//	@Bean
//	public UserDetailsService userDetailsService(DataSource datasource) {
//		InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
//	manager.createUser(User.withDefaultPasswordEncoder()
//				.username("john").password("123").roles("USER").build());
//		return manager;
//		return new JdbcUserDetailsManager(datasource);
//	}
	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder();
		// return NoOpPasswordEncoder.getInstance();
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
		return configuration.getAuthenticationManager();
	}
}
