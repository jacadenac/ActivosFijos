package asd.prueba.dao;

import asd.prueba.model.Person;
import asd.prueba.security.SpringAuthSession;
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
 * Person Dao Class
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@Repository("personDao")
public class PersonDaoJdbc implements PersonDao {
    
    /**
     * Template para consultas jdbc
     */
    protected NamedParameterJdbcTemplate npJdbcTemplate;
    private static final String SQL_COUNT_ALL = "SELECT count(*) FROM person";
    private static final String SQL_FIND_ALL = "SELECT * FROM person";
    private static final String SQL_FIND_BY_PK = "SELECT * FROM person WHERE asset_owner_id = :asset_owner_id";
    private static final String SQL_INSERT = "INSERT INTO person (asset_owner_id,document_num,phone,created_by_user) VALUES (:asset_owner_id, :document_num, :phone, :created_by_user)";
    private static final String SQL_UPDATE = "UPDATE person SET asset_owner_id = :asset_owner_id, document_num = :document_num, phone = :phone, updated_by_user = :updated_by_user WHERE asset_owner_id = :asset_owner_id";
    private static final String SQL_DELETE = "DELETE FROM person WHERE asset_owner_id = :asset_owner_id";
    
    private static final Logger LOGGER = LogManager.getLogger(PersonDaoJdbc.class);
    
    @Autowired
    private SpringAuthSession springAuthSession;

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
    public Person findById(Object pk) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_owner_id", pk);
        try{
            return (Person)this.npJdbcTemplate.queryForObject(
                    SQL_FIND_BY_PK, 
                    mapParams, 
                    new BeanPropertyRowMapper(Person.class)
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
    public List<Person> findAll() {
        return this.npJdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper(Person.class));
    }

    /**
     * Busca todos los elementos registrados y los retorna paginados
     * @param pageNo Número de página a ser consultada
     * @param pageSize Máximo número de elementos por página
     * @return Lista de elementos paginados
     * @throws SQLException en caso de error durante la consulta
     */
    @Override
    public Page<Person> findAllWithPagination(int pageNo, int pageSize) throws SQLException {
        PaginationHelper<Person> ph = new PaginationHelper();
        return ph.fetchPage(
                npJdbcTemplate,
                SQL_COUNT_ALL, 
                SQL_FIND_ALL,
                pageNo,
                pageSize,
                new BeanPropertyRowMapper(Person.class)
        );
    }

    /**
     * Inserta un elemento nuevo en la base de datos
     * @param modelObject elemento que será insertado
     * @throws SQLException en caso de error durante la inserción
     */
    @Override
    public void insert(Person modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("document_num", modelObject.getDocumentNum());
        mapParams.addValue("phone", modelObject.getPhone());
        mapParams.addValue("created_by_user", springAuthSession.getUserName());
        this.npJdbcTemplate.update(SQL_INSERT, mapParams); 
    }

    /**
     * Actualiza el elemento en la base de datos
     * @param modelObject elemento que será actualizado
     * @throws SQLException en caso de error durante la actualización
     */
    @Override
    public void update(Person modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("document_num", modelObject.getDocumentNum());
        mapParams.addValue("phone", modelObject.getPhone());
        mapParams.addValue("updated_by_user", springAuthSession.getUserName());
        mapParams.addValue("asset_owner_id", modelObject.getAssetOwnerId());
        int numRowsAffected = this.npJdbcTemplate.update(SQL_UPDATE, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getAssetOwnerId());
        } 
    }

    /**
     * Elimina el elemento de la base de datos
     * @param modelObject elemento que será eliminado
     * @throws SQLException en caso de error durante la eliminación
     */
    @Override
    public void delete(Person modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_owner_id", modelObject.getAssetOwnerId());
        String sentenceSQL = SQL_DELETE;
        int numRowsAffected = this.npJdbcTemplate.update(sentenceSQL, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getAssetOwnerId());
        } 
    }
    
    /**
     * Arroja error indicando que el elemento no fue encontrado
     * @param pk llave primaria
     */
    private void throwNotFoundByPK(Object pk){
        String message = "No "+Person.class.getSimpleName()+" found with PK " + pk;
        EmptyResultDataAccessException erdae = new EmptyResultDataAccessException(message, 1);
        LOGGER.error(message);
        throw erdae;
    }

}
