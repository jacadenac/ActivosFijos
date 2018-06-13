package asd.prueba.dao;

import asd.prueba.model.AuthUser;
import asd.prueba.utils.Page;
import asd.prueba.utils.PaginationHelper;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import javax.sql.DataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

/**
 * AuthUser Dao Class
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@Repository("authUserDao")
public class AuthUserDaoJdbc implements AuthUserDao {
    
    /**
     * Template para consultas jdbc
     */
    protected NamedParameterJdbcTemplate npJdbcTemplate;
    private static final String SQL_COUNT_ALL = "SELECT count(*) FROM auth_user";
    private static final String SQL_FIND_ALL = "SELECT * FROM auth_user";
    private static final String SQL_FIND_BY_PK = "SELECT * FROM auth_user WHERE username = :username";
    private static final String SQL_INSERT = "INSERT INTO auth_user "
            + "(username, password, fullname, email, enabled) "
            + "VALUES (:username, :password, :fullname, :email, :enabled)";
    private static final String SQL_UPDATE = "UPDATE auth_user SET "
            + "fullname = :fullname, email = :email, enabled = :enabled, "
            + "updated_date = :updated_date WHERE username = :username";
    private static final String SQL_DELETE = "DELETE FROM auth_user "
            + "WHERE username = :username";
    
    private static final Logger LOGGER = LogManager.getLogger(AuthUserDaoJdbc.class);

    /**
     * Se inyecta dataSource configurado en applicationContext.xml
     * @param dataSource fuente de datos
     */
    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.npJdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }
    
    /**
     * Busca elemento por llave primaria
     * @param pk llave primaria
     * @return elemento encontrado, nulo si no se encontró el elemento
     */
    @Override
    public AuthUser findById(Object pk) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("username", pk);
        try{
            return (AuthUser)this.npJdbcTemplate.queryForObject(
                    SQL_FIND_BY_PK, 
                    mapParams, 
                    new BeanPropertyRowMapper(AuthUser.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    /**
     * Busca todos los elementos registrados
     * @return lista con todos los elementos encontrados
     */
    @Override
    public List<AuthUser> findAll() {
        return this.npJdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper(AuthUser.class));
    }

    /**
     * Busca todos los elementos registrados y los retorna paginados
     * @param pageNo Número de página a ser consultada
     * @param pageSize Máximo número de elementos por página
     * @return Lista de elementos paginados
     * @throws SQLException en caso de error durante la consulta
     */
    @Override
    public Page<AuthUser> findAllWithPagination(int pageNo, int pageSize) throws SQLException {
        PaginationHelper<AuthUser> ph = new PaginationHelper();
        return ph.fetchPage(
                npJdbcTemplate,
                SQL_COUNT_ALL, 
                SQL_FIND_ALL,
                pageNo,
                pageSize,
                new BeanPropertyRowMapper(AuthUser.class)
        );
    }

    /**
     * Inserta un elemento nuevo en la base de datos
     * @param modelObject elemento que será insertado
     * @throws SQLException en caso de error durante la inserción
     */
    @Override
    public void insert(AuthUser modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("username", modelObject.getUsername());
        mapParams.addValue("password", modelObject.getPassword());
        mapParams.addValue("fullname", modelObject.getFullname());
        mapParams.addValue("email", modelObject.getEmail());
        mapParams.addValue("enabled", modelObject.isEnabled());
        mapParams.addValue("updated_date", new Date());
        this.npJdbcTemplate.update(SQL_INSERT, mapParams); 
    }

    /**
     * Actualiza el elemento en la base de datos
     * @param modelObject elemento que será actualizado
     * @throws SQLException en caso de error durante la actualización
     */
    @Override
    public void update(AuthUser modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("fullname", modelObject.getFullname());
        mapParams.addValue("email", modelObject.getEmail());
        mapParams.addValue("enabled", modelObject.isEnabled());
        mapParams.addValue("username", modelObject.getUsername());
        mapParams.addValue("updated_date", new Date());
        int numRowsAffected = this.npJdbcTemplate.update(SQL_UPDATE, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getUsername());
        } 
    }

    /**
     * Elimina el elemento de la base de datos
     * @param modelObject elemento que será eliminado
     * @throws SQLException en caso de error durante la eliminación
     */
    @Override
    public void delete(AuthUser modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("username", modelObject.getUsername());
        String sentenceSQL = SQL_DELETE;
        int numRowsAffected = this.npJdbcTemplate.update(sentenceSQL, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getUsername());
        } 
    }
    
    /**
     * Arroja error indicando que el elemento no fue encontrado
     * @param pk llave primaria
     */
    private void throwNotFoundByPK(Object pk){
        String message = "No "+AuthUser.class.getSimpleName()+" found with PK " + pk;
        EmptyResultDataAccessException erdae = new EmptyResultDataAccessException(message, 1);
        LOGGER.error(message);
        throw erdae;
    }

}
