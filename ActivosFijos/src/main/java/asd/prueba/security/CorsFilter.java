package asd.prueba.security;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * Clase para gestión de acceso por dominios
 * 
 * @author Alejandro Cadena
 * @version 1.0
 */
@Component
public class CorsFilter extends OncePerRequestFilter {

    /**
     * Establece access allow (metodo options)
     * @param request petición realizada por el cliente
     * @param response respuesta asociada a la petición
     * @param filterChain filtro que se aplicará sobre la petición
     * @throws ServletException en caso de error del servlert
     * @throws IOException en caso de error aplicanto filtro
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        if (request.getHeader("Access-Control-Request-Method") != null && "OPTIONS".equals(request.getMethod())) {
            response.addHeader("Access-Control-Allow-Origin", "*");
            response.addHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
            response.addHeader("Access-Control-Allow-Headers", "*");
            response.addHeader("Access-Control-Allow-Headers", "Authorization");
            response.addHeader("Access-Control-Allow-Headers", "Content-Type");
        }
        filterChain.doFilter(request, response);
    }
    
}
