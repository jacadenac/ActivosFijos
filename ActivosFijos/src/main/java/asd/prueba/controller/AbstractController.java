package asd.prueba.controller;

import asd.prueba.exception.ModelMappingException;
import asd.prueba.mapper.CustomObjectMapper;
import java.io.IOException;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alejandro Cadena
 * @param <Model>
 * @Version 1.0
 */
@Component
public abstract class AbstractController<Model> {
    
    /**
     * Modelo sobre el cual se aplica el controlador rest
     */
    protected final Class<Model> classModel;
    
    /**
     * Objeto personalizado para el mapeo json/objeto
     */
    protected CustomObjectMapper customObjectMapper;
    
    /**
     * Número máximo de elementos por página por defecto
     */
    protected static final int DEFAULT_PAGE_SIZE=100;
    
    private static final Logger LOGGER = LogManager.getLogger(AbstractController.class);

    public AbstractController() {
        this.classModel = (Class<Model>) GenericTypeResolver.resolveTypeArgument(getClass(), AbstractController.class);
    }
    
    /**
     * Se inyecta instancia del objeto que permite mapear objetos a json
     * @param customObjectMapper 
     */
    @Autowired
    protected void setCustomObjectMapper(CustomObjectMapper customObjectMapper) {
        this.customObjectMapper = customObjectMapper;
    }
    
    /**
     * Cabeceras Http por defecto
     */
    protected HttpHeaders getDefaultHeaders(){
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
        return httpHeaders;        
    }
    
    /**
     * 
     * @param requestBody
     * @return
     * @throws ModelMappingException 
     */
    protected Model mapperToModel(String requestBody) throws ModelMappingException{
        try {
            return customObjectMapper.readValue(requestBody, classModel);
        } catch (IOException ex) {
            ModelMappingException mmEx = new ModelMappingException(ex, classModel);
            LOGGER.error(mmEx.getMessage());
            throw mmEx;
        }
    }
    
    /**
     * 
     * @param object
     * @return
     * @throws ModelMappingException 
     */
    protected String mapperToJson(Object object) throws ModelMappingException{
        try {
            if(object == null){
                return null;
            }else if(! (object instanceof String) ){
                return  customObjectMapper.writeValueAsString(object);
            }
            return String.valueOf(object);
        } catch (IOException ex) {
            ModelMappingException mmEx = new ModelMappingException(ex, classModel);
            LOGGER.error(mmEx.getMessage());
            throw mmEx;
        }
    }
    
    /**
     * 
     * @param requestBody
     * @return
     * @throws ModelMappingException 
     */
    protected List<Model> mapperToListModel(String requestBody) throws ModelMappingException{
        try {
            return customObjectMapper.readValue(
                    requestBody, 
                    customObjectMapper.getTypeFactory().constructCollectionType(List.class, classModel)
            );
        } catch (IOException ex) {
            ModelMappingException mmEx = new ModelMappingException(ex, classModel);
            LOGGER.error(mmEx.getMessage());
            throw mmEx;
        }
    }
    
}
