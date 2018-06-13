package asd.prueba.service;


import asd.prueba.dao.AuthUserDao;
import asd.prueba.exception.FieldValidationException;
import asd.prueba.exception.ValidationFieldError;
import asd.prueba.model.AuthUser;
import asd.prueba.utils.ApiStatus;
import asd.prueba.utils.Page;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alejandro Cadena
 */
@Service("authUserBs")
public class AuthUserBsImpl implements AuthUserBs{
    
    /**
     * Objeto para acceso a datos (DAO)
     */
    AuthUserDao authUserDao;

    /**
     * Se inyecta objeto para acceso a datos (DAO)
     * @param authUserDao 
     */
    @Autowired 
    public AuthUserBsImpl(AuthUserDao authUserDao) {
        this.authUserDao = authUserDao;
    }
    
    /**
     * Se consulta elemento por llave primaria
     * @param pk llave primaria
     * @return elemento encontrado, nulo si no se encontró el elemento
     */
    @Override
    public AuthUser findById(Object pk) {
        return authUserDao.findById(pk);
    }

    /**
     * Se consultan todos los elementos
     * @return lista con todos los elementos
     */
    @Override
    public List<AuthUser> findAll() {
        return authUserDao.findAll();
    }

    /**
     * Se consultan todos los elementos paginados
     * @param pageNo página consultada
     * @param pageSize máximo número de elementos por página
     * @return lista paginada de elementos
     * @throws SQLException en caso de error durante la paginación
     */
    @Override
    public Page<AuthUser> findAllWithPagination(int pageNo, int pageSize) throws SQLException {
        return authUserDao.findAllWithPagination(pageNo, pageSize);
    }

    /**
     * Inserta un nuevo elemento
     * @param modelObject elemento que será insertado
     * @throws FieldValidationException en caso de error de validación
     * @throws SQLException 
     */
    @Override
    public void insert(AuthUser modelObject) throws FieldValidationException, SQLException {
        this.validate(modelObject);
        authUserDao.insert(modelObject);
    }

    /**
     * Actualiza un elemento existente
     * @param pk llave primaria
     * @param modelObject elemento que será actualizado
     * @throws SQLException en caso de error durante la actualización
     * @throws FieldValidationException en caso de error de validación
     * @throws Exception en caso de error interno
     */
    @Override
    public void update(Object pk, AuthUser modelObject) throws SQLException, FieldValidationException, Exception {
        modelObject.setUsername((String) pk);
        this.validate(modelObject);
        authUserDao.update(modelObject);
    }

    /**
     * Elimina un elemento existente
     * @param modelObject elemento que será eliminado
     * @throws SQLException en caso de error durante la eliminación
     */
    @Override
    public void delete(AuthUser modelObject) throws SQLException {
        authUserDao.delete(modelObject);
    }
    
    /**
     * Consulta usuario y valida credenciales
     * @param username nombre de usuario
     * @param password contraseña del usuario
     * @return usuario encontrado
     * @throws SQLException en caso de error durante la consulta
     */
    @Override
    public AuthUser findByUsername(String username, String password) throws SQLException {
        AuthUser authUser = authUserDao.findById(username);
        if(authUser != null){
            if(authUser.getPassword().equals(password)){
                return authUser;
            }
        }
        return null;
    }
    
    /**
     * Método que valida elemento que será insertado
     * @param modelObject elemento recibido en la petición
     * @throws blacksip.generic.exception.FieldValidationException en caso de 
     * error de validación
     */
    private void validate(AuthUser modelObject) throws FieldValidationException{
        List<ValidationFieldError> validationErrors = new ArrayList();
        
        if(modelObject.getUsername() == null || "".equals(modelObject.getUsername())){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_CAN_NOT_BE_EMPTY_OR_NULL, "username")
            );
        }else if(modelObject.getUsername().length() > 60){
            validationErrors.add(
                    new ValidationFieldError(
                            ApiStatus.FIELD_WITH_INVALID_SIZE, 
                            "username", 
                            "The maximum length allowed is 60 characters"
                    )
            );
        }

        if(modelObject.getPassword()== null){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_IS_REQUIRED, "password")
            );
        }else if(modelObject.getPassword().equals("")){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_CAN_NOT_BE_EMPTY, "password")
            );
        }else if(modelObject.getPassword().length() > 60){
            validationErrors.add(
                    new ValidationFieldError(
                            ApiStatus.FIELD_WITH_INVALID_SIZE, 
                            "password", 
                            "The maximum length allowed is 60 characters"
                    )
            );
        }
        if(!validationErrors.isEmpty()){
            throw new FieldValidationException(validationErrors);
        }
    }
    
}
