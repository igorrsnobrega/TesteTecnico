package br.com.eicon.teste.exception;

import br.com.eicon.teste.record.ErrorMessageRecord;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ApplicationExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorMessageRecord> handleException(Exception e) {
        return new ResponseEntity<>(
                new ErrorMessageRecord(HttpStatus.BAD_REQUEST.value(), "Erro: " + e.getMessage()),
                HttpStatus.BAD_REQUEST);
    }

}
