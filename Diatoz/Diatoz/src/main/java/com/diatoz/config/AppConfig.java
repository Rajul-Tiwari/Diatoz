package com.diatoz.config;

import com.diatoz.jwt.AuthTokenFilter;
import com.diatoz.jwt.EntryPoint;
import com.diatoz.service.UserModelServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


@Configuration
@RequiredArgsConstructor
public class AppConfig {


    private final UserModelServiceImpl userModelServiceImpl;

    private final AuthTokenFilter authTokenFilter;


    private final EntryPoint entryPoint;






    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {


        http.exceptionHandling().authenticationEntryPoint(entryPoint).and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeHttpRequests(auth ->{
                    auth.requestMatchers().permitAll()
                            .antMatchers("/api/v1/admin/login"
                                    ,"/api/v1/teacher/login"
                                    , "/api/v1/student/login"
                            ).permitAll()
                            .antMatchers("/api/v1/admin/**" ).hasAuthority("ADMIN")
                            .antMatchers("/api/v1/teacher/**" ).hasAuthority("TEACHER")
                            .antMatchers("/api/v1/student/**" ).hasAuthority("STUDENT")
                            .anyRequest().authenticated();
                }).cors().and().csrf().disable();
        http.authenticationProvider(daoAuthenticationProvider());
        http.addFilterBefore(tokenFilter(), UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
    @Bean
    public DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider();
        daoAuthenticationProvider.setUserDetailsService(userModelServiceImpl);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder() ;

    }


    /**
     * Creates and configures the {@link AuthenticationManager} bean.
     *
     * @param auth the {@link AuthenticationConfiguration} used to retrieve the authentication manager
     * @return the configured {@link AuthenticationManager} bean
     * @throws Exception if an error occurs during configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration auth) throws Exception {

        return auth.getAuthenticationManager()   ;

    }



    /**
     * Creates and configures the {@link AuthTokenFilter} bean.
     *
     * @return the configured {@link AuthTokenFilter} bean
     */
    @Bean
    public AuthTokenFilter tokenFilter(){
        return new AuthTokenFilter();
    }



}
