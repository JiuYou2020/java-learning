package cn.learning.creative_mode.factory_method_pattern.logger_example;

import cn.learning.creative_mode.factory_method_pattern.logger_example.utils.YMLUtil;
import cn.learning.creative_mode.factory_method_pattern.logger_example.factory.DatabaseLoggerFactory;
import cn.learning.creative_mode.factory_method_pattern.logger_example.factory.LoggerFactory;
import cn.learning.creative_mode.factory_method_pattern.logger_example.utils.YMLUtil;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/22 下午7:50
 */
public class Client {
    public static void main(String[] args) {
        LoggerFactory loggerFactory;

        loggerFactory = (DatabaseLoggerFactory) YMLUtil.getBean();
        loggerFactory.createLogger().writeLog();
    }
}
