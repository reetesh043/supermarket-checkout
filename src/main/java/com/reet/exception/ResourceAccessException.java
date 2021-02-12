package com.reet.exception;

public class ResourceAccessException extends BaseException {

    /**
     * default serial version Id
     */
    private static final long serialVersionUID = 1L;

    public ResourceAccessException(String msg) {
        super(msg);
    }

    public ResourceAccessException(String msg, Throwable t) {
        super(msg, t);
    }

}
