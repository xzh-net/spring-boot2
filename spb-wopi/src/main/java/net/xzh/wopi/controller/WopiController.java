package net.xzh.wopi.controller;


import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

import cn.hutool.core.io.FileUtil;

@RestController
@RequestMapping("/wopi")
public class WopiController {

	/**
	 * 获取文件基本信息，根据文件信息初始化工具栏和状态栏
	 * @param fileId
	 * @return
	 */
    @GetMapping("/files/{fileId}")
    public ResponseEntity<Object> checkFileInfo(@PathVariable String fileId) {
        // 根据fileId获取文件的元数据信息
        String jsonStr = "{\n" +
                "  \"BaseFileName\": \"test.docx\",\n" +
                "  \"OwnerId\": \"12345678\",\n" +
                "  \"Size\": 102400,\n" +
                "  \"Version\": \"1.0\",\n" +
                "  \"UserId\": \"12345678\",\n" +
                "  \"UserFriendlyName\": \"xzh\",\n" +
                "  \"UserCanWrite\": true,\n" +
                "  \"UserCanRename\": true,\n" +
                "  \"UserCanNotWriteRelative\": false,\n" +
                "  \"UserCanEdit\": true,\n" +
                "  \"UserCanExport\": true,\n" +
                "  \"SupportsUpdate\": true,\n" +
//                "  \"SupportsLocks\": true,\n" +
                "  \"SupportsRename\": true,\n" +
                "  \"SupportsExtendedLockLength\": true,\n" +
                "  \"SupportsUserInfo\": true\n" +
                "}";
        JSONObject jsonObject = JSON.parseObject(jsonStr);
        return new ResponseEntity<>(jsonObject, HttpStatus.OK);
    }

    /**
     * 文件下载
     * @param fileId
     * @return
     * @throws IOException
     */
	@GetMapping("/files/{fileId}/contents")
	public ResponseEntity<byte[]> getFile(@PathVariable String fileId) throws IOException {
		// 根据fileId获取文件的内容
		byte[] fileContent = getFileContent(fileId);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		headers.setContentDispositionFormData("attachment", "test.docx");
		headers.setContentLength(fileContent.length);
		return new ResponseEntity<>(fileContent, headers, HttpStatus.OK);
	}
	
	/**
	 * 文件保存
	 * @param file
	 * @param fileName
	 * @return
	 */
	@RequestMapping(value = "/files/{fileId}/contents", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(HttpServletRequest request,@PathVariable String fileId) {
		System.out.println(fileId+",写入文件");
        try {
        	Path filePath = Paths.get("/home", "test.docx");
        	Files.copy(request.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);
            return ResponseEntity.status(200).body(null);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(404).body("Not possible to get the file content.");
        }
    }

	private byte[] getFileContent(String fileId) throws IOException {
		// 实现获取文件内容的逻辑，返回文件的二进制数据
		return FileUtil.readBytes(new File("/home/test.docx"));
	}
}