package net.xzh.oss.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import net.xzh.oss.common.model.CommonResult;
import net.xzh.oss.model.FileInfo;
import net.xzh.oss.service.IFileService;

/**
 * 文件上传
 *
 */
@Api(tags = "文件上传")
@RestController
public class FileController {
	@Resource
	private IFileService fileService;

	/**
	 * 文件上传 根据fileType选择上传方式
	 *
	 * @param file
	 * @return
	 * @throws Exception
	 */
	@ApiOperation("文件上传")
	@RequestMapping(value = "/files-anon", method = RequestMethod.POST)
	public CommonResult<FileInfo> upload(@RequestParam("file") MultipartFile file) throws Exception {
		return CommonResult.success(fileService.upload(file));
	}

	/**
	 * 文件删除
	 *
	 * @param id
	 */
	@ApiOperation("文件删除")
	@RequestMapping(value = "/files/{id}", method = RequestMethod.DELETE)
	public CommonResult<Object> delete(@PathVariable String id) {
		try {
			fileService.delete(id);
			return CommonResult.success(1);
		} catch (Exception ex) {
			return CommonResult.failed();
		}
	}
}
