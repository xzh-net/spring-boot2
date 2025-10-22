package net.xzh.mongo.service.impl;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import net.xzh.mongo.domain.MemberProductCollection;
import net.xzh.mongo.domain.UmsMember;
import net.xzh.mongo.repository.MemberProductCollectionRepository;
import net.xzh.mongo.service.MemberCollectionService;

/**
 * 会员收藏Service实现类
 */
@Service
public class MemberCollectionServiceImpl implements MemberCollectionService {
    @Autowired
    private MemberProductCollectionRepository productCollectionRepository;
   
    private UmsMember member;
    
    @PostConstruct
    public void init() {
    	member=new UmsMember();
    	member.setId(999L);
    	member.setUsername("zhangsan");
    	member.setIcon("header");
    	member.setNickname("张三");
    }
    @Override
    public int add(MemberProductCollection productCollection) {
        int count = 0;
        productCollection.setMemberId(member.getId());
        productCollection.setMemberNickname(member.getNickname());
        productCollection.setMemberIcon(member.getIcon());
        MemberProductCollection findCollection = productCollectionRepository.findByMemberIdAndProductId(productCollection.getMemberId(), productCollection.getProductId());
        if (findCollection == null) {
            productCollectionRepository.save(productCollection);
            count = 1;
        }
        return count;
    }

    @Override
    public int delete(Long productId) {
        return productCollectionRepository.deleteByMemberIdAndProductId(member.getId(), productId);
    }

    @Override
    public Page<MemberProductCollection> list(Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum - 1, pageSize);
        return productCollectionRepository.findByMemberId(member.getId(), pageable);
    }

    @Override
    public MemberProductCollection detail(Long productId) {
        return productCollectionRepository.findByMemberIdAndProductId(member.getId(), productId);
    }

    @Override
    public void clear() {
        productCollectionRepository.deleteAllByMemberId(member.getId());
    }
}
