package cn.learning.creative_mode.factory_method_pattern.logger_example.factory;

import cn.learning.creative_mode.factory_method_pattern.logger_example.logger.Logger;
import cn.learning.creative_mode.factory_method_pattern.logger_example.logger.Logger;

/**
 * @author jiuyou2020
 * @description 日志工厂接口，具体日志接口都要实现它
 * @date 2024/4/22 下午7:45
 */
public interface LoggerFactory {
    Logger createLogger();
}
