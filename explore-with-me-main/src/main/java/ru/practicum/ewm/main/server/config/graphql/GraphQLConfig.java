package ru.practicum.ewm.main.server.config.graphql;

import graphql.scalars.ExtendedScalars;
import graphql.schema.GraphQLScalarType;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.graphql.data.query.QuerydslDataFetcher;
import org.springframework.graphql.execution.RuntimeWiringConfigurer;
import ru.practicum.ewm.main.server.category.repository.CategoryRepository;
import ru.practicum.ewm.main.server.event.repository.EventRepository;
import ru.practicum.ewm.main.server.user.repository.UserRepository;

import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class GraphQLConfig {
    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;
    private final EventRepository eventRepository;
    private final GraphQLLocalDateTimeCoercing localDateTimeCoercing;

    @Bean
    public RuntimeWiringConfigurer runtimeWiringConfigurer() {
        return wiringBuilder -> wiringBuilder
                .type("Query", builder -> builder
                        .dataFetchers(Map.of(
                                "categories", QuerydslDataFetcher.builder(categoryRepository).many(),
                                "users", QuerydslDataFetcher.builder(userRepository).many(),
                                "events", QuerydslDataFetcher.builder(eventRepository).many()
                        ))
                )

                .scalar(ExtendedScalars.GraphQLLong)
                .scalar(GraphQLScalarType.newScalar()
                        .name("LocalDateTime")
                        .description("GraphQL scalar that represents java.time.LocalDateTime.")
                        .coercing(localDateTimeCoercing)
                        .build());
/*
                        .dataFetcher("categories",
                                QuerydslDataFetcher.builder(categoryRepository).many())
                        .dataFetcher("users")
*/
    }
}

/*
    private final DataFetcher<Iterable<Category>> categoriesDataFetcher = environment -> {
        Object idsArgument = environment.getArgument("ids");
        if (idsArgument instanceof List) {
            List idsList = (List) idsArgument;
            if (idsList.isEmpty()) {
                return categoryRepository.findAll();
            }
            if (idsList.get(0) instanceof Long) {
                List<Long> ids = (List<Long>) idsList;
                return categoryRepository.findAllById(ids);
            }
        }
        throw new ValidationException("Ids argument must have type [Long].");
    };
*/
/*
        var customizer = new QuerydslBinderCustomizer<QCategory>() {
            @Override
            public void customize(QuerydslBindings bindings, QCategory root) {
                bindings.bind(root.id)
                        .all((rootId, ids) -> Optional.ofNullable(rootId.in(ids)));
            }
        };
*/
