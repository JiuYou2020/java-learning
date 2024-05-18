package cn.learning.creative_mode.factory_method_pattern.logger_example.logger;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/22 下午7:48
 */
public class FileLogger extends Logger {

    @Override
    public void writeLog() {
        System.out.println("使用文件记录日志");
    }
}
