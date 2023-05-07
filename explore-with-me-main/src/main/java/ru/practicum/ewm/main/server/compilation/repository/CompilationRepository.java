package ru.practicum.ewm.main.server.compilation.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import ru.practicum.ewm.main.server.compilation.entity.Compilation;

@Repository
public interface CompilationRepository extends JpaRepository<Compilation, Long> {
    @Modifying
    @Query("DELETE " +
            "FROM " +
            "   Compilation c " +
            "WHERE " +
            "   c.id = :compId")
    int deleteByIdAndCountDeleted(Long compId);

    @Query("SELECT " +
            "   c " +
            "FROM " +
            "   Compilation c " +
            "WHERE " +
            "   c.pinned = :pinned")
    Page<Compilation> findAllByPinned(
            Boolean pinned,
            Pageable pageRequest
    );
}