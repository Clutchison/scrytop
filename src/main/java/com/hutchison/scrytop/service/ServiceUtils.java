package com.hutchison.scrytop.service;

import org.springframework.stereotype.Component;

@Component
public class ServiceUtils {
    public static String likeifyString(String s) {
        return "%" + s + "%";
    }
}
