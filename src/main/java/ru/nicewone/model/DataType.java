package ru.nicewone.model;

import java.util.Map;

public record DataType(Long type, String eventRegExp, Map<String, String> data) {

}

