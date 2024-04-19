package singleton_pattern;

/**
 * @author jiuyou2020
 * @description 枚举单例模式
 * @date 2024/4/19 下午1:54
 */
public enum EnumSingleton {
    INSTANCE;

    public static void main(String[] args) {
        EnumSingleton s1, s2;
        s1 = EnumSingleton.INSTANCE;
        s2 = EnumSingleton.INSTANCE;
        System.out.println(s1 == s2);
    }

    /**
     * 业务方法
     */
    public void whateverMethod() {
    }
}
