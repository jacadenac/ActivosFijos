package asd.prueba.controller;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestBuilders.*;
import static org.springframework.security.test.web.servlet.response.SecurityMockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

import javax.servlet.Filter;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

/**
 *
 * @author Alejandro
 * @version 1.0
 */
@Component
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml" })
@ActiveProfiles(profiles = "development")
public class AuthenticationNGTest extends AbstractTestNGSpringContextTests  {
    
    public static final String USERNAME = "alejo";
    public static final String PASSWORD = "yb6x2g32vzm1gt0b";
    
    @Autowired
    @InjectMocks
    private AuthUserController userController;

    @Autowired
    private Filter springSecurityFilterChain;

    private MockMvc mvc;

    @BeforeClass
    public void setup() {
        mvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    @Test
    public void requiresAuthentication() throws Exception {
        System.out.println("requiresAuthentication");
        mvc
            .perform(get("/users/login").contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void authenticationSuccess() throws Exception {
        System.out.println("authenticationSuccess");
        mvc
            .perform(get("/users/login")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(httpBasic(USERNAME, PASSWORD)))
                    .andExpect(status().isOk())
                    .andExpect(authenticated().withUsername(USERNAME));
    }
    
}
