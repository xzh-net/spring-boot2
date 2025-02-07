package net.xzh.attach.mapper;

import net.xzh.attach.model.FileInfo;

public interface FileInfoMapper {


    int insert(FileInfo record);

    FileInfo selectByPrimaryKey(String id);
    
    int deleteByPrimaryKey(String id);


}