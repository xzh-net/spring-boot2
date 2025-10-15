package net.xzh.oss.modules.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;

import net.xzh.oss.model.ObjectInfo;
import net.xzh.oss.modules.mapper.FileInfoMapper;
import net.xzh.oss.modules.model.FileInfo;
import net.xzh.oss.modules.utils.FileUtils;

/**
 * AbstractIFileService 抽取类 根据zlt.file-server.type 实例化具体对象
 *
 */
public abstract class AbstractFileService implements FileService {

	@Autowired
	private FileInfoMapper fileInfoMapper;

	private static final String FILE_SPLIT = ".";

	@Override
	public FileInfo upload(MultipartFile file) {
		FileInfo fileInfo = FileUtils.getFileInfo(file);
		if (!fileInfo.getName().contains(FILE_SPLIT)) {
			throw new IllegalArgumentException("缺少后缀名");
		}
		ObjectInfo objectInfo = uploadFile(file);
		fileInfo.setPath(objectInfo.getObjectPath());
		fileInfo.setUrl(objectInfo.getObjectUrl());
		// 设置文件来源
		fileInfo.setSource(fileType());
		// 将文件信息保存到数据库
		fileInfoMapper.insert(fileInfo);
		return fileInfo;
	}

	/**
	 * 删除文件
	 * 
	 * @param id 文件id
	 */
	@Override
	public void delete(String id) {
		FileInfo fileInfo = fileInfoMapper.selectByPrimaryKey(id);
		if (fileInfo != null) {
			fileInfoMapper.deleteByPrimaryKey(id);
			this.deleteFile(fileInfo.getPath());
		}
	}

	/**
	 * 文件来源
	 *
	 * @return
	 */
	protected abstract String fileType();

	/**
	 * 上传文件
	 *
	 * @param file
	 */
	protected abstract ObjectInfo uploadFile(MultipartFile file);

	/**
	 * 删除文件资源
	 *
	 * @param objectPath 文件路径
	 */
	protected abstract void deleteFile(String objectPath);

}
