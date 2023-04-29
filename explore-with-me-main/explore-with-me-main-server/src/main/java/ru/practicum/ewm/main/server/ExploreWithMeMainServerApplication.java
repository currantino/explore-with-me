package ru.practicum.ewm.main.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import ru.practicum.ewm.main.server.event.repository.CustomEventRepositoryImpl;

@SpringBootApplication
@EnableJpaRepositories(repositoryBaseClass = CustomEventRepositoryImpl.class)
public class ExploreWithMeMainServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExploreWithMeMainServerApplication.class, args);
    }
}
