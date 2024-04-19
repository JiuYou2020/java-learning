package singleton_pattern;

/**
 * @author jiuyou2020
 * @description IoDH式单例模式
 * @date 2024/4/19 下午12:42
 */
public class IoZH {
    private IoZH() {
    }

    public static IoZH getInstance() {
        return HolderClass.instance;
    }

    public static void main(String[] args) {
        IoZH s1, s2;
        s1 = IoZH.getInstance();
        s2 = IoZH.getInstance();
        System.out.println(s1 == s2);
    }

    private static class HolderClass {
        private final static IoZH instance = new IoZH();
    }

}
