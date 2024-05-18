package cn.learning.creative_mode.factory_method_pattern.logger_example.utils;

/**
 * @author jiuyou2020
 * @description 解析YML并通过反射获取实例对象
 * @date 2024/4/22 下午8:13
 */
public class YMLUtil {
    //解析config.yml文件，获取配置的日志器类名
    private static String getLoggerClassName() {
        //省略读取配置文件的代码，直接返回配置的日志器类名，实际项目中，我们可以利用springboot的@Value注解来读取配置文件
        return "creative_mode.factory_method_pattern.logger_example.factory.DatabaseLoggerFactory";
    }

    public static Object getBean() {
        Object obj = null;
        try {
            Class<?> clazz = Class.forName(getLoggerClassName());
            obj = clazz.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}