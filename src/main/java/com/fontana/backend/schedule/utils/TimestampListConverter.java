package com.fontana.backend.schedule.utils;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Converter
public class TimestampListConverter implements AttributeConverter<List<Timestamp>, String> {

    private static final String TIMESTAMP_SEPARATOR = ",";

    @Override
    public String convertToDatabaseColumn(List<Timestamp> attribute) {
        return attribute.stream()
                .map(timestamp -> Long.toString(timestamp.getTime()))
                .collect(Collectors.joining(TIMESTAMP_SEPARATOR));
    }

    @Override
    public List<Timestamp> convertToEntityAttribute(String dbData) {
        return Arrays.stream(dbData.split(TIMESTAMP_SEPARATOR))
                .map(Long::parseLong)
                .map(Timestamp::new)
                .collect(Collectors.toList());
    }
}
