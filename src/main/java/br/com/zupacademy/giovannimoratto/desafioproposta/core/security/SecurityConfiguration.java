package br.com.zupacademy.giovannimoratto.desafioproposta.core.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.oauth2.server.resource.OAuth2ResourceServerConfigurer;

import static org.springframework.http.HttpMethod.GET;
import static org.springframework.http.HttpMethod.POST;

/**
 * @Author giovanni.moratto
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests(authorizeRequests -> authorizeRequests
                        .antMatchers(GET, "/actuator/**").permitAll()
                        .antMatchers(POST, "/api/nova-proposta").hasAuthority("SCOPE_propostas:write")
                        .antMatchers(GET, "/api/proposta/**").hasAuthority("SCOPE_propostas:read")
                        .antMatchers(POST, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:write")
                        .antMatchers(GET, "/api/cartoes/**").hasAuthority("SCOPE_cartoes:read")
                        .anyRequest().authenticated())
                .oauth2ResourceServer(OAuth2ResourceServerConfigurer::jwt);
    }
}
