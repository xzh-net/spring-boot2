package net.xzh.oss.modules.service;

import org.springframework.web.multipart.MultipartFile;

import net.xzh.oss.modules.model.FileInfo;

/**
 * 文件service
 *
 */
public interface FileService {
	
	FileInfo upload(MultipartFile file);
	
	void delete(String id);

}
