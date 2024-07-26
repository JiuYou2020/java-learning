package cn.southtang.guava;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.nio.charset.Charset;
import java.util.List;

/**
 * @author JiuYou2020
 * @date 2024/07/25
 */
// 忽略不稳定的 API 警告
@SuppressWarnings("UnstableApiUsage")
public class WorkBloomFilter {
    private final BloomFilter<String> bloomFilter;

    /**
     * 初始化布隆过滤器
     *
     * @param expectedInsertions 预计插入的元素数量
     * @param fpp                期望的误判率
     */
    public WorkBloomFilter(int expectedInsertions, double fpp) {
        this.bloomFilter = BloomFilter.create(
                Funnels.stringFunnel(Charset.defaultCharset()),
                expectedInsertions,
                fpp
        );
    }

    public void add(Work work) {
        bloomFilter.put(work.getId());
    }

    public void addAll(List<Work> works) {
        for (Work work : works) {
            add(work);
        }
    }

    public boolean mightContain(String workId) {
        return bloomFilter.mightContain(workId);
    }
}