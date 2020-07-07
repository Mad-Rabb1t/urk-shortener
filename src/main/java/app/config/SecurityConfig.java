package app.config;

import app.service.ZUserCreationService;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
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

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .authorizeRequests()
                .antMatchers("/", "/register", "/login", "/forgot", "/error", "/remove").permitAll()
                .anyRequest().authenticated();

//        httpSecurity.rememberMe();

        httpSecurity
                .formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .passwordParameter("password")
                .successForwardUrl("/start")
                .failureForwardUrl("/error")
                .permitAll();
    }

    //    /**
//     * by removing this @Bean annotation
//     * we make this part of code INVISIBLE for Spring
//     */
////  @Bean
//    @Override
//    protected UserDetailsService userDetailsService() {
//        return new InMemoryUserDetailsManager(Arrays.asList(
//                User.withDefaultPasswordEncoder()
//                        .username("random@mail.ru")
//                        .password("123")
//                        .roles().build(),
//                User.withDefaultPasswordEncoder()
//                        .username("veryrandom@mail.ru")
//                        .password("234")
//                        .roles().build()
//        ));
//    }

}