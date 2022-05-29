package com.kuan.tdd.args.exceptions;

/**
 * @author qinxuekuan
 * @date 2022/5/28
 */
public class InsufficientArgumentsException extends RuntimeException {

    private final String value;

    public InsufficientArgumentsException(String value) {
        this.value = value;
    }

    public String getOption() {
        return value;
    }
}
