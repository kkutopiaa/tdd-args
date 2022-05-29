package com.kuan.tdd.args;

/**
 * @author qinxuekuan
 * @date 2022/5/28
 */
public class IllegalOptionException extends RuntimeException {

    private final String parameter;

    public IllegalOptionException(String parameter) {
        this.parameter = parameter;
    }

    public String getParameter() {
        return parameter;
    }
}
