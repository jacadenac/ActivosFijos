package asd.prueba.security;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Clase para configuración de autenticación básica
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */

@Configuration
@EnableWebSecurity
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Autowired
    private AuthenticationEntryPoint authEntryPoint;
    
    @Autowired
    private DataSource dataSource;
    
    private static final String[] AUTH_WHITELIST = {
            // -- swagger ui
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v2/api-docs",
            "/webjars/**"
    };
    
    /**
     * Configuración general de la autenticación básica
     * @param http configuración de seguridad http de Spring
     * @throws Exception en caso de error durante configuración
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().authorizeRequests()
                .antMatchers(AUTH_WHITELIST).permitAll()
                .antMatchers("users/login**").permitAll()
                .anyRequest().authenticated()
                .and().httpBasic()
                .authenticationEntryPoint(authEntryPoint);
    }
    
    /**
     * Método para validar autenticación utilizando base de datos de usuarios
     * @param auth gestor de autenticaciones de Spring
     * @throws Exception en caso de error durante proceso de validación
     */
    @Autowired
    public void configAuthentication(AuthenticationManagerBuilder auth) throws Exception {
      auth.jdbcAuthentication().dataSource(dataSource)
            .usersByUsernameQuery(
                    "select username, password, enabled from auth_user where username=?")
            .authoritiesByUsernameQuery(
                    "select username, role from auth_user_role where username=?");
    }
    
}
