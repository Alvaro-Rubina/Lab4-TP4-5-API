package org.spdgrupo.lab4tp45api.config.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.mercadopago.exceptions.MPApiException;
import com.mercadopago.exceptions.MPException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Arrays;
import java.util.stream.Collectors;

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

    @ExceptionHandler(ExistingUserException.class)
    public ResponseEntity<String> handleExistingUserException(ExistingUserException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<String> handleHttpMessageNotReadable(HttpMessageNotReadableException ex) {
        if (ex.getCause() instanceof InvalidFormatException) {
            InvalidFormatException cause = (InvalidFormatException) ex.getCause();
            if (cause.getTargetType() != null && cause.getTargetType().isEnum()) {
                String valoresValidos = Arrays.stream(cause.getTargetType().getEnumConstants())
                        .map(Object::toString)
                        .collect(Collectors.joining(", "));
                String nombreCampo = cause.getTargetType().getSimpleName();
                return new ResponseEntity<>(
                        nombreCampo + " inválido: '" + cause.getValue() + "'. Los valores permitidos son: [" + valoresValidos + "]",
                        HttpStatus.BAD_REQUEST
                );
            }
        }
        return new ResponseEntity<>("Error en el formato del JSON: " + ex.getMessage(), HttpStatus.BAD_REQUEST);
    }


}
