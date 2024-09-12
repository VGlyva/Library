package ru.alexandrina.library.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.alexandrina.library.entity.Author;
import ru.alexandrina.library.service.AuthorService;

@RestController
@RequestMapping("author")
@RequiredArgsConstructor
public class AuthorController {

    @Autowired
    private final AuthorService authorService;

    @Operation(
            summary = "Получение информации об авторе",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найден автор"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Автор не найден"
                    )},
            tags = "Авторы"
    )

    @GetMapping("{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable int id) {
        Author author = authorService.findAuthor(id);
        if (author == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(author);
    }

    @Operation(
            summary = "Добавление автора",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Author.class
                            )
                    )}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Автор добавлен"
                    ),

            },
            tags = "Авторы"
    )

    @PostMapping()
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {
        Author createAuthor = authorService.addAuthor(author);
        return ResponseEntity.ok(createAuthor);
    }
    @Operation(summary = "Обновление автора",
            responses = {
                    @ApiResponse(
                            responseCode = "200",

                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Author.class)
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Автор не найден"
                    )
            },
            tags = "Авторы")

    @PutMapping("{id}")
    public ResponseEntity<Author> editAuthor(@RequestBody Author author) {
        Author foundAuthor = authorService.editAuthor(author);
        if (foundAuthor == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundAuthor);
    }

    @Operation(summary = "Удаление автора",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Автор не найден"
                    )
            },
            tags = "Авторы")

    @DeleteMapping("{id}")
    public void deleteAuthor(@PathVariable int id) {
        authorService.removeAuthor(id);
    }
}
