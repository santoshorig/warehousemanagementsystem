package com.kamadhenu.warehousemanagementsystem.model.domain.exception;

/**
 * Custom exception
 */
public class CustomException extends Exception {

    /**
     * @param errorMessage Error message for custom errors
     */
    public CustomException(String errorMessage) {
        super(errorMessage);
    }
}
