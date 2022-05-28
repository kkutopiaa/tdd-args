package com.kuan.tdd.args;

import java.util.List;

/**
 * @author qinxuekuan
 * @date 2022/5/27
 */
interface OptionParser<T> {
    /**
     * 解析命令行参数的主要接口
     *
     * @param arguments 要解析的参数列表
     * @param option    解析参数选项需要用到的类
     * @return 解析之后的对象
     */
    T parse(List<String> arguments, Option option);
}
