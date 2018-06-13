package asd.prueba.controller;

import asd.prueba.exception.FieldValidationException;
import asd.prueba.exception.ModelMappingException;
import asd.prueba.model.AuthUser;
import asd.prueba.service.AuthUserBs;
import asd.prueba.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.sql.SQLException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador Rest para Usuarios
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@RestController
@Api(value="Autenticacion")
@RequestMapping(value = "users")
public class AuthUserController extends AbstractController<AuthUser>{
    
    @Autowired
    private AuthUserBs authUserBs; 
    
    /**
     * Método para login del usuario
     * @return respuesta http para login
     * @throws java.sql.SQLException en caso de error
     * @throws asd.prueba.exception.ModelMappingException
     */
    @RequestMapping(
            value = {"/login"},
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Object> login() throws SQLException, AccessDeniedException, ModelMappingException  {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        AuthUser authUser = null;
        if(auth.isAuthenticated()){
            authUser = authUserBs.findById(auth.getName());
        }
        if(authUser == null){
            throw new AccessDeniedException("Access Denied Exception");
        }else{
            return new ResponseEntity(mapperToJson(authUser), HttpStatus.OK);
        }
    }
    
    /**
     * Método para logout del usuario
     * @param request petición http
     * @param response respuesta http para logout
     * @return respuesta http para logout
     */
    @RequestMapping(value="/logout",
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> logoutPage (HttpServletRequest request, HttpServletResponse response) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null){ 
            new SecurityContextLogoutHandler().logout(request, response, auth);
        }
        JSONObject bodyResponse = new JSONObject();
        bodyResponse.put("message", "successful logout");
        return new ResponseEntity(bodyResponse.toString(), HttpStatus.OK);
    }
    
    /**
     * Método para obtener un usuario por su identificador
     * @param id identificador del usuario
     * @return usuario encontrado
     * @throws asd.prueba.exception.ModelMappingException
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> getById(
            @PathVariable(value = "id") Object id) throws ModelMappingException {
        AuthUser model = authUserBs.findById(id);
        HttpStatus httpStatus = HttpStatus.OK;
        if(model == null){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), httpStatus);
    }
    
    /**
     * Método para obtener toda la lista de usuarios
     * @param request petición http
     * @return lista de usuarios
     * @throws java.sql.SQLException en caso de error obteniendo lista de usuarios
     * @throws asd.prueba.exception.ModelMappingException
     */
    @RequestMapping(
            value = {""},
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Object> getAll(HttpServletRequest request) throws SQLException, ModelMappingException, Exception {
        Page<AuthUser> listAuthUser = authUserBs.findAllWithPagination(1, DEFAULT_PAGE_SIZE);
        //List<AuthUser> listAuthUser = authUserBs.findAll();
        return new ResponseEntity(mapperToJson(listAuthUser), this.getDefaultHeaders(), HttpStatus.OK);
    }
    
    /**
     * Método para obtener lista paginada de usuarios
     * @param page número de página
     * @param size cantidad máxima de elementos por página
     * @return LIsta paginada de usuarios
     * @throws SQLException en caso de error durante la paginación
     * @throws asd.prueba.exception.ModelMappingException
     */
    @RequestMapping(
            value = {""},
            params = { "page", "size" },
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    @ApiResponses(value = {
        @ApiResponse(code = 200, message = "Successfully retrieved list"),
        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
    })
    public ResponseEntity<Object> getPaginate(
            @RequestParam(value="page", required=true ) Integer page, 
            @RequestParam(value = "size", required=true) Integer size
            ) throws SQLException, ModelMappingException, Exception {
        return new ResponseEntity(mapperToJson(authUserBs.findAllWithPagination(page, size)), HttpStatus.OK);
    }
    
    /**
     * Método para almacenar un nuevo usuario
     * @param requestBody usuario a ser almacenado
     * @return resultado de la operación
     * @throws asd.prueba.exception.ModelMappingException en caso de error mapeando el objeto
     * @throws asd.prueba.exception.FieldValidationException en caso de error de validación
     * @throws java.sql.SQLException en caso de error durante el almacenamiento
     */
    @RequestMapping(
            value = "",
            method = RequestMethod.POST,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> save(
            @RequestBody String requestBody
    ) throws ModelMappingException, FieldValidationException, SQLException {
        AuthUser model = mapperToModel(requestBody);
        authUserBs.insert(model);
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), HttpStatus.CREATED);
    }
    
    /**
     *
     * @param id identificador el usuario a actualizar
     * @param requestBody usuario a ser actualizado
     * @return resultado de la operación
     * @throws asd.prueba.exception.ModelMappingException en caso de error mapeando el objeto
     * @throws asd.prueba.exception.FieldValidationException en caso de error de validación
     * @throws java.sql.SQLException en caso de error durante la actualización
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.PUT,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> update(
            @PathVariable(value = "id") Object id, 
            @RequestBody String requestBody
    ) throws ModelMappingException, FieldValidationException, SQLException, Exception {
        AuthUser modelUpdated = mapperToModel(requestBody);
        authUserBs.update(id, modelUpdated);
        AuthUser model = authUserBs.findById(id);
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), httpStatus);
    }
    
    /**
     *
     * @param id identificador del usuario a ser eliminado
     * @return resultado de la operación
     * @throws asd.prueba.exception.ModelMappingException en caso de error mapeando el objeto
     * @throws asd.prueba.exception.FieldValidationException en caso de error de validación
     * @throws java.sql.SQLException en caso de error durante la eliminación
     */
    @RequestMapping(
            value = "/{id}",
            method = RequestMethod.DELETE,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> delete(
            @PathVariable(value = "id") Object id
    ) throws ModelMappingException, FieldValidationException, SQLException {
        AuthUser model = authUserBs.findById(id);
        HttpStatus httpStatus;
        if(model == null){
            httpStatus = HttpStatus.NOT_FOUND;
        }else{
            authUserBs.delete(model);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), httpStatus);
    }

}

