package ru.practicum.ewm.stats.client.viewstats;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stats.client.exception.ViewStatsException;
import ru.practicum.ewm.stats.dto.viewstats.ViewStats;

import java.time.LocalDateTime;
import java.util.List;

@Component
@RequiredArgsConstructor
public class ViewStatsClient {
    private static final String API_PREFIX = "/stats";
    private final RestTemplate rest;

    public ViewStatsClient(
            @Value("${stats-server.url}")
            String serverUrl,
            RestTemplateBuilder builder) {
        rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .build();
    }

    public List<ViewStats> viewStats(
            LocalDateTime start,
            LocalDateTime end,
            List<String> uris,
            Boolean unique
    ) {
        RequestEntity<Void> request = RequestEntity
                .get("?start={start}&end={end}&uris={uris}&unique={unique}",
                        start, end, uris, unique)
                .accept(MediaType.APPLICATION_JSON)
                .build();
        ResponseEntity<List<ViewStats>> response = rest.exchange(request, new ParameterizedTypeReference<List<ViewStats>>() {
        });
        if (response.getStatusCode().isError()) {
            throw new ViewStatsException("Couldn't load stats.");
        }
        return response.getBody();
    }
}
