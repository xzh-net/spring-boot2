package net.xzh.oss.modules.mapper;

import net.xzh.oss.modules.model.FileInfo;

public interface FileInfoMapper {

    int insert(FileInfo record);

    FileInfo selectByPrimaryKey(String id);
    
    int deleteByPrimaryKey(String id);


}