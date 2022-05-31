package com.kuan.tdd.args.exceptions;

/**
 * @author qinxuekuan
 * @date 2022/5/28
 */
public class IllegalValueException extends RuntimeException {

    private final String value;

    private final String option;

    public IllegalValueException(String value, String option) {
        this.value = value;
        this.option = option;
    }

    public String getValue() {
        return value;
    }

    public String getOption() {
        return option;
    }
}
