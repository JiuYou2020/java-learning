package cn.southtang.guava;

import java.util.ArrayList;
import java.util.List;

/**
 * @author JiuYou2020
 * @date 2024/07/25
 */
public class Main {
    public static void main(String[] args) {
        WorkBloomFilter filter = new WorkBloomFilter(1000, 0.01);
        // 随机生成一些作品ID
        List<Work> allWorks = generateWorks();
        // 初始化时,将所有已知的作品ID添加到过滤器中
        filter.addAll(allWorks);

        // 在查询缓存或数据库之前
        String workId = "workId100";
        if (!filter.mightContain(workId)) {
            // 如果布隆过滤器说这个ID不存在,那么它肯定不存在
            // 直接返回"未找到",不需要查询缓存或数据库
            System.out.println("workId: " + workId + " 不存在");
        }

        // 否则,继续正常的缓存/数据库查询流程
        // ...
        System.out.println("workId: " + workId + " 存在");
        System.out.println("do something");
    }

    private static List<Work> generateWorks() {
        List<Work> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Work work = new Work();
            work.setId("workId" + i);
            list.add(work);
        }
        return list;
    }
}
