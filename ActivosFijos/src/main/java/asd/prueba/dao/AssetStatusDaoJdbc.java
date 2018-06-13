package asd.prueba.dao;

import asd.prueba.model.AssetStatus;
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
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

/**
 * AssetStatus Dao Class
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@Repository("assetStatusDao")
public class AssetStatusDaoJdbc implements AssetStatusDao {
    
    /**
     * Template para consultas jdbc
     */
    protected NamedParameterJdbcTemplate npJdbcTemplate;
    private static final String SQL_COUNT_ALL = "SELECT count(*) FROM asset_status";
    private static final String SQL_FIND_ALL = "SELECT * FROM asset_status";
    private static final String SQL_FIND_BY_PK = "SELECT * FROM asset_status WHERE asset_status_id = :asset_status_id";
    private static final String SQL_INSERT = "INSERT INTO asset_status (name,created_by_user) VALUES (:name, :created_by_user)";
    private static final String SQL_UPDATE = "UPDATE asset_status SET name = :name, updated_by_user = :updated_by_user WHERE asset_status_id = :asset_status_id";
    private static final String SQL_DELETE = "DELETE FROM asset_status WHERE asset_status_id = :asset_status_id";
    
    private static final Logger LOGGER = LogManager.getLogger(AssetStatusDaoJdbc.class);

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
    public AssetStatus findById(Object pk) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_status_id", pk);
        try{
            return (AssetStatus)this.npJdbcTemplate.queryForObject(
                    SQL_FIND_BY_PK, 
                    mapParams, 
                    new BeanPropertyRowMapper(AssetStatus.class)
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
    public List<AssetStatus> findAll() {
        return this.npJdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper(AssetStatus.class));
    }

    /**
     * Busca todos los elementos registrados y los retorna paginados
     * @param pageNo Número de página a ser consultada
     * @param pageSize Máximo número de elementos por página
     * @return Lista de elementos paginados
     * @throws SQLException en caso de error durante la consulta
     */
    @Override
    public Page<AssetStatus> findAllWithPagination(int pageNo, int pageSize) throws SQLException {
        PaginationHelper<AssetStatus> ph = new PaginationHelper();
        return ph.fetchPage(
                npJdbcTemplate,
                SQL_COUNT_ALL, 
                SQL_FIND_ALL,
                pageNo,
                pageSize,
                new BeanPropertyRowMapper(AssetStatus.class)
        );
    }

    /**
     * Inserta un elemento nuevo en la base de datos
     * @param modelObject elemento que será insertado
     * @throws SQLException en caso de error durante la inserción
     */
    @Override
    public void insert(AssetStatus modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("name", modelObject.getName());
        mapParams.addValue("created_by_user", springAuthSession.getUserName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.npJdbcTemplate.update(SQL_INSERT, mapParams, keyHolder); 
        Long generatedId = keyHolder.getKey().longValue();
        modelObject.setAssetStatusId(generatedId);
    }

    /**
     * Actualiza el elemento en la base de datos
     * @param modelObject elemento que será actualizado
     * @throws SQLException en caso de error durante la actualización
     */
    @Override
    public void update(AssetStatus modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("name", modelObject.getName());
        mapParams.addValue("asset_status_id", modelObject.getAssetStatusId());
        mapParams.addValue("updated_by_user", springAuthSession.getUserName());
        int numRowsAffected = this.npJdbcTemplate.update(SQL_UPDATE, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getAssetStatusId());
        } 
    }

    /**
     * Elimina el elemento de la base de datos
     * @param modelObject elemento que será eliminado
     * @throws SQLException en caso de error durante la eliminación
     */
    @Override
    public void delete(AssetStatus modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_status_id", modelObject.getAssetStatusId());
        String sentenceSQL = SQL_DELETE;
        int numRowsAffected = this.npJdbcTemplate.update(sentenceSQL, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getAssetStatusId());
        } 
    }
    
    /**
     * Arroja error indicando que el elemento no fue encontrado
     * @param pk llave primaria
     */
    private void throwNotFoundByPK(Object pk){
        String message = "No "+AssetStatus.class.getSimpleName()+" found with PK " + pk;
        EmptyResultDataAccessException erdae = new EmptyResultDataAccessException(message, 1);
        LOGGER.error(message);
        throw erdae;
    }

}
