package com.kuan.tdd.args;

import java.io.Serializable;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

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

    static Map<Class<?>, OptionParser<?>> parsers = Map.of(
            boolean.class, new BooleanOptionParser(),
            int.class, new SingleValuedOptionParser<>(0, Integer::valueOf),
            String.class, new SingleValuedOptionParser<>("", String::valueOf)
    );


    static OptionParser<?> getParser(Class<?> parameterType) {
        return parsers.get(parameterType);
    }

    private static Object parseOption(List<String> arguments, Parameter parameter) {
        Class<?> parameterType = parameter.getType();
        Option option = parameter.getAnnotation(Option.class);

        return getParser(parameterType).parse(arguments, option);

    }


}
