package net.xzh.sharding.service.impl;

import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

import net.xzh.sharding.mapper.AreaMapper;
import net.xzh.sharding.model.Area;
import net.xzh.sharding.service.IAreaService;

/**
 * @author xzh
 */
@Service
public class AreaServiceImpl extends ServiceImpl<AreaMapper, Area> implements IAreaService {

}