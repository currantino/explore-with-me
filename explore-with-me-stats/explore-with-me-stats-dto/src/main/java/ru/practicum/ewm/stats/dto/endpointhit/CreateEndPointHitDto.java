package ru.practicum.ewm.stats.dto.endpointhit;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Builder;
import lombok.Data;
import ru.practicum.ewm.stats.dto.endpointhit.deserializer.CustomCreateEndPointHitDtoDeserializer;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
@Builder
@JsonDeserialize(using = CustomCreateEndPointHitDtoDeserializer.class)
public class CreateEndPointHitDto {
    @NotBlank(message = "App name cannot be blank or null.")
    private String app;

    @NotBlank(message = "Endpoint hit uri cannot be blank or null.")
    private String uri;

    @NotBlank(message = "Requester ip address cannot be blank or null.")
    private String ip;

    @NotNull(message = "Endpoint hit timestamp cannot be null.")
    private LocalDateTime timestamp;
}
