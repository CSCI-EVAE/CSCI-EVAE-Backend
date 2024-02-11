package fr.ubo.dosi.projectagile.cscievaebackend.exception;

public class BadRequestException extends RuntimeException {
    public BadRequestException(String message) {
        super(message);
    }
}