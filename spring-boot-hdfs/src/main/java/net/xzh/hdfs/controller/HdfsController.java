package net.xzh.hdfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.hdfs.repository.HdfsService;


@RestController
public class HdfsController {

	@Autowired
    private HdfsService hdfsService;

    /**
     * 创建HDFS目录
     * @param srcFile
     * @return
     */
    @PostMapping("/mkdir")
    public boolean mkdir(String dir){
    	return hdfsService.mkdir(dir);
    }
    
    /**
     * 上传文件至HDFS
     * @param dstPath
     * @return
     */
    @PostMapping("/uploadFileToHdfs")
    public void uploadFileToHdfs(String srcFile,String dstPath){
    	hdfsService.uploadFileToHdfs(srcFile, dstPath);
    }
}