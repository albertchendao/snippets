package org.example.test.benchmark;

import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

/**
 * 基准测试基础类
 *
 * @author Albert
 * @version 1.0
 * @since 2022/6/15 11:24 AM
 */
public class BaseBenchmark {
    //生成的文件路径：{工程根目录}/{reportFileDir}/{XXX.class.getSimpleName()}.json
    private static final String reportFileDir = "jmh-reports/";

    private static String resolvePath(Class<?> targetClazz) {
        return reportFileDir + targetClazz.getSimpleName() + ".json";
    }

    /**
     * 一份标准的配置，根据实际需求配置预热和迭代等参数
     *
     * @param targetClazz 要运行 JMH 测试的类
     * @throws RunnerException See:{@link RunnerException}
     */
    protected static String run(Class<?> targetClazz) throws RunnerException {
        String reportFilePath = resolvePath(targetClazz);
        Options options = new OptionsBuilder()
                .include(targetClazz.getSimpleName())
                .shouldFailOnError(true)
                //结果报告文件输出路径
                .result(reportFilePath)
                //结果报告文件输出格式 JSON
                .resultFormat(ResultFormatType.JSON)
                .build();
        new Runner(options).run();
        return reportFilePath;
    }
}