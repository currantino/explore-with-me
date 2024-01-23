package ru.practicum.ewm.main.server.config.stats;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.practicum.ewm.stats.client.endpointhit.EndPointHitClient;

@Configuration
public class StatsConfig {
    @Bean
    public EndPointHitClient endPointHitClient(
            @Value("${stats-server.url}")
            String statsServerUrl,
            @Autowired
            RestTemplateBuilder builder
    ) {
        return new EndPointHitClient(statsServerUrl, builder);
    }
}
