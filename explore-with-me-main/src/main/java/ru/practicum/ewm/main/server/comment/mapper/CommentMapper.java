package ru.practicum.ewm.main.server.comment.mapper;

import org.mapstruct.*;
import ru.practicum.ewm.main.server.comment.dto.CommentDto;
import ru.practicum.ewm.main.server.comment.dto.CreateCommentDto;
import ru.practicum.ewm.main.server.comment.dto.UpdateCommentDto;
import ru.practicum.ewm.main.server.comment.entity.Comment;
import ru.practicum.ewm.main.server.config.mapstruct.CentralMapperConfig;

@Mapper(config = CentralMapperConfig.class)
public interface CommentMapper {

    @Mapping(target = "eventId", source = "event.id")
    CommentDto toDto(Comment comment);

    Comment toEntity(CreateCommentDto commentDto);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Comment partialUpdate(
            UpdateCommentDto commentDto,
            @MappingTarget
            Comment comment
    );
}
