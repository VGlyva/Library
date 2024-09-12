package ru.alexandrina.library.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;
import ru.alexandrina.library.dto.PublisherDto;
import ru.alexandrina.library.entity.Publisher;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface PublisherMapper {

    @Mapping(target = "id", ignore = true)
    public Publisher toEntity(PublisherDto genreDto);

    @Mapping(target = "id", ignore = true)
    void fillEntity(PublisherDto genreDto, @MappingTarget Publisher genre);

    PublisherDto toDto(Publisher publisher);
}
