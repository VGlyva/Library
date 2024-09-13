package ru.alexandrina.library.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.alexandrina.library.dto.PublisherDto;
import ru.alexandrina.library.entity.Publisher;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PublisherRepository extends JpaRepository<Publisher, Long> {
    @Query("SELECT new ru.alexandrina.library.dto.PublisherDto(id, title) FROM Publisher WHERE title like %:title%")
    List<PublisherDto> findByTitle(@Param("title") String title);
}
