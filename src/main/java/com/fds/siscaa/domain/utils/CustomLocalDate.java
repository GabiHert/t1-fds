package com.fds.siscaa.domain.utils;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class CustomLocalDate {
    CustomLocalDate() {
    }

    public static LocalDate now() {
        return LocalDate.now();
    }
}
