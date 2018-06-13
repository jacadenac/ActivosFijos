package asd.prueba.dao;

import asd.prueba.model.AssetOwner;
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
 * AssetOwner Dao Class
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@Repository("assetOwnerDao")
public class AssetOwnerDaoJdbc implements AssetOwnerDao {
    
    /**
     * Template para consultas jdbc
     */
    protected NamedParameterJdbcTemplate npJdbcTemplate;
    private static final String SQL_COUNT_ALL = "SELECT count(*) FROM asset_owner";
    private static final String SQL_FIND_ALL = "SELECT * FROM asset_owner";
    private static final String SQL_FIND_BY_PK = "SELECT * FROM asset_owner WHERE asset_owner_id = :asset_owner_id";
    private static final String SQL_INSERT = "INSERT INTO asset_owner (asset_name,created_by_user) VALUES (:asset_name, :created_by_user)";
    private static final String SQL_UPDATE = "UPDATE asset_owner SET asset_name = :asset_name, updated_by_user = :updated_by_user WHERE asset_owner_id = :asset_owner_id";
    private static final String SQL_DELETE = "DELETE FROM asset_owner WHERE asset_owner_id = :asset_owner_id";
    
    private static final Logger LOGGER = LogManager.getLogger(AssetOwnerDaoJdbc.class);
    
    @Autowired 
    AreaDao areaDao;
    
    @Autowired 
    PersonDao personDao;
    
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
    public AssetOwner findById(Object pk) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_owner_id", pk);
        try{
            AssetOwner assetOwner = (AssetOwner) this.npJdbcTemplate.queryForObject(
                    SQL_FIND_BY_PK, 
                    mapParams, 
                    new BeanPropertyRowMapper(AssetOwner.class)
            );
            if(assetOwner != null){
                assetOwner.setArea(areaDao.findById(pk));
                assetOwner.setPerson(personDao.findById(pk));
            }
            return assetOwner;
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    /**
     * Busca todos los elementos registrados
     * @return lista con todos los elementos encontrados
     */
    @Override
    public List<AssetOwner> findAll() {
        return this.npJdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper(AssetOwner.class));
    }

    /**
     * Busca todos los elementos registrados y los retorna paginados
     * @param pageNo Número de página a ser consultada
     * @param pageSize Máximo número de elementos por página
     * @return Lista de elementos paginados
     * @throws SQLException en caso de error durante la consulta
     */
    @Override
    public Page<AssetOwner> findAllWithPagination(int pageNo, int pageSize) throws SQLException {
        PaginationHelper<AssetOwner> ph = new PaginationHelper();
        return ph.fetchPage(
                npJdbcTemplate,
                SQL_COUNT_ALL, 
                SQL_FIND_ALL,
                pageNo,
                pageSize,
                new BeanPropertyRowMapper(AssetOwner.class)
        );
    }

    /**
     * Inserta un elemento nuevo en la base de datos
     * @param modelObject elemento que será insertado
     * @throws SQLException en caso de error durante la inserción
     */
    @Override
    public void insert(AssetOwner modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_name", modelObject.getAssetName());
        mapParams.addValue("created_by_user", springAuthSession.getUserName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.npJdbcTemplate.update(SQL_INSERT, mapParams, keyHolder); 
        Long generatedId = keyHolder.getKey().longValue();
        modelObject.setAssetOwnerId(generatedId);
    }

    /**
     * Actualiza el elemento en la base de datos
     * @param modelObject elemento que será actualizado
     * @throws SQLException en caso de error durante la actualización
     */
    @Override
    public void update(AssetOwner modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_name", modelObject.getAssetName());
        mapParams.addValue("updated__by_user", springAuthSession.getUserName());
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
    public void delete(AssetOwner modelObject) throws SQLException {
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
        String message = "No "+AssetOwner.class.getSimpleName()+" found with PK " + pk;
        EmptyResultDataAccessException erdae = new EmptyResultDataAccessException(message, 1);
        LOGGER.error(message);
        throw erdae;
    }

}
