package ru.practicum.ewm.stats.dto.endpointhit.deserializer;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.JsonNode;
import ru.practicum.ewm.stats.dto.endpointhit.CreateEndPointHitDto;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomCreateEndPointHitDtoDeserializer extends JsonDeserializer<CreateEndPointHitDto> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public CreateEndPointHitDto deserialize(JsonParser jp,
                                            DeserializationContext paramDeserializationContext)
            throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec()
                .readTree(jp);
        String app = node.get("app")
                .asText();
        String uri = node.get("uri")
                .asText();
        String ip = node.get("ip")
                .asText();
        String timestamp = node.get("timestamp")
                .asText();

        return CreateEndPointHitDto.builder()
                .app(app)
                .uri(uri)
                .ip(ip)
                .timestamp(LocalDateTime.parse(timestamp, FORMATTER))
                .build();

    }

}
