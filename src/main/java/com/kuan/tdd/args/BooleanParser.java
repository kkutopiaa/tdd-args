package com.kuan.tdd.args;

import java.util.List;

/**
 * @author qinxuekuan
 * @date 2022/5/27
 */
class BooleanParser implements OptionParser {
    @Override
    public Object parseOption(List<String> arguments, Option option) {
        String optionValue = "-" + option.value();
        return arguments.contains(optionValue);
    }
}
