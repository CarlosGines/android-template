package com.domain.androidtemplate.data.net;

/**
 * Exception for errors from server
 */
public class ServerResponseException extends RuntimeException {

    // ==========================================================================
    // Member variables
    // ==========================================================================

    private int mCode;

    // ==========================================================================
    // Constructor
    // ==========================================================================

    public ServerResponseException(int code, String detailMessage) {
        super(detailMessage);
        mCode = code;
    }

    // ==========================================================================
    // Getters & Setters
    // ==========================================================================

    /**
     * @return the error code of the exception.
     */
    public int getStatusCode() {
        return mCode;
    }
}