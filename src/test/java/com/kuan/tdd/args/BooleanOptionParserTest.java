package com.kuan.tdd.args;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author qinxuekuan
 * @date 2022/5/28
 */
public class BooleanOptionParserTest {

//    BooleanOptionParserTest:
//    sad path:
//        Boolean -l t  /  -l t f
//    default value:
//        Boolean false


    @Test
    public void should_not_accept_extra_argument_for_boolean_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new BooleanOptionParser().parse(asList("-l", "t"), option("l")));
        Assertions.assertEquals("l", e.getOption());
    }


    @Test
    public void should_get_default_value_to_false_if_option_not_present() {
        assertFalse(new BooleanOptionParser().parse(List.of(), option("l")));

    }

    static Option option(String value) {
        return new Option(){

            @Override
            public Class<? extends Annotation> annotationType() {
                return Option.class;
            }

            @Override
            public String value() {
                return value;
            }
        };
    }

}
