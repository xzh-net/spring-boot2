package net.xzh.mongo.service;

import org.springframework.data.domain.Page;

import net.xzh.mongo.domain.MemberProductCollection;

/**
 * 会员收藏Service
 * Created 2018/8/2.
 */
public interface MemberCollectionService {
    int add(MemberProductCollection productCollection);

    int delete(Long productId);

    Page<MemberProductCollection> list(Integer pageNum, Integer pageSize);

    MemberProductCollection detail(Long productId);

    void clear();
}
