package com.kuan.tdd.args;

import java.util.List;
import java.util.function.Function;

/**
 * @author qinxuekuan
 * @date 2022/5/27
 */
class SingleValuedOptionParser<T> implements OptionParser {

    Function<String, T> valueParser;

    public SingleValuedOptionParser(Function<String, T> valueParser) {
        this.valueParser = valueParser;
    }

    @Override
    public T parseOption(List<String> arguments, Option option) {
        String optionValue = "-" + option.value();
        int index = arguments.indexOf(optionValue);
        String value = arguments.get(index + 1);
        return valueParser.apply(value);
    }

}
