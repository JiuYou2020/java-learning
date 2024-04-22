package factory_method_pattern.logger_example.logger;

/**
 * @author jiuyou2020
 * @description
 * @date 2024/4/22 下午7:48
 */
public class DatabaseLogger extends Logger{
    @Override
    public void writeLog() {
        System.out.println("使用数据库记录日志");
    }
}
