package com.fds.siscaa.domain.utils;

import java.time.LocalDate;

import org.springframework.stereotype.Component;

@Component
public class CustomLocalDate {
    private static LocalDate mockedDate;

    CustomLocalDate() {
    }

    public static void mock(LocalDate date) {
        mockedDate = date;
    }

    public static LocalDate now() {
        return mockedDate != null ? mockedDate : LocalDate.now();
    }

    public static void reset() {
        mockedDate = null;
    }
}
