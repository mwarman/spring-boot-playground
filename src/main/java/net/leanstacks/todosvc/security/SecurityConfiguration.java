package net.leanstacks.todosvc.security;

import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {

  private static final Logger logger = LoggerFactory.getLogger(SecurityConfiguration.class);

  @Bean
  public InMemoryUserDetailsManager inMemoryUserDetailsManager() {
    // password encoding for demonstration purposes only
    // not suitable for production
    UserBuilder users = User.withDefaultPasswordEncoder();
    UUID userPass = UUID.randomUUID();
    logger.debug("\n\nGenerated user password: {}\n\n", userPass);
    UserDetails user = users
        .username("user")
        .password(userPass.toString())
        .roles("USER")
        .build();
    UUID adminPass = UUID.randomUUID();
    logger.debug("\n\nGenerated admin password: {}\n\n", adminPass);
    UserDetails admin = users
        .username("admin")
        .password(adminPass.toString())
        .roles("ADMIN")
        .build();
    return new InMemoryUserDetailsManager(user, admin);
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf((csrf) -> csrf.disable())
        .authorizeHttpRequests(
            (authorize) -> authorize
                .requestMatchers("/api/todos/**").hasRole("USER")
                .requestMatchers("/api/actuators/health").permitAll()
                .requestMatchers("/api/actuators/**").hasRole("ADMIN")
                .anyRequest().denyAll())
        .httpBasic(Customizer.withDefaults());

    return http.build();
  }

}
