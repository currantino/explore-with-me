package ru.practicum.ewm.main.server.comment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class CreateCommentDto {

    @NotBlank(message = "Comment text must not be blank or null.")
    @Size(max = 2047, message = "Comment text length must be less than 2048 symbols.")
    String text;

    public CreateCommentDto(
            @JsonProperty("text")
            String text
    ) {
        this.text = text;
    }
}
