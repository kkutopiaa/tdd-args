package com.kuan.tdd.args;

import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author qinxuekuan
 * @date 2022/5/18
 */
public class ArgsTest {

    // -l -p 8080 -d /usr/logs
//    Single Option:
//        Boolean -l
    @Test
    public void should_set_boolean_option_to_true_if_flag_present() {
        BooleanOption option = Args.parse(BooleanOption.class, "-l");
        assertTrue(option.logging());

        Class<BooleanOption> cls = BooleanOption.class;
        System.out.println(cls);

    }

    @Test
    public void should_set_boolean_option_to_false_if_flag_not_present() {
        BooleanOption option = Args.parse(BooleanOption.class);
        assertFalse(option.logging());
    }

    record BooleanOption(@Option("l") boolean logging) {
    }


//        Integer -p 8080

    @Test
    public void should_parse_int_as_option_value() {
        IntOption option = Args.parse(IntOption.class, "-p", "8080");
        assertEquals(8080, option.port());
    }

    record IntOption(@Option(value = "p") int port) {
    }


    //        String  -d /usr/logs
    @Test
    public void should_get_directory_as_option_value() {
        StringOption option = Args.parse(StringOption.class, "-d", "/usr/logs");
        assertEquals("/usr/logs", option.directory());
    }

    record StringOption(@Option("d") String directory) {
    }


    //    Multi Options:   -l -p 8080 -d /usr/logs
// -l -p 8080 -d /usr/logs
// 接口对用提供的使用方式 的 构想
    @Test
//    @Disabled
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





//    SingleValuedOptionParserTest：
//    sad path:
//        Integer -p / -p 8080 8081
//        String  -d / -d /usr/logs /usr/vars
//    default value:
//        Integer 0
//        String ""


    //    Mult Options:   -l -p 8080 -d /usr/logs
    @Test
    @Disabled
    public void should_example_2() {
        ListOptions options = Args.parse(ListOptions.class, "-g", "this", "is", "a", "list", "-d", "1", "2", "-3", "5");
        assertArrayEquals(new String[]{"this", "is", "a", "list"}, options.group());
        assertArrayEquals(new int[]{1, 2, -3, 5}, options.decimals());
    }

    record ListOptions(@Option("g") String[] group,
                       @Option("d") int[] decimals) {
    }



}
