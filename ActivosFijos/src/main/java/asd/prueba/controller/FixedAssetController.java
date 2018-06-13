package asd.prueba.controller;

import asd.prueba.exception.FieldValidationException;
import asd.prueba.exception.ModelMappingException;
import asd.prueba.model.FixedAsset;
import asd.prueba.service.FixedAssetBs;
import asd.prueba.utils.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador Rest para Activos Fijos
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@RestController
@Api(value="Activos fijos")
@RequestMapping(value = "fixedAssets")
public class FixedAssetController extends AbstractController<FixedAsset>{
    
    @Autowired
    private FixedAssetBs fixedAssetBs; 
            
    /**
     * Método para obtener un activo por su identificador
     * @param id identificador del activo
     * @return activo encontrado
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
        FixedAsset model = fixedAssetBs.findById(id);
        HttpStatus httpStatus = HttpStatus.OK;
        if(model == null){
            httpStatus = HttpStatus.NOT_FOUND;
        }
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), httpStatus);
    }
    
    /**
     * Método para obtener un activo por su serial
     * @param serial serial del activo
     * @return activos encontrado
     * @throws asd.prueba.exception.ModelMappingException
     * @throws java.sql.SQLException en caso de error durante la consulta
     */
    @RequestMapping(
            value = "",
            params = { "serial"},
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> getBySerial(
            @RequestParam("serial") String serial) throws ModelMappingException, SQLException {
        List<FixedAsset> list = fixedAssetBs.findBySerial(serial);
        return new ResponseEntity(mapperToJson(list), this.getDefaultHeaders(), HttpStatus.OK);
    }
    
    /**
     * Método para obtener activos por fecha de compra
     * @param purchaseDate fecha de compra
     * @return activos encontrados
     * @throws asd.prueba.exception.ModelMappingException
     * @throws java.sql.SQLException en caso de error durante la consulta
     */
    @RequestMapping(
            value = "",
            params = { "purchaseDate"},
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> getByPurchaseDate(
            @RequestParam(value="purchaseDate") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date purchaseDate) throws ModelMappingException, SQLException { // ISO 8601 
        List<FixedAsset> list = fixedAssetBs.findByPurchaseDate(purchaseDate);
        return new ResponseEntity(mapperToJson(list), this.getDefaultHeaders(), HttpStatus.OK);
    }
    
     /**
     * Método para obtener activos por tipo de activo
     * @param assetTypeId tipo de activo
     * @return activos encontrados
     * @throws asd.prueba.exception.ModelMappingException
     * @throws java.sql.SQLException en caso de error durante la consulta
     */
    @RequestMapping(
            value = "",
            params = { "assetTypeId"},
            method = RequestMethod.GET,
            consumes =  MediaType.APPLICATION_JSON_VALUE,
            produces = {MediaType.APPLICATION_JSON_VALUE}
    )
    public ResponseEntity<Object> getByPurchaseDate(
            @RequestParam(value="assetTypeId") Long assetTypeId) throws ModelMappingException, SQLException {
        List<FixedAsset> list = fixedAssetBs.findByAssetTypeId(assetTypeId);
        return new ResponseEntity(mapperToJson(list), this.getDefaultHeaders(), HttpStatus.OK);
    }
    
    /**
     * Método para obtener toda la lista de activos
     * @param request petición http
     * @return lista de activos
     * @throws java.sql.SQLException en caso de error obteniendo lista de activos
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
        Page<FixedAsset> listFixedAsset = fixedAssetBs.findAllWithPagination(1, DEFAULT_PAGE_SIZE);
        return new ResponseEntity(mapperToJson(listFixedAsset), this.getDefaultHeaders(), HttpStatus.OK);
    }
    
    /**
     * Método para obtener lista paginada de activos
     * @param page número de página
     * @param size cantidad máxima de elementos por página
     * @return LIsta paginada de activos
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
        return new ResponseEntity(mapperToJson(fixedAssetBs.findAllWithPagination(page, size)), HttpStatus.OK);
    }
    
    /**
     * Método para almacenar un nuevo activo
     * @param requestBody activo a ser almacenado
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
        FixedAsset model = mapperToModel(requestBody);
        fixedAssetBs.insert(model);
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), HttpStatus.CREATED);
    }
    
    /**
     *
     * @param id identificador el activo a actualizar
     * @param requestBody activo a ser actualizado
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
        FixedAsset modelUpdated = mapperToModel(requestBody);
        fixedAssetBs.update(id, modelUpdated);
        FixedAsset model = fixedAssetBs.findById(id);
        HttpStatus httpStatus = HttpStatus.OK;
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), httpStatus);
    }
    
    /**
     *
     * @param id identificador del activo a ser eliminado
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
        FixedAsset model = fixedAssetBs.findById(id);
        HttpStatus httpStatus;
        if(model == null){
            httpStatus = HttpStatus.NOT_FOUND;
        }else{
            fixedAssetBs.delete(model);
            httpStatus = HttpStatus.OK;
        }
        return new ResponseEntity(mapperToJson(model), this.getDefaultHeaders(), httpStatus);
    }

}

