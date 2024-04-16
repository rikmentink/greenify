package nl.hu.greenify.security.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import nl.hu.greenify.security.presentation.filter.JwtAuthenticationFilter;
import nl.hu.greenify.security.presentation.filter.JwtAuthorizationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import static org.springframework.http.HttpMethod.POST;
import static org.springframework.security.config.http.SessionCreationPolicy.STATELESS;
import static org.springframework.security.web.util.matcher.AntPathRequestMatcher.antMatcher;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
public class SecurityConfig {
    private static final String LOGIN_PATH = "/login";
    private static final String REGISTER_PATH = "/account/register";

    private Integer jwtExpirationInMs = 864000000;

    // Different secret key need to be in the database later
    private String jwtSecret = "3A7F89B2D4E6C810F15392A5B7C4E8A3D719BFC45E25D631A8F76C9A4B02D3FE";

    @Bean
    protected AuthenticationManager authenticationManager(final PasswordEncoder passwordEncoder,final UserDetailsService userDetailsService) {
        final DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return new ProviderManager(provider);
    }

    @Bean
    protected SecurityFilterChain filterChain(final HttpSecurity http, final AuthenticationManager authenticationManager) throws Exception {
        http.cors(Customizer.withDefaults())
                .csrf(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(r -> r
                        .requestMatchers(antMatcher(POST, REGISTER_PATH)).permitAll()
                        .requestMatchers(antMatcher(POST, LOGIN_PATH)).permitAll()
                        .requestMatchers(antMatcher("/***")).permitAll()
                        .requestMatchers(antMatcher("/**")).permitAll()
                        .requestMatchers(antMatcher("/*")).permitAll()
                        .requestMatchers(antMatcher("/error")).anonymous()
                        .anyRequest().authenticated()
                )
                .addFilterBefore(new JwtAuthenticationFilter(
                        LOGIN_PATH,
                        jwtSecret,
                        jwtExpirationInMs,
                        authenticationManager
                ), UsernamePasswordAuthenticationFilter.class)
                .addFilter(new JwtAuthorizationFilter(jwtSecret, authenticationManager))
                .sessionManagement(s -> s.sessionCreationPolicy(STATELESS));
        return http.build();
    }

    @Bean
    protected CorsConfigurationSource corsConfigurationSource() {
        return request -> {
            var cors = new CorsConfiguration();
            cors.addAllowedOrigin("*");
            cors.addAllowedMethod("*");
            cors.addAllowedHeader("*");
            return cors;
        };
    }

    @Bean
    protected PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
