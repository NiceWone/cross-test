package ru.nicewone.model;

import java.time.LocalDateTime;
import java.util.Map;

public record DataTypeOut(Long type, LocalDateTime dateTime, Map<String, String> data) {

}
