package ru.practicum.ewm.stats.client.endpointhit;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.util.DefaultUriBuilderFactory;
import ru.practicum.ewm.stats.client.base.BaseClient;
import ru.practicum.ewm.stats.dto.endpointhit.CreateEndPointHitDto;

import javax.servlet.http.HttpServletRequest;

import static java.time.LocalDateTime.now;

@Component
public class EndPointHitClient extends BaseClient {
    private static final String API_PREFIX = "/hit";

    @Autowired
    public EndPointHitClient(
            @Value("${stats-server.url}")
            String serverUrl,
            RestTemplateBuilder builder
    ) {
        super(builder
                .uriTemplateHandler(new DefaultUriBuilderFactory(serverUrl + API_PREFIX))
                .requestFactory(HttpComponentsClientHttpRequestFactory.class)
                .build());
    }

    public void createEndPointHit(HttpServletRequest request) {
        CreateEndPointHitDto body = CreateEndPointHitDto.builder()
                .app("explore-with-me")
                .uri(request.getRequestURI())
                .timestamp(now())
                .ip(request.getRemoteAddr())
                .build();
        post(body, Strings.EMPTY);
    }
}
