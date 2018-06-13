package asd.prueba.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.servlet.Filter;
import static asd.prueba.controller.AuthenticationNGTest.PASSWORD;
import static asd.prueba.controller.AuthenticationNGTest.USERNAME;
import asd.prueba.model.AuthUser;
import asd.prueba.service.AuthUserBs;
import asd.prueba.utils.Page;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import static org.mockito.Mockito.*;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import org.mockito.Spy;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import static org.hamcrest.core.Is.is;
import org.springframework.beans.factory.annotation.Autowired;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.*;

/**
 *
 * @author Alejandro
 * @version 1.0
 */
@Component
@ContextConfiguration(locations = { "file:src/main/webapp/WEB-INF/applicationContext.xml" })
@ActiveProfiles(profiles = "development")
public class AuthUserControllerNGTest extends AbstractTestNGSpringContextTests {
    
    private MockMvc mockMvc;

    @Autowired
    @Mock
    AuthUserBs userService;

    @Autowired
    @InjectMocks
    private AuthUserController userController;
    
    @Spy
    List<AuthUser> users = new ArrayList<AuthUser>();
 
    @Captor
    ArgumentCaptor<AuthUser> captor;
    
    @Autowired
    private Filter springSecurityFilterChain;

    @BeforeClass
    public void init(){
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders
                .standaloneSetup(userController)
                .addFilters(springSecurityFilterChain)
                .build();
    }

    /**
     * Test of getById method, of class AuthUserController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetById() throws Exception {
        System.out.println("getById");
        AuthUser user = new AuthUser("juan", "juan123", "juan@example.com", "Juan David", true, new Date(), new Date());
        when(userService.findById("juan")).thenReturn(user);
        mockMvc.perform(get("/users/{id}", "juan")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andExpect(jsonPath("$.username", is("juan")));
        verify(userService, times(1)).findById("juan");
    }
    
    /**
     * Test of getById method, of class AuthUserController.
     * @throws Exception 
     */
    @Test
    public void testFindByIdFail404NotFound() throws Exception {
        System.out.println("testFindByIdFail404NotFound");
        when(userService.findById("sandra")).thenReturn(null);
        mockMvc.perform(get("/users/{id}", "sandra")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
        verify(userService, times(1)).findById("sandra");
        verifyNoMoreInteractions(userService);
    }

    /**
     * Test of getAll method, of class AuthUserController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetAll() throws Exception {
        System.out.println("getAll");
        List<AuthUser> usersList = getAuthUserList();
        when(userService.findAll()).thenReturn(usersList);
        mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .with(httpBasic(USERNAME, PASSWORD))
                .param("page", "1")
                .param("size", "1"))
                    .andExpect(status().isOk());
    }
    
    /**
     * Test of getPaginate method, of class AuthUserController.
     * @throws java.lang.Exception
     */
    @Test
    public void testGetPaginate() throws Exception {
        System.out.println("getPaginate");
        Page<AuthUser> pages = new Page();
        pages.setTotalItems(2);
        pages.setPagesAvailable(2);
        pages.setPageNumber(1);
        pages.setPageItems(getAuthUserList().subList(0, 0));
        when(userService.findAllWithPagination(1, 1)).thenReturn(pages);
        mockMvc.perform(get("/users")
            .contentType(MediaType.APPLICATION_JSON_UTF8)
            .with(httpBasic(USERNAME, PASSWORD))
            .param("page", "1")
            .param("size", "1"))
                .andExpect(status().isOk());
    }

    /**
     * Test of save method, of class AuthUserController.
     * @throws java.lang.Exception
     */
    @Test
    public void testSave() throws Exception {
        System.out.println("save");
        AuthUser user = new AuthUser("david", "david123", "david@example.com", "David", true, null, null);
        doNothing().when(userService).insert(user);
        mockMvc.perform(
                post("/users")
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .with(httpBasic(USERNAME, PASSWORD))
                .content(asJsonString(user)))
                    .andExpect(status().isCreated());
    }

    /**
     * Test of update method, of class AuthUserController.
     * @throws java.lang.Exception
     */
    @Test
    public void testUpdate() throws Exception {
        System.out.println("update");
        AuthUser user = new AuthUser("alejandro", "alejandro234", "alejandro2@example.com", "Alejandro", false, null, null);
        when(userService.findById(user.getUsername())).thenReturn(user);
        doNothing().when(userService).update(user.getUsername(), user);
        mockMvc.perform(
                put("/users/{id}", user.getUsername())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .with(httpBasic(USERNAME, PASSWORD))
                    .content(asJsonString(user)))
                        .andExpect(status().isOk());
        verify(userService, times(1)).findById(user.getUsername());
        verify(userService, times(1)).update(user.getUsername(), user);
    }

    /**
     * Test of delete method, of class AuthUserController.
     */
    @Test
    public void testDelete() throws Exception {
        System.out.println("delete");
        AuthUser user = new AuthUser("david2", "juan234", "juan2@example.com", "Juan David 2", true, null, null);
        when(userService.findById(user.getUsername())).thenReturn(user);
        doNothing().when(userService).delete(user);
        mockMvc.perform(
                delete("/users/{id}", user.getUsername())
                    .contentType(MediaType.APPLICATION_JSON_UTF8)
                    .with(httpBasic(USERNAME, PASSWORD)))
                        .andExpect(status().isOk());
        verify(userService, times(1)).findById(user.getUsername());
        verify(userService, times(1)).delete(user);
        verifyNoMoreInteractions(userService);
    }
    
    
    /*
     * Simple data provider method
     */
    public List<AuthUser> getAuthUserList() {
        if(users.isEmpty()){
            AuthUser a1 = new AuthUser();
            a1.setUsername("juan");
            a1.setPassword("juan123");
            a1.setEmail("juan@ejemplo.com");
            a1.setFullname("Juan David");
            a1.setEnabled(true);
            a1.setCreatedDate(new Date());
            a1.setUpdatedDate(new Date());

            AuthUser a2 = new AuthUser();
            a2.setUsername("alejandro");
            a2.setPassword("alejo123");
            a2.setEmail("alejo@ejemplo.com");
            a2.setFullname("Alejandro");
            a2.setEnabled(true);
            a2.setCreatedDate(new Date());
            a2.setUpdatedDate(new Date());

            users.add(a1);
            users.add(a2);
        }
        return users;
    }
    
    /*
     * converts a Java object into JSON representation
     */
    public String asJsonString(final Object obj) {
        try {
            return userController.customObjectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }
    
}

