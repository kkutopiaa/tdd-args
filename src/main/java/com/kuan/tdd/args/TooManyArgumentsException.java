package com.kuan.tdd.args;

/**
 * @author qinxuekuan
 * @date 2022/5/28
 */
public class TooManyArgumentsException extends RuntimeException {

    String value;

    public TooManyArgumentsException(String value) {
        this.value = value;
    }

    public String getOption() {
        return value;
    }
}
