package ru.alexandrina.library.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.alexandrina.library.entity.Publishing;
import ru.alexandrina.library.repository.PublishingRepository;

@Service
public class PublishingService {
    @Autowired
    private final PublishingRepository publishingRepository;

    public PublishingService(PublishingRepository publishingRepository) {
        this.publishingRepository = publishingRepository;
    }

    /**
     * метод возвращает издательство по его Id
     *
     * @param id
     * @return Publishing
     */
    public Publishing findPublishing(int id) {
        return publishingRepository.findById(id).get();
    }

    /**
     * метод добавляет в БД издательство
     *
     * @param publishing
     * @return book
     */

    public Publishing addPublishing(Publishing publishing) {
        return publishingRepository.save(publishing);
    }

    /**
     * метод обновляет издательство в БД
     *
     * @param publishing
     * @return метод возвращает обновленное издательство
     */

    public Publishing editPublishing(Publishing publishing) {
        return publishingRepository.save(publishing);
    }

    /**
     * метод удаляет издательство из БД
     *
     * @param id
     * @return метод возвращает удалённое издательство
     */

    public ResponseEntity<?> removePublishing(int id) {
        publishingRepository.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
