package com.kuan.tdd.args;

import java.util.List;
import java.util.function.Function;

/**
 * @author qinxuekuan
 * @date 2022/5/27
 */
class SingleValuedOptionParser<T> implements OptionParser<T> {

    T defaultValue;

    Function<String, T> valueParser;

    public SingleValuedOptionParser(T defaultValue, Function<String, T> valueParser) {
        this.defaultValue = defaultValue;
        this.valueParser = valueParser;
    }

    @Override
    public T parse(List<String> arguments, Option option) {
        String optionValue = "-" + option.value();
        int index = arguments.indexOf(optionValue);

        if (index == -1) {
            return defaultValue;
        }

        if (index + 1 >= arguments.size() || arguments.get(index + 1).startsWith("-")) {
            throw new InsufficientArgumentsException(option.value());
        }

        if (index + 2 < arguments.size() && !arguments.get(index + 2).startsWith("-")) {
            throw new TooManyArgumentsException(option.value());
        }

        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }

}
