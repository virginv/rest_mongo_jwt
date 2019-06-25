package com.base.mongo.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

/**
 * Clase de ayuda para manejo de ResponseEntity
 *
 * @author vleon
 */
public class ResponseUtil {

    /**
     * Constructor
     */
    private ResponseUtil() {
    }

    /**
     * Envuelve el valor opcional en una {@link ResponseEntity} con un estatus {@link HttpStatus#OK}, o si está vacío devuelve una
     * {@link ResponseEntity} con un estatus {@link HttpStatus#NOT_FOUND}
     *
     * @param posibleRespuesta respuesta a regresar si está presente
     * @param <X>              tipo de la respuesta
     *
     * @return respuesta conteniendo {@code posibleRespuesta} si tiene valor o {@link HttpStatus#NOT_FOUND}
     */
    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> posibleRespuesta) {
        return wrapOrNotFound(posibleRespuesta, null);
    }

    /**
     * Envuelve el valor opcional en una {@link ResponseEntity} con un estatus {@link HttpStatus#OK} y los headers o si está vacío
     * devuelve una {@link ResponseEntity} con un estatus {@link HttpStatus#NOT_FOUND}
     *
     * @param posibleRespuesta respuesta a regresar si está presente
     * @param header
     * @param <X>              tipo de la respuesta
     *
     * @return respuesta conteniendo {@code posibleRespuesta} si tiene valor o {@link HttpStatus#NOT_FOUND}
     */
    public static <X> ResponseEntity<X> wrapOrNotFound(Optional<X> posibleRespuesta, HttpHeaders header) {
        return posibleRespuesta.map(response -> ResponseEntity.ok().headers(header).body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
