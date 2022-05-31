package com.kuan.tdd.args;

import com.kuan.tdd.args.exceptions.IllegalOptionException;
import com.kuan.tdd.args.exceptions.IllegalValueException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.kuan.tdd.args.OptionParsersTest.option;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qinxuekuan
 * @date 2022/5/18
 */
public class ArgsTest {

    // -l -p 8080 -d /usr/logs
//    Single Option:
//        Boolean -l
//        Integer -p 8080
//        String  -d /usr/logs

    //    Multi Options:   -l -p 8080 -d /usr/logs
// -l -p 8080 -d /usr/logs
// 接口对用提供的使用方式 的 构想
    @Test
    public void should_parse_multi_options() {
        MultiOptions options = Args.parse(MultiOptions.class, "-l", "-p", "8080", "-d", "/usr/logs");
        assertTrue(options.logging());
        assertEquals(8080, options.port());
        assertEquals("/usr/logs", options.directory());
    }

    record MultiOptions(@Option("l") boolean logging,
                        @Option("p") int port,
                        @Option("d") String directory) {
    }


    @Test
    public void should_throw_illegal_option_exception_if_annotation_not_present() {
        IllegalOptionException e = assertThrows(IllegalOptionException.class,
                () -> Args.parse(OptionWithoutAnnotation.class, "-l", "-p", "8080", "-d", "/usr/logs"));
        assertEquals("port", e.getParameter());
    }


    record OptionWithoutAnnotation(@Option("l") boolean logging,
                                   int port,
                                   @Option("d") String directory) {
    }


    // -g "this" "is" ,  {"this","is"}
    @Test
    public void should_parse_list_value() {
        assertArrayEquals(new String[]{"this", "is"},
                OptionParsers.list(String[]::new, String::valueOf)
                        .parse(Arrays.asList("-g", "this", "is"), option("g")));
    }

    // default value , []
    @Test
    public void should_use_empty_array_as_default_value() {
        String[] value = OptionParsers.list(String[]::new, String::valueOf)
                .parse(List.of(), option("g"));
        assertEquals(0, value.length);
    }

    // -d  ,  a throw exception
    @Test
    public void should_throw_exception_if_value_parser_cant_parse_value() {
        Function<String, String> parser = (it) -> {
            throw new RuntimeException();
        };
        IllegalValueException e = assertThrows(IllegalValueException.class,
                () -> OptionParsers.list(String[]::new, parser)
                        .parse(Arrays.asList("-g", "this", "is"), option("g")));
        assertEquals("this", e.getValue());
        assertEquals("g", e.getOption());
    }


    // sad path
    @Test
    public void should_not_treat_negative_int_as_flag() {
        assertArrayEquals(new Integer[]{-1, -2},
                OptionParsers.list(Integer[]::new, Integer::valueOf)
                        .parse(Arrays.asList("-g", "-1", "-2"), option("g")));
    }


    //    Multi Options:   -l -p 8080 -d /usr/logs
    @Test
//    @Disabled
    public void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new Integer[]{1, 2, -3, 5}, options.decimals());
    }

    record ListOptions(@Option("g") String[] group,
                       @Option("d") Integer[] decimals) {
    }


}
