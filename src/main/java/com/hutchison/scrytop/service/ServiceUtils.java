package com.hutchison.scrytop.service;

import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Component
public class ServiceUtils {
    public static String likeifyString(String s) {
        return "%" + s + "%";
    }

    public static List<String> loadCardList(String relativePath) {
        try {
            // cubeLists/vintage_fall_2020.txt
            InputStream inputStream = new ClassPathResource(relativePath).getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            List<String> s = new ArrayList<>();
            while (reader.ready()) {
                s.add(reader.readLine());
            }
            return s;
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("Error attempting to read local card list.");
        }
    }
}
