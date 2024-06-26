package cn.learning.creative_mode.factory_method_pattern.logger_example.factory;

import cn.learning.creative_mode.factory_method_pattern.logger_example.logger.FileLogger;
import cn.learning.creative_mode.factory_method_pattern.logger_example.logger.Logger;
import cn.learning.creative_mode.factory_method_pattern.logger_example.logger.FileLogger;
import cn.learning.creative_mode.factory_method_pattern.logger_example.logger.Logger;

/**
 * @author jiuyou2020
 * @description 使用文件记录日志
 * @date 2024/4/22 下午7:47
 */
public class FileLoggerFactory implements LoggerFactory {

    @Override
    public Logger createLogger() {
        FileLogger fileLogger = new FileLogger();
        System.out.println("对文件日志记录器进行初始化操作");
        return fileLogger;
    }
}
