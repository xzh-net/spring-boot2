package net.xzh.oss.mapper;

import net.xzh.oss.model.FileInfo;

public interface FileInfoMapper {


    int insert(FileInfo record);

    FileInfo selectByPrimaryKey(String id);
    
    int deleteByPrimaryKey(String id);


}