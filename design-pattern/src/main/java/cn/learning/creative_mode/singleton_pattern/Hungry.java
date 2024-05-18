package cn.learning.creative_mode.singleton_pattern;

/**
 * @author jiuyou2020
 * @description 恶汉式单例模式
 * @date 2024/4/19 下午12:42
 */
public class Hungry {
    private static final Hungry instance = new Hungry();

    private Hungry() {
    }

    public static Hungry getInstance() {
        return instance;
    }
}
