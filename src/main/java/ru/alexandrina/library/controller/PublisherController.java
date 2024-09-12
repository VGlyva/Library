package ru.alexandrina.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrina.library.dto.PublisherDto;
import ru.alexandrina.library.service.PublisherService;

import java.util.List;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("publisher")
@Tag(name = "Издательства", description = "CRUD методы для работы с издательствами")
public class PublisherController {

    private final PublisherService publisherService;

    @Autowired
    public PublisherController(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @Operation(summary = "Добавление издательства")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "издательство успешно добавлено"),
                    @ApiResponse(responseCode = "400", description = "Название издательства пустое"),
                    @ApiResponse(responseCode = "409", description = "издательство с таким названием уже есть в БД"),
            }
    )
    @PostMapping
    public ResponseEntity<PublisherDto> create(@RequestBody @Valid PublisherDto publisherDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(publisherService.create(publisherDto));
    }


    @Operation(summary = "Обновление издательства")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Издательство успешно добавлено"),
                    @ApiResponse(responseCode = "400", description = "Название Издательства пустое"),
                    @ApiResponse(responseCode = "404", description = "Издательство с таким id не найдено"),
            }
    )
    @PutMapping("/{id}")
    public PublisherDto update(@PathVariable Long id,
                           @RequestBody @Valid PublisherDto genreDto) {
        return publisherService.update(id, genreDto);
    }

    @Operation(summary = "Получение издательства по id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Издательство успешно найдено"),
                    @ApiResponse(responseCode = "404", description = "Издательство с таким id не найдено"),
            }
    )
    @GetMapping("/{id}")
    public PublisherDto get(@PathVariable Long id) {
        return publisherService.get(id);
    }

    @Operation(summary = "Удаление издательства по id")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Издательство успешно удалено"),
                    @ApiResponse(responseCode = "404", description = "Издательство с таким id не найдено"),
            }
    )
    @DeleteMapping("/{id}")
    public PublisherDto delete(@PathVariable Long id) {
        return publisherService.delete(id);
    }

    @Operation(summary = "Получение списка издательств, отфильтрованных по опциональному параметру")
    @ApiResponses(
            value = {
                    @ApiResponse(responseCode = "201", description = "Издательство успешно найдено"),
            }
    )
    @GetMapping
    public List<PublisherDto> list(@RequestParam(required = false, value = "title") String title) {
        if (title == null || title.isEmpty()) {
            title = null;
        }
        return publisherService.list(title);
    }
}
