package com.base.mongo.config.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.base.mongo.util.ErrorCode;


@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class BusinessServiceException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

    private final ErrorCode code;
    
    /**
     * Creates a new instance of <code>BusinessServiceException</code>.
     * <p>
     * @param errorCode - instance of <code>ErrorCode</code>.
     */
    public BusinessServiceException(ErrorCode errorCode) {
        super(errorCode.getMessage());
        this.code = errorCode;
    }

    /**
     * Constructs an instance of <code>BusinessServiceException</code>.
     * <p>
     * @param errorCode - instance of <code>ErrorCode</code>.
     * @param cause     - error cause.
     */
    public BusinessServiceException(ErrorCode errorCode, Throwable cause) {
        super(errorCode.getMessage(), cause);
        this.code = errorCode;
    }
    
    /**
     * Constructor completo.
     *
     * @param errorCode
     * @param cause
     * @param bus
     */
    public BusinessServiceException(ErrorCode errorCode, String message) {
        super(message);
        this.code = errorCode;
    }
    

	public ErrorCode getCode() {
		return code;
	}
    
	
}
