package ru.alexandrina.library.exception;

public class PublisherNotFoundException extends RuntimeException{
    private final long id;

    public PublisherNotFoundException(long id) {
        this.id = id;
    }

    @Override
    public String getMessage() {
        return "Издательство с id " + id + " не найдено";
    }
}
