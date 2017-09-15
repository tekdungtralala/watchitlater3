package info.wiwitadityasaputra.config;

import javax.servlet.Filter;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.csrf.CsrfFilter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.addFilterBefore(corsFilter(), CsrfFilter.class)
			.authorizeRequests()
				.antMatchers("/api/auth/**").permitAll()
				.antMatchers("/api/**").hasRole("USER")
				.anyRequest().permitAll()
				.and()
			.cors().and()
			.csrf().disable();
	}
	
	private Filter corsFilter() {
		return new CorsFilter();
	}
}
