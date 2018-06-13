package asd.prueba.dao;

import asd.prueba.model.FixedAsset;
import java.util.Date;
import java.util.List;


/**
 *
 * @author JuanDavid
 */
public interface FixedAssetDao extends InterfaceDao<FixedAsset>{
    
    List<FixedAsset> findByAssetTypeId(Long assetTypeId);
    List<FixedAsset> findByPurchaseDate(Date purchaseDate);
    List<FixedAsset> findBySerial(String serial);
    void throwNotFoundByPK(Object pk);
    
}
