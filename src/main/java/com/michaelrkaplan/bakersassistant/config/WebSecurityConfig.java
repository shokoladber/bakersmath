package com.michaelrkaplan.bakersassistant.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authorization.AuthorityAuthorizationDecision;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.FormLoginConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.ExceptionTranslationFilter;
import org.springframework.security.web.access.intercept.AuthorizationFilter;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;
import org.springframework.security.web.authentication.RememberMeServices;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.session.SessionAuthenticationStrategy;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

//    @Autowired
//    private AuthorityAuthorizationDecision authorityAuthorizationDecision;
//
//    @Autowired
//    public WebSecurityConfig(AuthorityAuthorizationDecision authorityAuthorizationDecision) {
//        this.authorityAuthorizationDecision = authorityAuthorizationDecision;
//    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public AuthorizationFilter authorizationFilter() {
//        return new AuthorizationFilter(authorityAuthorizationDecision, new HttpSessionRequestCache());
//    }

//    @Bean
//    public UsernamePasswordAuthenticationFilter usernamePasswordAuthenticationFilter() {
//        return new UsernamePasswordAuthenticationFilter();
//    }
//
//    @Bean
//    public LoginUrlAuthenticationEntryPoint loginUrlAuthenticationEntryPoint() {
//        return new LoginUrlAuthenticationEntryPoint("/login");
//
//    }
//
//    @Bean
//    public ExceptionTranslationFilter exceptionTranslationFilter() throws Exception {
//        return new ExceptionTranslationFilter(loginUrlAuthenticationEntryPoint());
//    }
//
//
//    public Customizer<FormLoginConfigurer<HttpSecurity>> customizerFormLoginConfigurer() {
//        return formLoginConfigurer ->
//            formLoginConfigurer
//                .loginPage("/login")
//                .passwordParameter("password")
//                .usernameParameter("email");
//    };

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authorizeHttpRequests) ->
                    authorizeHttpRequests
                            .requestMatchers("/**")
                            .permitAll()
            )
            .formLogin(form -> form
                    .loginPage("/login")
                    .permitAll()
            );
        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails userDetails = User.builder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();

        return new InMemoryUserDetailsManager(userDetails);
    }

}

//    //After filter chain
//    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken();
//    AuthenticationManager authenticationManager = new AuthenticationManager();
//    SessionAuthenticationStrategy sessionAuthenticationStrategy = new SessionAuthenticationStrategy();
//    SecurityContextHolder securityContextHolder = new SecurityContextHolder();
//    RememberMeServices rememberMeServices = new RememberMeServices();
//    ApplicationEventPublisher applicationEventPublisher = new ApplicationEventPublisher();
//    AuthenticationSuccessHandler authenticationSuccessHandler = new AuthenticationSuccessHandler();
