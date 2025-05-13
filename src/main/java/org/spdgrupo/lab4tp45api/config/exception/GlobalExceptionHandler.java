package org.spdgrupo.lab4tp45api.config.exception;

import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<String> handleNotFoundException(NotFoundException ex) {
        return new ResponseEntity<>(ex.getMessage(),HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MPException.class)
    public ResponseEntity<String> handleMPException(MPException ex) {
        return new ResponseEntity<>("Error de MercadoPago: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(MPApiException.class)
    public ResponseEntity<String> handleMPApiException(MPApiException ex) {
        return new ResponseEntity<>("Error en la API de MercadoPago: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

}
