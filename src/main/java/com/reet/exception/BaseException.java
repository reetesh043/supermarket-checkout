package com.reet.exception;

public class BaseException extends RuntimeException {
    /**
     * default serial version Id
     */
    private static final long serialVersionUID = 1L;
    // Encapsulated error data object
    private String msg;

    public BaseException() {
        //default constructor
    }

    public BaseException(String msg) {
        super(msg);
        this.msg = msg;
    }

    public BaseException(String msg, Throwable cause) {
        super(msg, cause);
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
