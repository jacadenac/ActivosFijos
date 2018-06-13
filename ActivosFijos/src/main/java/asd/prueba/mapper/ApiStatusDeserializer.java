package asd.prueba.mapper;

import asd.prueba.utils.ApiStatus;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;

import java.io.IOException;
import org.springframework.stereotype.Component;

/**
 *
 * @author Alejandro Cadena
 */
@Component
public class ApiStatusDeserializer extends JsonDeserializer<ApiStatus> {
    
    @Override
    public ApiStatus deserialize(JsonParser jp, DeserializationContext ctxt)
            throws IOException, JsonProcessingException {
        String appStatusCode = jp.getText();
        return ApiStatus.fromCode(appStatusCode);
    }

}
