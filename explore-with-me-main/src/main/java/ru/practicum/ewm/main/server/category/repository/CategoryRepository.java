package ru.practicum.ewm.main.server.category.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.graphql.data.GraphQlRepository;
import ru.practicum.ewm.main.server.category.entity.Category;

@GraphQlRepository
public interface CategoryRepository extends JpaRepository<Category, Long>, QuerydslPredicateExecutor<Category> {
}
