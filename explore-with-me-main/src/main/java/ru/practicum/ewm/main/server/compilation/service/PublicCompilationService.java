package ru.practicum.ewm.main.server.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.mapper.CompilationMapper;
import ru.practicum.ewm.main.server.compilation.repository.CompilationRepository;
import ru.practicum.ewm.main.server.exception.CompilationNotFoundException;

@Service
@RequiredArgsConstructor
public class PublicCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;

    public CompilationDto getCompilationById(Long compId) {
        return compilationRepository.findById(compId)
                .map(compilationMapper::toDto)
                .orElseThrow(() -> new CompilationNotFoundException("Could not find the requested compilation."));
    }


    public Page<CompilationDto> getCompilations(
            Boolean pinned,
            Integer from,
            Integer size
    ) {
        Pageable pageRequest = PageRequest.of(from, size);
        return compilationRepository
                .findAllByPinned(pinned, pageRequest)
                .map(compilationMapper::toDto);
    }
}
