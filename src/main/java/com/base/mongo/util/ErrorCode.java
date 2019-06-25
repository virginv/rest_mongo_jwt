package com.base.mongo.util;

/**
 * Enumeration that represents the application error codes.
 * <p>
 *
 * @author vleon
 */
public enum ErrorCode {

    GENERAL_ERROR_ON_SEARCH(1, "Ocurrió un error al realizar la búsqueda con los parámetros enviados."),
    GENERAL_ERROR_ON_SAVE(  2, "Ocurrió un error al guardar la información."),
    GENERAL_ERROR_ON_UPDATE(3, "Ocurrió un error al actualizar la información."),
    GENERAL_ERROR_ON_DELETE(4, "Ocurrió un error al borrar la información."),
    GENERAL_ERROR_EXISTE(5, "La información con el registro indicado ya existe"),
    JWT_INVALID_USER(6, "Username is already taken!"),
    
    
	
;



    /**
     * Codigo del error.
     */
    private final int code;

    /**
     * Mensaje de error.
     */
    private final String message;

    /**
     * Constructor basico.
     *
     * @param code
     * @param message
     */
    private ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
