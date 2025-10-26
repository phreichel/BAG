package phi.timer;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

	@Bean
	public SecurityFilterChain getSecurityFilterChain(HttpSecurity http) throws Exception {
		http
			.csrf(arg -> arg.disable())
			.authorizeHttpRequests(arg -> arg.anyRequest().permitAll())
			.httpBasic(arg -> arg.realmName("TimerServer"));
		return http.build();
	}
	
}
