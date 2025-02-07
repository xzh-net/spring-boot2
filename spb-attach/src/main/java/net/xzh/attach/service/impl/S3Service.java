package net.xzh.attach.service.impl;

import javax.annotation.Resource;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.util.StrUtil;
import lombok.Getter;
import lombok.Setter;
import net.xzh.attach.common.constant.CommonConstant;
import net.xzh.attach.common.model.ObjectInfo;
import net.xzh.attach.common.properties.FileServerProperties;
import net.xzh.attach.common.template.S3Template;

/**
 * s3协议实现
 * @author xzh
 */
@Service
@ConditionalOnProperty(prefix = net.xzh.attach.common.properties.FileServerProperties.PREFIX, name = "type", havingValue = FileServerProperties.TYPE_S3)
public class S3Service extends AbstractIFileService {
	@Resource
	private S3Template s3Template;

	@Override
	protected String fileType() {
		return FileServerProperties.TYPE_S3;
	}

	@Override
	protected ObjectInfo uploadFile(MultipartFile file) {
		return s3Template.upload(file);
	}

	@Override
	protected void deleteFile(String objectPath) {
		S3Object s3Object = parsePath(objectPath);
		s3Template.delete(s3Object.bucketName, s3Object.objectName);
	}

	@Setter
	@Getter
	private class S3Object {
		private String bucketName;
		private String objectName;
	}

	private S3Object parsePath(String path) {
		S3Object s3Object = new S3Object();
		if (StrUtil.isNotEmpty(path)) {
			int splitIndex = path.lastIndexOf(CommonConstant.PATH_SPLIT);
			if (splitIndex != -1) {
				s3Object.bucketName = path.substring(0, splitIndex);
				s3Object.objectName = path.substring(splitIndex + 1);
			}
		}
		return s3Object;
	}

}
