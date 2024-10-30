package net.xzh.mongo.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

import net.xzh.mongo.domain.MemberProductCollection;

/**
 * 商品收藏Repository
 * Created 2018/8/2.
 */
public interface MemberProductCollectionRepository extends MongoRepository<MemberProductCollection,String> {
    MemberProductCollection findByMemberIdAndProductId(Long memberId, Long productId);
    int deleteByMemberIdAndProductId(Long memberId,Long productId);
    Page<MemberProductCollection> findByMemberId(Long memberId, Pageable pageable);
    void deleteAllByMemberId(Long memberId);
}
