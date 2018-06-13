package asd.prueba.security;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Clase para obtener datos de sesión
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@Component
public class SpringAuthSession {
    
    /**
     * Método que retorna el nombre del usuario que se encuentra autenticado
     * @return nombre del usuario autenticado
     */
    public String getUserName(){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if(auth == null){
            return "Usuario no autenticado";
        }else if(auth.isAuthenticated()){
            return auth.getName();
        }else{
            throw new AccessDeniedException("Access Denied Exception");
        }
    }
    
}
