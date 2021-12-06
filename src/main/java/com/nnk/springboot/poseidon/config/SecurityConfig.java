package com.nnk.springboot.poseidon.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.client.oidc.web.logout.OidcClientInitiatedLogoutSuccessHandler;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;

import com.nnk.springboot.poseidon.service.UserService;

@Configuration
@EnableWebSecurity(debug = false)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Autowired
	private UserService customUserDetailsService;

	@Autowired
	private ClientRegistrationRepository clientRegistrationRepository;

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		// @formatter:off

		http.csrf().disable()
			.authorizeRequests()
				.antMatchers("/", "/home", "/login", "/logout", "/error").permitAll()
				.antMatchers("/app/secure/article-details", "/user/**").hasAuthority("ADMIN")
				.anyRequest().authenticated()
			.and()
			.formLogin()
				.defaultSuccessUrl("/bidList/list")
			.and()
			.logout()
				.logoutUrl("/app-logout")
				.logoutSuccessHandler(oidcLogoutSuccessHandler())
				.invalidateHttpSession(true)
				.clearAuthentication(true)
				.deleteCookies("JSESSIONID")
				.logoutSuccessUrl("/")
			.and()
			.oauth2Login()
				.defaultSuccessUrl("/bidList/list")
			.and()
				.exceptionHandling()
					.accessDeniedPage("/403");
		 
		// @formatter:on

	}

	private OidcClientInitiatedLogoutSuccessHandler oidcLogoutSuccessHandler() {
		OidcClientInitiatedLogoutSuccessHandler successHandler = new OidcClientInitiatedLogoutSuccessHandler(
				clientRegistrationRepository);
		successHandler.setPostLogoutRedirectUri("http://localhost:8080/");
		return successHandler;
	}

	@Override
	protected void configure(AuthenticationManagerBuilder authManagerBuilder) throws Exception {
		authManagerBuilder.userDetailsService(customUserDetailsService).passwordEncoder(bCryptPasswordEncoder());
	}

	@Bean
	public PasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	/*
	 * Autorizes statics resources (css...)
	 */
	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/resources/**", "/static/**", "/css/**");
	}
}
