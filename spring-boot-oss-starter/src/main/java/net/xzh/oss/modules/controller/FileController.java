package net.xzh.oss.modules.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.xzh.oss.modules.model.FileInfo;
import net.xzh.oss.modules.service.FileService;

/**
 * 文件上传
 */
@RestController
public class FileController {
	@Resource
	private FileService fileService;

	/**
	 * 文件上传 
	 * @param file
	 * @return
	 * @throws Exception
	 */

	@RequestMapping(value = "/files-anon", method = RequestMethod.POST)
	public FileInfo upload(@RequestParam("file") MultipartFile file) throws Exception {
		return fileService.upload(file);
	}

	@RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
	public String delete(@PathVariable String id) {
		fileService.delete(id);
		return "success";
	}
}
