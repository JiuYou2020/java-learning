package cn.learning.creative_mode.singleton_pattern;

/**
 * @author jiuyou2020
 * @description 懒汉式单例模式
 * @date 2024/4/19 下午12:42
 */
public class Lazy {
    private static volatile Lazy instance;

    private Lazy() {
    }

    public static Lazy getInstance() {
        if (instance == null) {
            synchronized (Lazy.class) {
                if (instance == null) {
                    instance = new Lazy();
                }
            }
        }
        return instance;
    }
}
