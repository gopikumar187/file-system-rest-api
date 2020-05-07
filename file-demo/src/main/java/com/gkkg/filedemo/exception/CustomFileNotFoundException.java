package com.gkkg.filedemo.exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "FileNotFound")
public class CustomFileNotFoundException extends RuntimeException {

        public CustomFileNotFoundException(String message) {
            super(message);
        }
}
