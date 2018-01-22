package serialization;

import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;

public class SerializationUtils {

    public static String serializeToJson(Object obj) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
       return mapper.writeValueAsString(obj);

    }
}
