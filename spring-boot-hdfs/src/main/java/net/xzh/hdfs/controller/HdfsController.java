package net.xzh.hdfs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import net.xzh.hdfs.repository.HdfsService;

@RestController
public class HdfsController {

	@Autowired
	private HdfsService hdfsService;

	/**
	 * 创建HDFS目录
	 * 
	 * @param srcFile
	 * @return
	 */
	@PostMapping(value = "/mkdir")
	public String mkdir(@RequestParam String dir) {
		hdfsService.mkdir(dir);
		return "创建成功";
	}

	/**
	 * 上传文件至HDFS
	 * @param srcFile 本地文件路径
	 * @param dstPath 服务器目录/test123
	 * @return
	 */
	@PostMapping(value = "/upload")
	public String upload(String srcFile, String dstPath) {
		hdfsService.uploadFile(srcFile, dstPath);
		return "上传成功";
	}
}