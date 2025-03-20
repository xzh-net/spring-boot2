package net.xzh.oss.service;

import org.springframework.web.multipart.MultipartFile;

import net.xzh.oss.model.FileInfo;

/**
 * 文件service
 *
 */
public interface IFileService {
	
	FileInfo upload(MultipartFile file) throws Exception;
	
	void delete(String id);

}
