package com.kuan.tdd.args;

import org.junit.jupiter.api.Test;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.kuan.tdd.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * @author qinxuekuan
 * @date 2022/5/28
 */
public class SingleValuedOptionParserTest {


//    SingleValuedOptionParserTest：
//    sad path:
//        Integer -p / -p 8080 8081
//        String  -d / -d /usr/logs /usr/vars
//    default value:
//        Integer 0
//        String ""

    @Test
    public void should_not_accept_extra_argument_for_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::valueOf)
                        .parse(Arrays.asList("-p", "8080", "8081"), option("p")));

        assertEquals("p", e.getOption());
    }


    @Test
    public void should_not_accept_insufficient_argument_for_single_valued_option() {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::valueOf)
                        .parse(Arrays.asList("-p", "-t"), option("p")));

        assertEquals("p", e.getOption());
    }

    @Test
    public void should_not_accept_insufficient_argument_for_single_valued_option_2() {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::valueOf)
                        .parse(List.of("-p"), option("p")));

        assertEquals("p", e.getOption());
    }

    @Test
    public void should_set_default_value_to_0_for_int_option() {
        assertEquals(0,
                new SingleValuedOptionParser<>(0, Integer::valueOf)
                        .parse(List.of(), option("p")));

    }

    @Test
    public void should_not_accept_extra_argument_for_string_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> ((SingleValuedOptionParser<? extends Serializable>) new SingleValuedOptionParser<>("",String::valueOf))
                        .parse(List.of("-d", "/usr/log", "/tmp/aa"), option("d")));

        assertEquals("d", e.getOption());
    }


}
