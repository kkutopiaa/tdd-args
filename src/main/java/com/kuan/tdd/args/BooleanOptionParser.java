package com.kuan.tdd.args;

import com.kuan.tdd.args.exceptions.TooManyArgumentsException;

import java.util.List;

/**
 * @author qinxuekuan
 * @date 2022/5/27
 */
class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        String optionValue = "-" + option.value();
        int index = arguments.indexOf(optionValue);
        if (index + 1 < arguments.size() && !arguments.get(index + 1).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }
        return index != -1;
    }

}
