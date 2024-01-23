package ru.practicum.ewm.main.server.config.graphql;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.data.query.QuerydslDataFetcher;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.comment.repository.CommentRepository;
import ru.practicum.ewm.main.server.compilation.repository.CompilationRepository;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.location.repository.LocationRepository;
import ru.practicum.ewm.main.server.participationrequest.repository.ParticipationRequestRepository;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GraphQLConfig {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final GraphQLLocalDateTimeCoercing localDateTimeCoercing;
    private final LocationRepository locationRepository;
    private final CommentRepository commentRepository;
    private final CompilationRepository compilationRepository;
    private final ParticipationRequestRepository participationRequestRepository;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .type("Query", builder -> builder
                        .dataFetchers(Map.of(
                                "categories", QuerydslDataFetcher.builder(categoryRepository).many(),
                                "users", QuerydslDataFetcher.builder(userRepository).many(),
                                "events", QuerydslDataFetcher.builder(eventRepository).many(),
                                "locations", QuerydslDataFetcher.builder(locationRepository).many(),
                                "comments", QuerydslDataFetcher.builder(commentRepository).many(),
                                "compilations", QuerydslDataFetcher.builder(compilationRepository).many(),
                                "participationrequests", QuerydslDataFetcher.builder(participationRequestRepository).many()
                        ))
                )
                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(GraphQLScalarType.newScalar()
                        .name("LocalDateTime")
                        .description("GraphQL scalar that represents java.time.LocalDateTime.")
                        .coercing(localDateTimeCoercing)
                        .build());
    }

}