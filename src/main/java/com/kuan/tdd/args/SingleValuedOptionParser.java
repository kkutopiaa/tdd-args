package com.kuan.tdd.args;

import com.kuan.tdd.args.exceptions.InsufficientArgumentsException;
import com.kuan.tdd.args.exceptions.TooManyArgumentsException;

import java.util.List;
import java.util.function.Function;
import java.util.stream.IntStream;

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

        List<String> values = values(arguments, index);

        if (values.size() < 1) {
            throw new InsufficientArgumentsException(option.value());
        }
        if (values.size() > 1) {
            throw new TooManyArgumentsException(option.value());
        }

        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }

    private List<String> values(List<String> arguments, int index) {
        int followingFlag = IntStream.range(index + 1, arguments.size())
                .filter(it -> arguments.get(it).startsWith("-"))
                .findFirst().orElse(arguments.size());

        List<String> values = arguments.subList(index + 1, followingFlag);
        return values;
    }

}
