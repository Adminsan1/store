package com.store.serveice.ex;

public class AddressCountLimitException extends ServiceException{

    public AddressCountLimitException() {
        super();
    }

    public AddressCountLimitException(String message, Throwable cause) {
        super(message, cause);
    }

    public AddressCountLimitException(Throwable cause) {
        super(cause);
    }

    protected AddressCountLimitException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public AddressCountLimitException(String s) {

    }
}
