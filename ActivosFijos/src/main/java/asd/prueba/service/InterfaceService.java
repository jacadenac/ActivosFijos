package asd.prueba.service;

import asd.prueba.exception.FieldValidationException;
import asd.prueba.utils.Page;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Alejandro Cadena
 * @param <Model> Model
 */
public interface InterfaceService<Model> {
    
    Model findById(Object pk);
    List<Model> findAll();
    Page<Model> findAllWithPagination(final int pageNo, final int pageSize) throws SQLException;
    void insert(Model modelObject) throws FieldValidationException, SQLException;
    void update(Object pk, Model modelObject) throws SQLException, FieldValidationException, Exception;
    void delete(Model modelObject) throws SQLException;
    
}