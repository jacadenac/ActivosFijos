package asd.prueba.service;


import asd.prueba.dao.AssetOwnerDao;
import asd.prueba.dao.AssetStatusDao;
import asd.prueba.dao.AssetTypeDao;
import asd.prueba.dao.FixedAssetDao;
import asd.prueba.exception.FieldValidationException;
import asd.prueba.exception.ValidateField;
import asd.prueba.exception.ValidationFieldError;
import asd.prueba.model.Area;
import asd.prueba.model.FixedAsset;
import asd.prueba.model.Person;
import asd.prueba.utils.ApiStatus;
import asd.prueba.utils.Page;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author Alejandro Cadena
 */
@Service("fixedAssetBs")
public class FixedAssetBsImpl implements FixedAssetBs{
    
    /**
     * Objeto para acceso a datos (DAO)
     */
    FixedAssetDao fixedAssetDao;
    
    @Autowired 
    AssetStatusDao assetStatusDao;
    
    @Autowired 
    AssetTypeDao assetTypeDao;
    
    @Autowired 
    AssetOwnerDao assetOwnerDao;

    /**
     * Se inyecta objeto para acceso a datos (DAO)
     * @param fixedAssetDao 
     */
    @Autowired 
    public FixedAssetBsImpl(FixedAssetDao fixedAssetDao) {
        this.fixedAssetDao = fixedAssetDao;
    }
    
    /**
     * Se consulta elemento por llave primaria
     * @param pk llave primaria
     * @return elemento encontrado, nulo si no se encontró el elemento
     */
    @Override
    public FixedAsset findById(Object pk) {
        FixedAsset fixedAsset = fixedAssetDao.findById(pk);
        if(fixedAsset != null){
            fixedAsset.setAssetStatus(assetStatusDao.findById(fixedAsset.getAssetStatusId()));
            fixedAsset.setAssetType(assetTypeDao.findById(fixedAsset.getAssetTypeId()));
            fixedAsset.setAssetOwner(assetOwnerDao.findById(fixedAsset.getAssetOwnerId()));
        }
        return fixedAsset;
    }

    /**
     * Se consultan todos los elementos
     * @return lista con todos los elementos
     */
    @Override
    public List<FixedAsset> findAll() {
        return fixedAssetDao.findAll();
    }

    /**
     * Se consultan todos los elementos paginados
     * @param pageNo página consultada
     * @param pageSize máximo número de elementos por página
     * @return lista paginada de elementos
     * @throws SQLException en caso de error durante la paginación
     */
    @Override
    public Page<FixedAsset> findAllWithPagination(int pageNo, int pageSize) throws SQLException {
        return fixedAssetDao.findAllWithPagination(pageNo, pageSize);
    }

    /**
     * Inserta un nuevo elemento
     * @param modelObject elemento que será insertado
     * @throws FieldValidationException en caso de error de validación
     * @throws SQLException 
     */
    @Override
    public void insert(FixedAsset modelObject) throws FieldValidationException, SQLException {
        this.validateInsert(modelObject);
        fixedAssetDao.insert(modelObject);
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
    public void update(Object pk, FixedAsset modelObject) throws SQLException, FieldValidationException, Exception {
        modelObject.setFixedAssetId(Long.valueOf(String.valueOf(pk)));
        this.validateUpdate(modelObject);
        fixedAssetDao.update(modelObject);
    }

    /**
     * Elimina un elemento existente
     * @param modelObject elemento que será eliminado
     * @throws SQLException en caso de error durante la eliminación
     */
    @Override
    public void delete(FixedAsset modelObject) throws SQLException {
        fixedAssetDao.delete(modelObject);
    }
    
    /**
     * Busca elementos por identificador del tipo
     * @param assetTypeId identificador del tipo
     * @return lista de elementos encontrados
     * @throws java.sql.SQLException en caso de error durante la consulta
     */
    @Override
    public List<FixedAsset> findByAssetTypeId(Long assetTypeId) throws SQLException {
        return fixedAssetDao.findByAssetTypeId(assetTypeId);
    }

    /**
     * Busca elementos por fecha de compra
     * @param purchaseDate fecha de compra
     * @return lista de elementos encontrados
     * @throws java.sql.SQLException en caso de error durante la consulta
     */
    @Override
    public List<FixedAsset> findByPurchaseDate(Date purchaseDate) throws SQLException {
        return fixedAssetDao.findByPurchaseDate(purchaseDate);
    }

    /**
     * Busca elementos por serial
     * @param serial serial del activo
     * @return lista de elementos encontrados
     * @throws java.sql.SQLException en caso de error durante la consulta
     */
    @Override
    public List<FixedAsset> findBySerial(String serial) throws SQLException {
        return fixedAssetDao.findBySerial(serial);
    }
    
    /**
     * Método que valida elemento que será insertado
     * @param modelObject elemento recibido en la petición
     * @throws blacksip.generic.exception.FieldValidationException en caso de 
     * error de validación
     */
    private void validateInsert(FixedAsset modelObject) throws FieldValidationException{
        List<ValidationFieldError> validationErrors = new ArrayList();
        if(ValidateField.isNullOrEmpty(modelObject.getAssetOwnerId())){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_IS_REQUIRED, "assetOwnerId", "El campo de propietario del activo es requerido")
            );
        }
        if(ValidateField.isNullOrEmpty(modelObject.getAssetStatusId())){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_IS_REQUIRED, "assetStatusId", "El campo de estado del activo es requerido")
            );
        }
        if(ValidateField.isNullOrEmpty(modelObject.getAssetTypeId())){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_IS_REQUIRED, "assetTypeId", "El campo de tipo de activo es requerido")
            );
        }
        if(ValidateField.isNullOrEmpty(modelObject.getName())){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_IS_REQUIRED, "assetNameId", "El campo de nombre es requerido")
            );
        }
        if(ValidateField.isNullOrEmpty(modelObject.getSerial())){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_IS_REQUIRED, "serial", "El campo de serial es requerido")
            );
        }
        if(ValidateField.isNullOrEmpty(modelObject.getPurchaseDate())){
            validationErrors.add(
                    new ValidationFieldError(ApiStatus.FIELD_IS_REQUIRED, "purchaseDate", "El campo de fecha de compra es requerido")
            );
        }
        if(modelObject.getWithdrawalDate() != null && modelObject.getPurchaseDate() != null){
            if(modelObject.getWithdrawalDate().compareTo(modelObject.getPurchaseDate()) < 0 ){
                validationErrors.add(
                    new ValidationFieldError(ApiStatus.VALIDATION_ERROR, "withdrawalDate", "La fecha de baja debe ser posterior a la fecha de compra")
                );
            }
        }
        if(!validationErrors.isEmpty()){
            throw new FieldValidationException(validationErrors);
        }
    }
    
    /**
     * Método que valida elemento que será actualizado
     * @param modelObject elemento recibido en la petición
     * @throws blacksip.generic.exception.FieldValidationException en caso de 
     * error de validación
     */
    private void validateUpdate(FixedAsset modelObject) throws FieldValidationException{
        List<ValidationFieldError> validationErrors = new ArrayList();
        
        FixedAsset fixedAsset = fixedAssetDao.findById(modelObject.getFixedAssetId());
        if(fixedAsset == null){
            fixedAssetDao.throwNotFoundByPK(modelObject.getFixedAssetId());
        }else{
            if(modelObject.getSerial() != null){
                fixedAsset.setSerial(modelObject.getSerial());
            }
            if(modelObject.getWithdrawalDate() != null){
                fixedAsset.setWithdrawalDate(modelObject.getWithdrawalDate());
            }
            modelObject = fixedAsset;
            if(modelObject.getWithdrawalDate() != null && modelObject.getPurchaseDate() != null){
                if(modelObject.getWithdrawalDate().compareTo(modelObject.getPurchaseDate()) < 0 ){
                    validationErrors.add(
                        new ValidationFieldError(
                                ApiStatus.VALIDATION_ERROR, 
                                "withdrawalDate",
                                "La fecha de baja debe ser posterior a la fecha de compra"
                        )
                    );
                }
            }
        }
        if(!validationErrors.isEmpty()){
            throw new FieldValidationException(validationErrors);
        }
    }
    
}
