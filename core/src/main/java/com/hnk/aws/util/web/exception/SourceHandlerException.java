package com.hnk.aws.util.web.exception;

/**
 * Error for {@link com.nkhoang.util.web.source.HTMLSourceHandler}.
 */
public class SourceHandlerException extends Exception {

    public SourceHandlerException(String errorMessage) {
        super(errorMessage);
    }

    public SourceHandlerException(String errorMessage, Throwable t) {
        super(errorMessage, t);
    }

}
