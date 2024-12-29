package com.hellspawn287.user.model;

import jakarta.persistence.AttributeConverter;
import jakarta.validation.constraints.NotNull;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Optional;

import static java.sql.Timestamp.valueOf;

public abstract class BaseMapper implements AttributeConverter<LocalDateTime, Timestamp> {
    @Override
    public Timestamp convertToDatabaseColumn(@NotNull LocalDateTime localDateTime) {
        return valueOf(Timestamp.valueOf(localDateTime).toString());
    }

    @Override
    public LocalDateTime convertToEntityAttribute(Timestamp timestamp) {
        return Optional.of(timestamp).map(Timestamp::toLocalDateTime).orElse(null);
    }
}
