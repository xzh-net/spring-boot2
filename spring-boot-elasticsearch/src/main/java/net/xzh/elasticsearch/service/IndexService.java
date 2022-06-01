package net.xzh.elasticsearch.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import net.xzh.elasticsearch.domain.IndexDto;

/**
 * 索引
 *
 * @author zlt
 * @date 2019/4/23
 */
public interface IndexService {
    /**
     * 创建索引
     */
    boolean create(IndexDto indexDto) throws IOException;

    /**
     * 删除索引
     * @param indexName 索引名
     */
    boolean delete(String indexName) throws IOException;

    /**
     * 索引列表
     * @param queryStr 搜索字符串
     * @param indices 默认显示的索引名
     */
    List<Map<String, String>> list(String queryStr, String indices) throws IOException;

    /**
     * 显示索引明细
     * @param indexName 索引名
     */
    Map<String, Object> show(String indexName) throws IOException;
}
