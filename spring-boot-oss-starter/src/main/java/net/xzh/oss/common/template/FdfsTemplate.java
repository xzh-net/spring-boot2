package net.xzh.oss.common.template;

import java.io.InputStream;

import javax.annotation.Resource;

import org.apache.commons.io.FilenameUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.github.tobato.fastdfs.domain.fdfs.StorePath;
import com.github.tobato.fastdfs.domain.proto.storage.DownloadCallback;
import com.github.tobato.fastdfs.service.FastFileStorageClient;

import lombok.SneakyThrows;
import net.xzh.oss.common.model.ObjectInfo;
import net.xzh.oss.common.properties.FileServerProperties;

/**
 * FastDFS配置
 *
 * @author xzh
 * @date 2021/2/11
 */
@ConditionalOnClass(FastFileStorageClient.class)
@ConditionalOnProperty(prefix = FileServerProperties.PREFIX, name = "type", havingValue = FileServerProperties.TYPE_FDFS)
public class FdfsTemplate {
    @Resource
    private FileServerProperties fileProperties;

    @Resource
    private FastFileStorageClient storageClient;

    @SneakyThrows
    public ObjectInfo upload(String objectName, InputStream is) {
        return upload(objectName, is, is.available());
    }

    @SneakyThrows
    public ObjectInfo upload(MultipartFile file) {
        return upload(file.getOriginalFilename(), file.getInputStream(), file.getSize());
    }

    /**
     * 上传对象
     * @param objectName 对象名
     * @param is 对象流
     * @param size 大小
     */
    private ObjectInfo upload(String objectName, InputStream is, long size) {
        StorePath storePath = storageClient.uploadFile(is, size, FilenameUtils.getExtension(objectName), null);
        ObjectInfo obj = new ObjectInfo();
        obj.setObjectPath(storePath.getFullPath());
        obj.setObjectUrl(fileProperties.getFdfs().getWebUrl() + "/" + storePath.getFullPath());
        return obj;
    }

    /**
     * 删除对象
     * @param objectPath 对象路径
     */
    public void delete(String objectPath) {
        if (!StringUtils.isEmpty(objectPath)) {
            StorePath storePath = StorePath.parseFromUrl(objectPath);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        }
    }

    /**
     * 下载对象
     * @param objectPath 对象路径
     * @param callback 回调
     */
    public <T> T download(String objectPath, DownloadCallback<T> callback) {
        if (!StringUtils.isEmpty(objectPath)) {
            StorePath storePath = StorePath.parseFromUrl(objectPath);
            return storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), callback);
        }
        return null;
    }
}
