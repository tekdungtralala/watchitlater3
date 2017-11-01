package info.wiwitadityasaputra.util.config;

import java.util.Arrays;

import info.wiwitadityasaputra.util.api.ApiPath;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.authorizeRequests()
				.antMatchers(
					ApiPath.PATH_USER_AUTH_ME,
					ApiPath.API_PATH_MOVIE_FAVORITE
					).hasRole("USER")
				.antMatchers("/api/**")
					.permitAll()
				.anyRequest()
					.permitAll()
				.and()
			.cors().and()
			.csrf().disable()
//			.logout()
//				.logoutRequestMatcher(new AntPathRequestMatcher(AbstractCtrl.API_PATH_USER_AUTH + AbstractCtrl.SIGNOUT))
//				.logoutSuccessUrl("/")
//				.deleteCookies("JSESSIONID")
//				.invalidateHttpSession(true)
		;
	}
	
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        final CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("HEAD", "GET", "POST", "PUT", "DELETE", "PATCH"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("Authorization", "Cache-Control", "Content-Type"));
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
}
