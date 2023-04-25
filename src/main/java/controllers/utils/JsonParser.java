package controllers.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;

public class JsonParser {
    public static final ObjectMapper mapper = new ObjectMapper();

    private JsonParser(){

    }

    @SneakyThrows
    public static String asString(String classPath){
        return Files.readString(Path.of((Objects.requireNonNull(JsonParser.class.getClassLoader().getResource(classPath))).toURI()));
    }

    @SneakyThrows
    public static ObjectNode createBody(String classPath){
        return mapper.readValue(asString(classPath), ObjectNode.class);
    }

//    @SneakyThrows
//    public static <T> T createBody(String classPath, Class<T> valueType){
//        return mapper.readValue(asString(classPath), valueType);
//    }

}
