package asd.prueba.service;

import asd.prueba.model.FixedAsset;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Alejandro Cadena
 */
public interface FixedAssetBs extends InterfaceService<FixedAsset>{
    List<FixedAsset> findByAssetTypeId(Long assetTypeId) throws SQLException;
    List<FixedAsset> findByPurchaseDate(Date purchaseDate) throws SQLException;
    List<FixedAsset> findBySerial(String serial) throws SQLException;
}
