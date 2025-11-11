package ru.alexandrina.library.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.alexandrina.library.dto.BookRequestDto;
import ru.alexandrina.library.dto.BookResponseDto;
import ru.alexandrina.library.entity.Book;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING, uses = {AuthorMapper.class, PublisherMapper.class})
public interface BookMapper {

    Book toEntity(BookRequestDto bookRequestDto);

    void fillEntity(BookRequestDto bookRequestDto, @MappingTarget Book book);
    BookResponseDto toDto(Book book);
}
