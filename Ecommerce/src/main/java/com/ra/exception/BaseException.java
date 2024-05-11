package com.ra.exception;

public class BaseException extends RuntimeException {
    private Message errorMessage;
    private MessageLoader errorMessageLoader = new MessageLoader();

    public BaseException(String errorCode) {
        super(errorCode);
        errorMessage = new Message();
        errorMessage.setCode(errorCode);
        errorMessage.setMessage(errorMessageLoader.getMessage(errorCode));
    }

    public Message getErrorMessage() {
        return this.errorMessage;
    }
}
