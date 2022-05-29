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
