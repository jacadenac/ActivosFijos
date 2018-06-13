package asd.prueba.service;

import asd.prueba.model.AuthUser;
import java.sql.SQLException;

/**
 *
 * @author Alejandro Cadena
 */
public interface AuthUserBs extends InterfaceService<AuthUser>{
    public AuthUser findByUsername(String username, String password) throws SQLException;
}
