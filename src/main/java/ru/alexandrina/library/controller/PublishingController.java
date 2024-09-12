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
import ru.alexandrina.library.entity.Publishing;
import ru.alexandrina.library.service.PublishingService;

@RestController
@RequiredArgsConstructor
@RequestMapping("publishing")
public class PublishingController {

    @Autowired
    private final PublishingService publishingService;

    @Operation(
            summary = "Получение информации о издательстве",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Найдено издательство"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Издательство не найдено"
                    )},
            tags = "Издательства"
    )

    @GetMapping("{id}")
    public ResponseEntity<Publishing> getPublishing(@PathVariable int id) {
        Publishing publishing = publishingService.findPublishing(id);
        if (publishing == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(publishing);
    }

    @Operation(
            summary = "Добавление издательства",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    content = {@Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(
                                    implementation = Publishing.class
                            )
                    )}
            ),
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Издательство добавлено"
                    ),

            },
            tags = "Издательства"
    )

    @PostMapping()
    public ResponseEntity<Publishing> createPublishing(@RequestBody Publishing publishing) {
        Publishing createPublishing = publishingService.addPublishing(publishing);
        return ResponseEntity.ok(createPublishing);
    }

    @Operation(summary = "Обновление издательства",
            responses = {
                    @ApiResponse(
                            responseCode = "200",

                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = Publishing.class)
                            )
                    ),

                    @ApiResponse(
                            responseCode = "400",
                            description = "Некорректный запрос"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Издательство не найдено"
                    )
            },
            tags = "Издательства")

    @PutMapping("{id}")
    public ResponseEntity<Publishing> editPublishing(@RequestBody Publishing publishing) {
        Publishing foundPublishing = publishingService.editPublishing(publishing);
        if (foundPublishing == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(foundPublishing);
    }

    @Operation(summary = "Удаление издательства",
            responses = {
                    @ApiResponse(
                            responseCode = "200"
                    ),

                    @ApiResponse(
                            responseCode = "404",
                            description = "Издательство не найдено"
                    )
            },
            tags = "Издательства")

    @DeleteMapping("{id}")
    public void deletePublishing(@PathVariable int id) {
        publishingService.removePublishing(id);
    }

}
