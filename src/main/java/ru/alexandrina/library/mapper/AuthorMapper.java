package ru.alexandrina.library.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.alexandrina.library.dto.AuthorDto;
import ru.alexandrina.library.entity.Author;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface AuthorMapper {

    @Mapping(target = "id", ignore = true)
    Author toEntity(AuthorDto authorDto);

    @Mapping(target = "id", ignore = true)
    void fillEntity(AuthorDto authorDto, @MappingTarget Author author);

    AuthorDto toDto(Author author);
}
