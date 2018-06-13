package asd.prueba.dao;

import asd.prueba.model.FixedAsset;
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
 * FixedAsset Dao Class
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@Repository("fixedAssetDao")
public class FixedAssetDaoJdbc implements FixedAssetDao {
    
    /**
     * Template para consultas jdbc
     */
    protected NamedParameterJdbcTemplate npJdbcTemplate;
    private static final String SQL_COUNT_ALL = "SELECT count(*) FROM fixed_asset";
    private static final String SQL_FIND_ALL = "SELECT * FROM fixed_asset";
    private static final String SQL_FIND_BY_PK = "SELECT * FROM fixed_asset WHERE fixed_asset_id = :fixed_asset_id";
    private static final String SQL_FIND_BY_ASSET_TYPE_ID = "SELECT * FROM fixed_asset WHERE asset_type_id = :asset_type_id";
    private static final String SQL_FIND_BY_PURCHASE_DATE = "SELECT * FROM fixed_asset WHERE purchase_date = :purchase_date";
    private static final String SQL_FIND_BY_SERIAL = "SELECT * FROM fixed_asset WHERE serial = :serial";
    private static final String SQL_INSERT = "INSERT INTO fixed_asset (asset_owner_id, asset_status_id, name, description, asset_type_id, serial, inventory_num, weight, high, width, long_value, purchase_value, purchase_date, withdrawal_date, color, created_by_user) VALUES (:asset_owner_id, :asset_status_id,:name,:description,:asset_type_id,:serial,:inventory_num,:weight,:high,:width,:long_value,:purchase_value,:purchase_date,:withdrawal_date,:color,:created_by_user)";
    private static final String SQL_UPDATE = "UPDATE fixed_asset SET serial = :serial, withdrawal_date = :withdrawal_date, updated_by_user = :updated_by_user WHERE fixed_asset_id = :fixed_asset_id";
    private static final String SQL_DELETE = "DELETE FROM fixed_asset WHERE fixed_asset_id = :fixed_asset_id";
    
    private static final Logger LOGGER = LogManager.getLogger(FixedAssetDaoJdbc.class);

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
    public FixedAsset findById(Object pk) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("fixed_asset_id", pk);
        try{
            return (FixedAsset)this.npJdbcTemplate.queryForObject(
                    SQL_FIND_BY_PK, 
                    mapParams, 
                    new BeanPropertyRowMapper(FixedAsset.class)
            );
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }
    
    /**
     * Busca elementos por identificador del tipo
     * @param assetTypeId identificador del tipo
     * @return lista de elementos encontrados
     */
    @Override
    public List<FixedAsset> findByAssetTypeId(Long assetTypeId) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_type_id", assetTypeId);
        return this.npJdbcTemplate.query(
                SQL_FIND_BY_ASSET_TYPE_ID, 
                mapParams, 
                new BeanPropertyRowMapper(FixedAsset.class)
        );
    }

    /**
     * Busca elementos por fecha de compra
     * @param purchaseDate fecha de compra
     * @return lista de elementos encontrados
     */
    @Override
    public List<FixedAsset> findByPurchaseDate(Date purchaseDate) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("purchase_date", purchaseDate);
        return this.npJdbcTemplate.query(
                SQL_FIND_BY_PURCHASE_DATE, 
                mapParams, 
                new BeanPropertyRowMapper(FixedAsset.class)
        );
    }

    /**
     * Busca elementos por serial
     * @param serial serial del activo
     * @return lista de elementos encontrados
     */
    @Override
    public List<FixedAsset> findBySerial(String serial) {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("serial", serial);
        return this.npJdbcTemplate.query(
                SQL_FIND_BY_SERIAL, 
                mapParams, 
                new BeanPropertyRowMapper(FixedAsset.class)
        );
    }
    
    /**
     * Busca todos los elementos registrados
     * @return lista con todos los elementos encontrados
     */
    @Override
    public List<FixedAsset> findAll() {
        return this.npJdbcTemplate.query(SQL_FIND_ALL, new BeanPropertyRowMapper(FixedAsset.class));
    }

    /**
     * Busca todos los elementos registrados y los retorna paginados
     * @param pageNo Número de página a ser consultada
     * @param pageSize Máximo número de elementos por página
     * @return Lista de elementos paginados
     * @throws SQLException en caso de error durante la consulta
     */
    @Override
    public Page<FixedAsset> findAllWithPagination(int pageNo, int pageSize) throws SQLException {
        PaginationHelper<FixedAsset> ph = new PaginationHelper();
        return ph.fetchPage(
                npJdbcTemplate,
                SQL_COUNT_ALL, 
                SQL_FIND_ALL,
                pageNo,
                pageSize,
                new BeanPropertyRowMapper(FixedAsset.class)
        );
    }

    /**
     * Inserta un elemento nuevo en la base de datos
     * @param modelObject elemento que será insertado
     * @throws SQLException en caso de error durante la inserción
     */
    @Override
    public void insert(FixedAsset modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("asset_owner_id", modelObject.getAssetOwnerId());
        mapParams.addValue("asset_status_id", modelObject.getAssetStatusId());
        mapParams.addValue("name", modelObject.getName());
        mapParams.addValue("description", modelObject.getDescription());
        mapParams.addValue("asset_type_id", modelObject.getAssetTypeId());
        mapParams.addValue("serial", modelObject.getSerial());
        mapParams.addValue("inventory_num", modelObject.getInventoryNum());
        mapParams.addValue("weight", modelObject.getWeight());
        mapParams.addValue("high", modelObject.getHigh());
        mapParams.addValue("width", modelObject.getWidth());
        mapParams.addValue("long_value", modelObject.getLongValue());
        mapParams.addValue("purchase_value", modelObject.getPurchaseValue());
        mapParams.addValue("purchase_date", modelObject.getPurchaseDate());
        mapParams.addValue("withdrawal_date", modelObject.getWithdrawalDate());
        mapParams.addValue("color", modelObject.getColor());
        mapParams.addValue("created_by_user", springAuthSession.getUserName());
        KeyHolder keyHolder = new GeneratedKeyHolder();
        this.npJdbcTemplate.update(SQL_INSERT, mapParams, keyHolder); 
        Long generatedId = keyHolder.getKey().longValue();
        modelObject.setFixedAssetId(generatedId);
    }

    /**
     * Actualiza el elemento en la base de datos
     * @param modelObject elemento que será actualizado
     * @throws SQLException en caso de error durante la actualización
     */
    @Override
    public void update(FixedAsset modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("serial", modelObject.getSerial());
        mapParams.addValue("withdrawal_date", modelObject.getWithdrawalDate());
        mapParams.addValue("updated_by_user", springAuthSession.getUserName());
        mapParams.addValue("fixed_asset_id", modelObject.getFixedAssetId());
        int numRowsAffected = this.npJdbcTemplate.update(SQL_UPDATE, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getFixedAssetId());
        } 
    }

    /**
     * Elimina el elemento de la base de datos
     * @param modelObject elemento que será eliminado
     * @throws SQLException en caso de error durante la eliminación
     */
    @Override
    public void delete(FixedAsset modelObject) throws SQLException {
        MapSqlParameterSource mapParams = new MapSqlParameterSource();
        mapParams.addValue("fixed_asset_id", modelObject.getFixedAssetId());
        String sentenceSQL = SQL_DELETE;
        int numRowsAffected = this.npJdbcTemplate.update(sentenceSQL, mapParams); 
        if(numRowsAffected == 0){
            this.throwNotFoundByPK(modelObject.getFixedAssetId());
        } 
    }
    
    /**
     * Arroja error indicando que el elemento no fue encontrado
     * @param pk llave primaria
     */
    public void throwNotFoundByPK(Object pk){
        String message = "No "+FixedAsset.class.getSimpleName()+" found with PK " + pk;
        EmptyResultDataAccessException erdae = new EmptyResultDataAccessException(message, 1);
        LOGGER.error(message);
        throw erdae;
    }

}
