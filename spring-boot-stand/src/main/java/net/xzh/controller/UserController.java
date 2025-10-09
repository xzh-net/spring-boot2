package net.xzh.controller;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.xzh.model.User;

/**
 * 获取数据常用6种方式
 * 
 * @author CR7
 *
 */

@RestController
@RequestMapping("/user")
public class UserController {

	/**
	 * 使用 @RequestParam 注解，接收 URL 查询参数或表单参数 请求示例：/user?id=100&name=Tom
	 * 
	 * @param id   必传参数
	 * @param name 可选参数（带默认值）
	 * @return
	 */
	@GetMapping("")
	public String getUser(@RequestParam("id") Long id,
			@RequestParam(value = "name", defaultValue = "Guest") String name) {
		return "ID: " + id + ", Name: " + name;
	}

	/**
	 * 使用 @PathVariable 注解，接收 RESTful 风格的路径参数 请求示例：/user/100
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
	public String getUser2(@PathVariable Long id) {
		return "ID: " + id;
	}

	/**
	 * 使用 @RequestBody 注解，接收 JSON/XML 格式的请求体（如 POST/PUT 请求） 请求示例：{ "id": 100,
	 * "name": "Tom" }
	 * 
	 * @param user
	 * @return
	 */

	@PostMapping("/json")
	public User json(@RequestBody User user) {
		return user;
	}

	/**
	 * 使用 @ModelAttribute 注解，接收表单数据并绑定到对象（支持 GET/POST） 请求示例：{ "id": 100, "name":
	 * "Tom" }
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/form-urlencoded")
	public User formUrlencoded(@ModelAttribute User user) {
		return user;
	}

	/**
	 * 使用MultipartFile接收文件（可多个）
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	public ResponseEntity<String> upload(@RequestParam("file") MultipartFile file) {
		// file name
		String fileName = file.getOriginalFilename();
		String newFileName = System.currentTimeMillis() + "." + FilenameUtils.getExtension(fileName);
		// type
		String contentType = file.getContentType();
		return ResponseEntity.ok("上传成功。文件: "+newFileName +" 类型: "+ contentType);
	}

	/**
	 * binary 不是 multipart/form-data 格式，无法使用@RequestBody和MultipartFile方式接收
	 * 不依赖spring注解，使用 HttpServletRequest 读取原始数据
	 * 
	 * @param request
	 * @return
	 */
	@PostMapping("/binary")
	public ResponseEntity<String> binary(HttpServletRequest request) {
		// 定义文件保存路径（根据实际需求调整）
		Path uploadPath = FileSystems.getDefault().getPath("d:/");
		try {
			// 确保上传目录存在
			Files.createDirectories(uploadPath);
			// 生成唯一文件名
			String filename = "upload_" + System.currentTimeMillis() + ".bin";
			Path filePath = uploadPath.resolve(filename);

			try (InputStream inputStream = request.getInputStream();
					OutputStream outputStream = Files.newOutputStream(filePath, StandardOpenOption.CREATE_NEW)) {

				// 使用更高效的缓冲区大小（根据实际情况调整）
				byte[] buffer = new byte[8192]; // 8KB缓冲区
				int bytesRead;
				long totalBytes = 0;

				while ((bytesRead = inputStream.read(buffer)) != -1) {
					outputStream.write(buffer, 0, bytesRead);
					totalBytes += bytesRead;
				}
				return ResponseEntity.ok("上传成功。文件大小: " + totalBytes + " bytes");

			} catch (FileAlreadyExistsException e) {
				return ResponseEntity.status(HttpStatus.CONFLICT).body("文件已存在");
			}

		} catch (IOException e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("上传失败: " + e.getMessage());
		}
	}
}