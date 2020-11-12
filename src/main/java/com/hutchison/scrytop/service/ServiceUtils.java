package com.hutchison.scrytop.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@Component
public class ServiceUtils {
    public static String likeifyString(String s) {
        return "%" + s + "%";
    }

    public static List<String> loadCardList(String relativePath) {
        try {
            // cubeLists/vintage_fall_2020.txt
            ClassPathResource classPathResource = new ClassPathResource(relativePath);
            return Files.readAllLines(classPathResource.getFile().toPath());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error attempting to read local card list.");
        }
    }
}
