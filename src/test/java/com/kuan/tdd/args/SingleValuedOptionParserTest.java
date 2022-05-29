package com.kuan.tdd.args;

import com.kuan.tdd.args.exceptions.InsufficientArgumentsException;
import com.kuan.tdd.args.exceptions.TooManyArgumentsException;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.kuan.tdd.args.BooleanOptionParserTest.option;
import static org.junit.jupiter.api.Assertions.*;

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

    // sad path , extra args
    @Test
    public void should_not_accept_extra_argument_for_single_valued_option() {
        TooManyArgumentsException e = assertThrows(TooManyArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::valueOf)
                        .parse(Arrays.asList("-p", "8080", "8081"), option("p")));

        assertEquals("p", e.getOption());
    }


    // sad path , insufficient args
    @Test
    public void should_not_accept_insufficient_argument_for_single_valued_option() {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::valueOf)
                        .parse(Arrays.asList("-p", "-t"), option("p")));

        assertEquals("p", e.getOption());
    }

    // sad path , insufficient args
    @Test
    public void should_not_accept_insufficient_argument_for_single_valued_option_2() {
        InsufficientArgumentsException e = assertThrows(InsufficientArgumentsException.class,
                () -> new SingleValuedOptionParser<>(0, Integer::valueOf)
                        .parse(List.of("-p"), option("p")));

        assertEquals("p", e.getOption());
    }

    // dafault value
    @Test
    public void should_set_default_value_to_0_for_int_option() {
        Object defaultValue = new Object();
        Function<String, Object> whatever = it -> null;
        assertSame(defaultValue,
                new SingleValuedOptionParser<>(defaultValue, whatever).parse(List.of(), option("p")));
    }


    // happy path
    @Test
    public void should_parse_value_if_flag_present() {
        Object parsed = new Object();
        Function<String, Object> parser = it -> parsed;
        Object whatever = new Object();
        assertSame(parsed,
                new SingleValuedOptionParser<>(whatever, parser).parse(Arrays.asList("-p", "8080"), option("p")));
    }


}
