package ru.practicum.ewm.main.server.exceptionhandler;

import graphql.GraphQLError;
import graphql.GraphqlErrorBuilder;
import graphql.schema.DataFetchingEnvironment;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.graphql.execution.DataFetcherExceptionResolverAdapter;
import org.springframework.graphql.execution.ErrorType;
import org.springframework.stereotype.Component;
import ru.practicum.ewm.main.server.exception.DataNotFoundException;

import javax.validation.ValidationException;

@Component
public class GraphqlCustomExceptionHandler extends DataFetcherExceptionResolverAdapter {

    @Override
    protected GraphQLError resolveToSingleError(
            Throwable e,
            DataFetchingEnvironment env
    ) {
        GraphqlErrorBuilder errorBuilder = GraphqlErrorBuilder
                .newError(env)
                .message(e.getMessage());
        if (e instanceof DataNotFoundException) {
            return errorBuilder
                    .errorType(ErrorType.NOT_FOUND)
                    .build();
        } else if (e instanceof EmptyResultDataAccessException) {
            return errorBuilder
                    .errorType(ErrorType.NOT_FOUND)
                    .build();
        } else if (e instanceof ValidationException) {
            return errorBuilder
                    .errorType(ErrorType.BAD_REQUEST)
                    .build();
        }
        return errorBuilder
                .build();
    }
}