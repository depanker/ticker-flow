package com.depanker.ticker.security.config;


import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static com.depanker.ticker.security.constants.SecurityConstants.REGISTER_USER;
import static com.depanker.ticker.security.constants.SecurityConstants.TOKEN_URL;


@Configuration
@EnableWebSecurity
@Data
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
	@Qualifier("applicationUserDetailService")
	UserDetailsService userDetailsService;

    @Value("${application.secret:SOLACTIVE}")
	private String secret;

	/**
	 * Methods which are permitted to not authorised user
	 * @param http
	 * @throws Exception
	 */
	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http.csrf().disable().authorizeRequests()
				.antMatchers(HttpMethod.POST, REGISTER_USER).permitAll()
				.antMatchers("/").permitAll().antMatchers(HttpMethod.POST, TOKEN_URL)
				.permitAll().anyRequest().authenticated().and()
				/* We filter the /otp/verify requests */
				.addFilterBefore(new JWTLoginFilter(TOKEN_URL, authenticationManager()),
						UsernamePasswordAuthenticationFilter.class)
				/* We filter the /oauth/login requests */

				/* And filter other requests to check the presence of JWT in header*/
				.addFilterBefore(new JWTAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
	}

	/**
	 * Create account by userDetailsService which will authenticate via otp
	 * @param auth
	 * @throws Exception
	 */
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService);
	}
}