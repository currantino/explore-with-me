package ru.practicum.ewm.main.server.compilation.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.practicum.ewm.main.server.compilation.controller.UpdateCompilationRequestDto;
import ru.practicum.ewm.main.server.compilation.dto.CompilationDto;
import ru.practicum.ewm.main.server.compilation.dto.NewCompilationDto;
import ru.practicum.ewm.main.server.compilation.dto.mapper.CompilationMapper;
import ru.practicum.ewm.main.server.compilation.dto.mapper.UpdateCompilationMapper;
import ru.practicum.ewm.main.server.compilation.entity.Compilation;
import ru.practicum.ewm.main.server.compilation.repository.CompilationRepository;
import ru.practicum.ewm.main.server.exception.CompilationNotFoundException;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
public class AdminCompilationService {
    private final CompilationRepository compilationRepository;
    private final CompilationMapper compilationMapper;
    private final UpdateCompilationMapper updateCompilationMapper;

    public CompilationDto createCompilation(NewCompilationDto compilationDto) {
        Compilation compilation = compilationMapper.toEntity(compilationDto);

        return compilationMapper
                .toDto(
                        compilationRepository
                                .save(compilation)
                        //TODO: ADD REFRESH
                );
    }

    @Transactional
    public void deleteCompilationById(Long compId) {
        int deleted = compilationRepository.deleteByIdAndCountDeleted(compId);
        if (deleted != 1) {
            throw new CompilationNotFoundException("Could not find the requested compilation.");
        }
    }

    public CompilationDto patchCompilation(
            Long compId,
            UpdateCompilationRequestDto updateRequestDto
    ) {
        Compilation compilation = compilationRepository.findById(compId)
                .orElseThrow(() -> new CompilationNotFoundException("Could not find the requested compilation."));
        Compilation updated = updateCompilationMapper.partialUpdate(updateRequestDto, compilation);
        Compilation saved = compilationRepository.save(updated);
        return compilationMapper.toDto(saved);
    }
}
