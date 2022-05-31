package com.kuan.tdd.args;

import com.kuan.tdd.args.exceptions.InsufficientArgumentsException;
import com.kuan.tdd.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static java.util.Arrays.asList;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qinxuekuan
 * @date 2022/5/28
 */
public class OptionParsersTest {

    static Option option(String value) {
        return new Option() {

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

    @Nested
    class UnaryOptionParserTest {
//    SingleValuedOptionParserTest：
//    sad path:
//        Integer -p / -p 8080 8081
//        String  -d / -d /usr/logs /usr/vars
//    default value:
//        Integer 0
//        String ""

        // sad path , extra args
        @Test
        public void should_not_accept_extra_argument_for_single_valued_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.unary(0, Integer::valueOf)
                            .parse(asList("-p", "8080", "8081"), option("p")));

            assertEquals("p", e.getOption());
        }


        // sad path , insufficient args
        @ParameterizedTest
        @ValueSource(strings = {"-p -l", "-p"})
        public void should_not_accept_insufficient_argument_for_single_valued_option(String arguments) {
            InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                    () -> OptionParsers.unary(0, Integer::valueOf)
                            .parse(asList(arguments.split(" ")), option("p")));

            assertEquals("p", e.getOption());
        }

        // default value
        @Test
        public void should_set_default_value_to_0_for_int_option() {
            Object defaultValue = new Object();
            Function<String, Object> whatever = it -> null;
            assertSame(defaultValue,
                    OptionParsers.unary(defaultValue, whatever).parse(List.of(), option("p")));
        }


        // happy path
        @Test
        public void should_parse_value_if_flag_present() {
            Object parsed = new Object();
            Function<String, Object> parser = it -> parsed;
            Object whatever = new Object();
            assertSame(parsed,
                    OptionParsers.unary(whatever, parser).parse(asList("-p", "8080"), option("p")));
        }
    }

    /**
     * @author qinxuekuan
     * @date 2022/5/28
     */
    @Nested
    class BooleanOptionParserTest {

        //    BooleanOptionParserTest:
        //    sad path:
        //        Boolean -l t  /  -l t f
        //    default value:
        //        Boolean false


        // happy path
        @Test
        public void should_get_true_if_option_present() {
            assertTrue(OptionParsers.bool().parse(List.of("-l"), option("l")));
        }

        // sad path
        @Test
        public void should_not_accept_extra_argument_for_boolean_option() {
            TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                    () -> OptionParsers.bool().parse(asList("-l", "t"), option("l")));
            Assertions.assertEquals("l", e.getOption());
        }

        // default value
        @Test
        public void should_get_default_value_to_false_if_option_not_present() {
            assertFalse(OptionParsers.bool().parse(List.of(), option("l")));

        }

    }
}
