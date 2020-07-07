package app.config;

import app.service.ZUserCreationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfiguration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

import java.util.Arrays;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    public SecurityConfig(ZUserCreationService z) {
        z.create();
    }

    /**
     * by removing this @Bean annotation
     * we make this part of code INVISIBLE for Spring
     */
//  @Bean
    @Override
    protected UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(Arrays.asList(
                User.withDefaultPasswordEncoder()
                        .username("random@mail.ru")
                        .password("123")
                        .roles().build(),
                User.withDefaultPasswordEncoder()
                        .username("veryrandom@mail.ru")
                        .password("234")
                        .roles().build()
        ));
    }
}