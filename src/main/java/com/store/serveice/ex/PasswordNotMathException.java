package com.store.serveice.ex;

/**
 * 密码验证失败
 */
public class PasswordNotMathException extends ServiceException{
    public PasswordNotMathException() {
        super();
    }

    public PasswordNotMathException(String message) {
        super(message);
    }

    public PasswordNotMathException(String message, Throwable cause) {
        super(message, cause);
    }

    public PasswordNotMathException(Throwable cause) {
        super(cause);
    }

    public PasswordNotMathException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
    }
}
