package com.br.webshop.purchase.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJsonFile(String path, Class<T> clazz) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(path));
        return objectMapper.readValue(jsonData, clazz);
    }
}