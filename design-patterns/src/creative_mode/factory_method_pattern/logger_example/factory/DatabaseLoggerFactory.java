package creative_mode.factory_method_pattern.logger_example.factory;

import creative_mode.factory_method_pattern.logger_example.logger.DatabaseLogger;
import creative_mode.factory_method_pattern.logger_example.logger.Logger;

/**
 * @author jiuyou2020
 * @description 使用数据库记录日志
 * @date 2024/4/22 下午7:47
 */
public class DatabaseLoggerFactory implements LoggerFactory {
    @Override
    public Logger createLogger() {
        Logger databaseLogger = new DatabaseLogger();
        System.out.println("对数据库日志记录器进行初始化操作");
        return databaseLogger;
    }
}
