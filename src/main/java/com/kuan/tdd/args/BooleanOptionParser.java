package com.kuan.tdd.args;

import com.kuan.tdd.args.exceptions.TooManyArgumentsException;

import java.util.List;

import static com.kuan.tdd.args.SingleValuedOptionParser.values;

/**
 * @author qinxuekuan
 * @date 2022/5/27
 */
class BooleanOptionParser implements OptionParser<Boolean> {
    @Override
    public Boolean parse(List<String> arguments, Option option) {
        return values(arguments, option, 0)
                .isPresent();
    }

}
