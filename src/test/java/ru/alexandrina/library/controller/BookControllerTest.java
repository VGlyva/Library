package ru.alexandrina.library.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import ru.alexandrina.library.base.Generator;
import ru.alexandrina.library.dto.AuthorDto;
import ru.alexandrina.library.dto.BookRequestDto;
import ru.alexandrina.library.dto.BookResponseDto;
import ru.alexandrina.library.dto.PublisherDto;
import ru.alexandrina.library.entity.Author;
import ru.alexandrina.library.entity.Publisher;
import ru.alexandrina.library.mapper.AuthorMapper;
import ru.alexandrina.library.mapper.PublisherMapper;
import ru.alexandrina.library.repository.AuthorRepository;
import ru.alexandrina.library.repository.BookRepository;
import ru.alexandrina.library.repository.PublisherRepository;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@AutoConfigureMockMvc
@SpringBootTest
@ActiveProfiles(profiles = {"h2-database"})
public class BookControllerTest extends Generator {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private AuthorRepository authorRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PublisherRepository publisherRepository;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private PublisherMapper publisherMapper;

    private final Faker faker = new Faker();

    @BeforeEach
    public void beforeEach() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/generate/authors").param("count", "100"))
                .andExpect(MockMvcResultMatchers.status().isOk());
        mockMvc.perform(MockMvcRequestBuilders.post("/generate/publishers").param("count", "10"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @AfterEach
    public void afterEach() {
        publisherRepository.deleteAll();
        authorRepository.deleteAll();
        bookRepository.deleteAll();
    }

    @Test
    void createTest() throws Exception {
        List<Author> authors = authorRepository.findAll(PageRequest.of(0, 10)).getContent();
        List<Publisher> genres = publisherRepository.findAll(PageRequest.of(0, 10)).getContent();

        int authorsCount = faker.random().nextInt(1, 3);
        int genresCount = faker.random().nextInt(1, 5);

        List<AuthorDto> authorDtos = authors.stream()
                .limit(authorsCount)
                .map(author -> authorMapper.toDto(author))
                .toList();

        List<PublisherDto> genreDtos = genres.stream()
                .limit(genresCount)
                .map(genre -> publisherMapper.toDto(genre))
                .toList();

        BookRequestDto bookRequestDto = generateBookRequestDto();

        bookRequestDto.setAuthorsIds(
                authorDtos.stream()
                        .map(AuthorDto::getId)
                        .collect(Collectors.toList())
        );
        bookRequestDto.setPublishersIds(
                genreDtos.stream()
                        .map(PublisherDto::getId)
                        .collect(Collectors.toList())
        );
        mockMvc.perform(MockMvcRequestBuilders.post("/book")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(bookRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(result -> {
                    BookResponseDto bookResponseDto = objectMapper.readValue(
                            result.getResponse().getContentAsString(),
                            BookResponseDto.class
                    );
                    assertThat(bookResponseDto.getYear()).isEqualTo(bookRequestDto.getYear());
                    assertThat(bookResponseDto.getPages()).isEqualTo(bookRequestDto.getPages());
                    assertThat(bookResponseDto.getTitle()).isEqualTo(bookRequestDto.getTitle());
                    assertThat(bookResponseDto.getPublishers())
                            .usingRecursiveComparison()
                            .ignoringCollectionOrder()
                            .isEqualTo(genreDtos);
                    assertThat(bookResponseDto.getAuthors())
                            .usingRecursiveComparison()
                            .ignoringCollectionOrder()
                            .isEqualTo(authorDtos);
                });
    }
}
