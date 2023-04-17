package ru.practicum.ewm.stats.client.endpointhit;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stats.client.exception.EndPointHitException;
import ru.practicum.ewm.stats.dto.endpointhit.CreateEndPointHitDto;

import java.net.URI;

@RequiredArgsConstructor
public class EndPointHitClient {
    private static final String API_PREFIX = "/hit";
    private final RestTemplate rest;

    public EndPointHitClient(
            @Value("${stats-server.url}")
            String serverUrl,
            RestTemplateBuilder builder
    ) {
        this.rest = builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .build();
    }

    public void createEndPointHit(
            @RequestBody
            CreateEndPointHitDto createEndPointHitDto
    ) {
        RequestEntity<CreateEndPointHitDto> request = RequestEntity
                .post(URI.create(API_PREFIX))
                .contentType(MediaType.APPLICATION_JSON)
                .body(createEndPointHitDto);

        ResponseEntity<Void> response = rest.exchange(request, Void.class);
        if (response.getStatusCode().isError()) {
            throw new EndPointHitException("Couldn't save endpoint hit.");
        }
    }
}
