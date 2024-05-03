package creative_mode.singleton_pattern;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jiuyou2020
 * @description 单例模式练习：Sunny软件公司开发人员欲创建一个数据库连接池，将指定个数的（如2个或3个）数据库连接对象存储在连接池中，客户端代码可以从池中随机取一个连接对象来连接数据库。试通过对单例类进行改造，设计一个能够自行提供指定个数实例对象的数据库连接类。
 * @date 2024/4/19 下午1:56
 */
public class Practice {
    private static final int DEFAULT_POOL_SIZE = 3;

    private static volatile List<Practice> pool;

    private Practice() {
    }

    public static Practice getInstance() {
        if (pool == null) {
            synchronized (Practice.class) {
                if (pool == null) {
                    pool = new ArrayList<>();
                    for (int i = 0; i < DEFAULT_POOL_SIZE; i++) {
                        pool.add(new Practice());
                    }
                }
            }
        }
        return pool.get((int) (Math.random() * DEFAULT_POOL_SIZE));
    }

    public static void main(String[] args) {
        Practice p1, p2, p3, p4, p5;
        p1 = Practice.getInstance();
        p2 = Practice.getInstance();
        p3 = Practice.getInstance();
        p4 = Practice.getInstance();
        p5 = Practice.getInstance();

        System.out.println(p1 == p2);
        System.out.println(p1 == p3);
        System.out.println(p1 == p4);
        System.out.println(p1 == p5);

        System.out.println(p2 == p3);
        System.out.println(p2 == p4);
        System.out.println(p2 == p5);

        System.out.println(p3 == p4);
        System.out.println(p3 == p5);

        System.out.println(p4 == p5);
    }
}
