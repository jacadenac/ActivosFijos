package asd.prueba.security;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.www.BasicAuthenticationEntryPoint;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alejandro Cadena
 */
@Component
public class AuthenticationEntryPoint extends BasicAuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authEx){
        PrintWriter writer = null;
        try {
            response.addHeader("Content-Type", "application/json; charset=utf-8");
            response.addHeader("Connection", "close");
            response.addHeader("WWW-Authenticate", "Basic realm=\"" + getRealmName() + "\"");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            JSONObject responseBody = new JSONObject();
            responseBody.put("error", "HTTP Status 401 - " + authEx.getMessage());
            writer = response.getWriter();
            writer.println(responseBody.toString());
        } catch (IOException ex) {
            Logger.getLogger(AuthenticationEntryPoint.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if(writer != null){
                writer.close();
            }
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        setRealmName("DeveloperStack");
        super.afterPropertiesSet();
    }

}