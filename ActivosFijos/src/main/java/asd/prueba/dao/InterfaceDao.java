package asd.prueba.dao;

import asd.prueba.utils.Page;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alejandro Cadena
 * @param <Model>
 */
public interface InterfaceDao<Model> {
    
    public Model findById(Object pk);
    public List<Model> findAll();
    public Page<Model> findAllWithPagination(int pageNo, int pageSize) throws SQLException;
    public void insert(Model modelObject) throws SQLException;   
    public void update(Model modelObject) throws SQLException;
    public void delete(Model modelObject) throws SQLException;
    
}
