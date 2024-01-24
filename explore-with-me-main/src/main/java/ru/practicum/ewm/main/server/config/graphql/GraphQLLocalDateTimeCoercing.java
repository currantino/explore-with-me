package ru.practicum.ewm.main.server.config.graphql;

import graphql.language.StringValue;
import graphql.schema.Coercing;
import graphql.schema.CoercingParseLiteralException;
import graphql.schema.CoercingParseValueException;
import graphql.schema.CoercingSerializeException;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static java.lang.String.format;

@Component
public class GraphQLLocalDateTimeCoercing implements Coercing<LocalDateTime, String> {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");


    @Override
    public String serialize(Object input) {
        if (!(input instanceof LocalDateTime)) {
            throw new CoercingSerializeException(getErrorMessage(input));
        }
        return formatter.format((LocalDateTime) input);
    }


    @Override
    public LocalDateTime parseValue(Object input) {
        if (!(input instanceof String)) {
            throw new CoercingParseValueException(getErrorMessage(input));
        }
        try {
            return LocalDateTime.parse(input.toString(), formatter);
        } catch (DateTimeParseException e) {
            throw new CoercingParseValueException(getErrorMessage(input));
        }
    }

    @Override
    public LocalDateTime parseLiteral(Object input) {
        if (!(input instanceof StringValue)) {
            throw new CoercingParseLiteralException(
                    format("Expected AST type 'StringValue' but was '%s'",
                           input.getClass().getSimpleName())
            );
        }
        try {
            String value = ((StringValue) input).getValue();
            return LocalDateTime.parse(value, formatter);
        } catch (DateTimeParseException e) {
            throw new CoercingParseLiteralException(getErrorMessage(input));
        }
    }

    private static String getErrorMessage(Object input) {
        return format("Invalid input: %s", input.toString());
    }
}
