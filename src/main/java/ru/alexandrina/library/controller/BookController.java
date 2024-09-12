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
import ru.alexandrina.library.entity.Book;
import ru.alexandrina.library.service.BookService;

@RestController
@RequestMapping("book")
@RequiredArgsConstructor
public class BookController {

    @Autowired
    private final BookService bookService;

    @Operation(
            summary = "Получение информации о книге",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найдена книга"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Книга не найдена"
                    )},
            tags = "Книги"
    )

    @GetMapping("{id}")
    public ResponseEntity<Book> getBook(@PathVariable int id) {
        Book book = bookService.findBook(id);
        if (book == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(book);
    }

    @Operation(
            summary = "Добавление книги",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Book.class
                            )
                    )}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Книга добавлена"
                    ),

            },
            tags = "Книги"
    )

    @PostMapping()
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book createBook = bookService.addBook(book);
        return ResponseEntity.ok(createBook);
    }

    @Operation(summary = "Обновление книги",
            responses = {
                    @ApiResponse(
                            responseCode = "200",

                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Book.class)
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Книга не найдена"
                    )
            },
            tags = "Книги")

    @PutMapping("{id}")
    public ResponseEntity<Book> editBook(@RequestBody Book book) {
        Book foundBook = bookService.editBook(book);
        if (foundBook == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundBook);
    }

    @Operation(summary = "Удаление книги",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Книга не найдена"
                    )
            },
            tags = "Книги")

    @DeleteMapping("{id}")
    public void deleteBook(@PathVariable int id) {
        bookService.removeBook(id);
    }


}
