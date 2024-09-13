package ru.alexandrina.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.alexandrina.library.dto.PublisherDto;
import ru.alexandrina.library.entity.Publisher;
import ru.alexandrina.library.exception.BookNotFoundException;
import ru.alexandrina.library.exception.PublisherNotFoundException;
import ru.alexandrina.library.mapper.PublisherMapper;
import ru.alexandrina.library.repository.PublisherRepository;

import java.util.List;


@Service
public class PublisherService {

    private final PublisherRepository publisherRepository;
    private final PublisherMapper publisherMapper;

    @Autowired
    public PublisherService(PublisherRepository publisherRepository, PublisherMapper publisherMapper) {
        this.publisherRepository = publisherRepository;
        this.publisherMapper = publisherMapper;
    }

    public PublisherDto create(PublisherDto publisherDto) {
        return publisherMapper.toDto(publisherRepository.save(publisherMapper.toEntity(publisherDto)));
    }

    public PublisherDto update(Long id, PublisherDto genreDto) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        publisherMapper.fillEntity(genreDto, publisher);
        return publisherMapper.toDto(publisher);
    }

    public PublisherDto get(Long id) {
        return publisherMapper.toDto(publisherRepository.findById(id)
                .orElseThrow(() -> new PublisherNotFoundException(id)));
    }

    public PublisherDto delete(Long id) {
        Publisher publisher = publisherRepository.findById(id)
                .orElseThrow(() -> new BookNotFoundException(id));
        publisherRepository.delete(publisher);
        return publisherMapper.toDto(publisher);
    }

    public List<PublisherDto> list(String title) {
        return publisherRepository.findByTitle(title);
    }
}
