package com.kuan.tdd.args;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * @author qinxuekuan
 * @date 2022/5/19
 */
public class Args {

    public static <T> T parse(Class<T> optionsClass, String... args) {

        List<String> arguments = Arrays.asList(args);
        Constructor<?> constructor = optionsClass.getDeclaredConstructors()[0];

        Object[] values = Arrays.stream(constructor.getParameters())
                                    .map(it -> parseOption(arguments, it)).toArray();

        try {
            return (T) constructor.newInstance(values);
        } catch (InstantiationException | IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

    static Map<Class<?>, OptionParser> parsers = Map.of(
            boolean.class, new BooleanParser(),
            int.class, new IntegerParser(),
            String.class, new StringParser()
    );

    static OptionParser getParser(Class<?> parameterType) {
        return parsers.get(parameterType);
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Class<?> parameterType = parameter.getType();
        Option option = parameter.getAnnotation(Option.class);

        return getParser(parameterType).parseOption(arguments, option);

    }

    interface OptionParser {
        Object parseOption(List<String> arguments, Option option);
    }

    static class StringParser implements OptionParser {

        @Override
        public Object parseOption(List<String> arguments, Option option) {
            String optionValue = "-" + option.value();
            int index = arguments.indexOf(optionValue);
            return arguments.get(index + 1);

        }
    }

    static class IntegerParser implements OptionParser {

        @Override
        public Object parseOption(List<String> arguments, Option option) {
            String optionValue = "-" + option.value();
            int index = arguments.indexOf(optionValue);
            return Integer.valueOf(arguments.get(index + 1));
        }
    }


    static class BooleanParser implements OptionParser {
        @Override
        public Object parseOption(List<String> arguments, Option option) {
            String optionValue = "-" + option.value();
            return arguments.contains(optionValue);
        }
    }



}
