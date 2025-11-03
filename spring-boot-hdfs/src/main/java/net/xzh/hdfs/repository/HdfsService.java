package net.xzh.hdfs.repository;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * HDFS服务类
 */
@Slf4j
@Component
public class HdfsService {

    private final FileSystem fileSystem;
    @SuppressWarnings("unused")
	private final Configuration configuration;

    @Autowired
    public HdfsService(FileSystem fileSystem, Configuration configuration) {
        this.fileSystem = fileSystem;
        this.configuration = configuration;
    }

    // ==================== 目录操作 ====================

    /**
     * 创建HDFS目录
     */
    public boolean mkdir(String path) {
        try {
            Path hdfsPath = new Path(path);
            if (!fileSystem.exists(hdfsPath)) {
                return fileSystem.mkdirs(hdfsPath);
            }
            log.info("目录已存在: {}", path);
            return true;
        } catch (IOException e) {
            log.error("创建HDFS目录失败: {}", path, e);
            return false;
        }
    }

    /**
     * 递归创建目录
     */
    public boolean mkdirs(String path) {
        try {
            return fileSystem.mkdirs(new Path(path));
        } catch (IOException e) {
            log.error("递归创建HDFS目录失败: {}", path, e);
            return false;
        }
    }

    // ==================== 文件上传 ====================

    /**
     * 上传本地文件到HDFS
     */
    public boolean uploadFile(String localFilePath, String hdfsPath) {
        return uploadFile(localFilePath, hdfsPath, false, true);
    }

    /**
     * 上传本地文件到HDFS（可配置参数）
     */
    public boolean uploadFile(String localFilePath, String hdfsPath, 
                            boolean deleteSource, boolean overwrite) {
        try {
            Path localPath = new Path(localFilePath);
            Path hdfsTargetPath = new Path(hdfsPath);
            
            fileSystem.copyFromLocalFile(deleteSource, overwrite, localPath, hdfsTargetPath);
            log.info("文件上传成功: {} -> {}", localFilePath, hdfsPath);
            return true;
        } catch (IOException e) {
            log.error("文件上传失败: {} -> {}", localFilePath, hdfsPath, e);
            return false;
        }
    }

    /**
     * 上传字节数组到HDFS
     */
    public boolean uploadBytes(byte[] data, String hdfsPath) {
        try (InputStream inputStream = new ByteArrayInputStream(data)) {
            return uploadStream(inputStream, hdfsPath);
        } catch (IOException e) {
            log.error("字节数组上传失败: {}", hdfsPath, e);
            return false;
        }
    }

    /**
     * 上传字符串到HDFS
     */
    public boolean uploadString(String content, String hdfsPath) {
        return uploadBytes(content.getBytes(StandardCharsets.UTF_8), hdfsPath);
    }

    /**
     * 上传输入流到HDFS
     */
    public boolean uploadStream(InputStream inputStream, String hdfsPath) {
        try (FSDataOutputStream outputStream = fileSystem.create(new Path(hdfsPath))) {
            IOUtils.copy(inputStream, outputStream);
            log.info("文件流上传成功: {}", hdfsPath);
            return true;
        } catch (IOException e) {
            log.error("文件流上传失败: {}", hdfsPath, e);
            return false;
        }
    }

    /**
     * 上传MultipartFile到HDFS
     */
    public boolean uploadMultipartFile(MultipartFile file, String hdfsPath) {
        try {
            return uploadStream(file.getInputStream(), hdfsPath);
        } catch (IOException e) {
            log.error("MultipartFile上传失败: {}", hdfsPath, e);
            return false;
        }
    }

    // ==================== 文件下载 ====================

    /**
     * 下载HDFS文件到本地
     */
    public boolean downloadFile(String hdfsPath, String localPath) {
        try {
            Path hdfsSrcPath = new Path(hdfsPath);
            Path localDstPath = new Path(localPath);
            
            fileSystem.copyToLocalFile(false, hdfsSrcPath, localDstPath, true);
            log.info("文件下载成功: {} -> {}", hdfsPath, localPath);
            return true;
        } catch (IOException e) {
            log.error("文件下载失败: {} -> {}", hdfsPath, localPath, e);
            return false;
        }
    }

    /**
     * 下载HDFS文件为字节数组
     */
    public byte[] downloadAsBytes(String hdfsPath) {
        try (FSDataInputStream inputStream = fileSystem.open(new Path(hdfsPath))) {
            return IOUtils.toByteArray(inputStream);
        } catch (IOException e) {
            log.error("下载文件为字节数组失败: {}", hdfsPath, e);
            return null;
        }
    }

    /**
     * 下载HDFS文件为字符串
     */
    public String downloadAsString(String hdfsPath) {
        byte[] bytes = downloadAsBytes(hdfsPath);
        return bytes != null ? new String(bytes, StandardCharsets.UTF_8) : null;
    }

    /**
     * 获取HDFS文件输入流
     */
    public FSDataInputStream openStream(String hdfsPath) {
        try {
            return fileSystem.open(new Path(hdfsPath));
        } catch (IOException e) {
            log.error("打开HDFS文件流失败: {}", hdfsPath, e);
            return null;
        }
    }

    // ==================== 文件列表操作 ====================

    /**
     * 列出目录内容
     */
    public List<FileInfo> listFiles(String path) {
        return listFiles(path, null);
    }

    /**
     * 列出目录内容（带过滤器）
     */
    public List<FileInfo> listFiles(String path, PathFilter filter) {
        try {
            Path hdfsPath = new Path(path);
            if (!fileSystem.exists(hdfsPath)) {
                return Collections.emptyList();
            }

            FileStatus[] statuses = filter != null ? 
                fileSystem.listStatus(hdfsPath, filter) : 
                fileSystem.listStatus(hdfsPath);

            return Arrays.stream(statuses)
                    .map(this::convertToFileInfo)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            log.error("列出目录内容失败: {}", path, e);
            return Collections.emptyList();
        }
    }

    /**
     * 递归列出所有文件
     */
    public List<FileInfo> listFilesRecursive(String path) {
        try {
            RemoteIterator<LocatedFileStatus> fileIterator = 
                fileSystem.listFiles(new Path(path), true);
            
            List<FileInfo> result = new ArrayList<>();
            while (fileIterator.hasNext()) {
                result.add(convertToFileInfo(fileIterator.next()));
            }
            return result;
        } catch (IOException e) {
            log.error("递归列出文件失败: {}", path, e);
            return Collections.emptyList();
        }
    }

    private FileInfo convertToFileInfo(FileStatus status) {
        return FileInfo.builder()
                .path(status.getPath().toString())
                .isDirectory(status.isDirectory())
                .length(status.getLen())
                .modificationTime(status.getModificationTime())
                .owner(status.getOwner())
                .group(status.getGroup())
                .permission(status.getPermission().toString())
                .build();
    }

    // ==================== 文件操作 ====================

    /**
     * 检查文件/目录是否存在
     */
    public boolean exists(String path) {
        try {
            return fileSystem.exists(new Path(path));
        } catch (IOException e) {
            log.error("检查文件存在失败: {}", path, e);
            return false;
        }
    }

    /**
     * 重命名文件/目录
     */
    public boolean rename(String srcPath, String dstPath) {
        try {
            return fileSystem.rename(new Path(srcPath), new Path(dstPath));
        } catch (IOException e) {
            log.error("重命名失败: {} -> {}", srcPath, dstPath, e);
            return false;
        }
    }

    /**
     * 删除文件/目录
     */
    public boolean delete(String path) {
        return delete(path, true);
    }

    /**
     * 删除文件/目录
     */
    public boolean delete(String path, boolean recursive) {
        try {
            return fileSystem.delete(new Path(path), recursive);
        } catch (IOException e) {
            log.error("删除失败: {}", path, e);
            return false;
        }
    }

    /**
     * 获取文件块位置信息
     */
    public BlockLocation[] getFileBlockLocations(String path) {
        try {
            FileStatus fileStatus = fileSystem.getFileStatus(new Path(path));
            return fileSystem.getFileBlockLocations(fileStatus, 0, fileStatus.getLen());
        } catch (IOException e) {
            log.error("获取文件块位置失败: {}", path, e);
            return new BlockLocation[0];
        }
    }

    /**
     * 获取文件状态
     */
    public FileStatus getFileStatus(String path) {
        try {
            return fileSystem.getFileStatus(new Path(path));
        } catch (IOException e) {
            log.error("获取文件状态失败: {}", path, e);
            return null;
        }
    }

    // ==================== 工具方法 ====================

    /**
     * 获取HDFS使用情况
     */
    public FsStatus getFsStatus() {
        try {
            return fileSystem.getStatus();
        } catch (IOException e) {
            log.error("获取HDFS状态失败", e);
            return null;
        }
    }

    /**
     * 生成带时间戳的文件路径
     */
    public String generateTimestampPath(String basePath, String filename) {
        String datePath = new java.text.SimpleDateFormat("yyyy/MM/dd")
                .format(new Date());
        return basePath + "/" + datePath + "/" + 
               System.currentTimeMillis() + "_" + filename;
    }

    /**
     * 文件信息DTO
     */
    @lombok.Builder
    @lombok.Data
    public static class FileInfo {
        private String path;
        private boolean isDirectory;
        private long length;
        private long modificationTime;
        private String owner;
        private String group;
        private String permission;
    }
}