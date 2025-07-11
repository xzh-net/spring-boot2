package net.xzh.controller;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import cn.hutool.core.io.FileUtil;
import net.xzh.model.User;

/**
 * 获取数据常用6种方式
 * 
 * @author CR7
 *
 */

@RestController
public class UserController {

	/**
	 * 使用 @RequestParam 注解，接收 URL 查询参数或表单参数 请求示例：/user?id=100&name=Tom
	 * 
	 * @param id   必传参数
	 * @param name 可选参数（带默认值）
	 * @return
	 */
	@GetMapping("/user/")
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
	@GetMapping("/user/{id}")
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

	@PostMapping("/user/raw")
	public User addUser(@RequestBody User user) {
		return user;
	}

	/**
	 * 使用 @ModelAttribute 注解，接收表单数据并绑定到对象（支持 GET/POST） 请求示例：{ "id": 100, "name":
	 * "Tom" }
	 * 
	 * @param user
	 * @return
	 */
	@PostMapping("/user/form-data")
	public User addUser2(@ModelAttribute User user) {
		return user;
	}

	/**
	 * 上传文件form-data
	 * 
	 * @param file
	 * @return
	 */
	@PostMapping("/upload")
	public String upload(@RequestParam("file") MultipartFile file) {
		// file name
		String fileName = file.getOriginalFilename();
		String newFileName = System.currentTimeMillis() + "." + FileUtil.extName(fileName);
		// type
		String contentType = file.getContentType();
		return "upload success:" + newFileName;
	}

	/**
	 * data-binary 不是 multipart/form-data 格式，无法使用@RequestBody和MultipartFile方式接收
	 * 不依赖spring注解，使用 HttpServletRequest 读取原始数据
	 * @param request
	 * @return
	 */
	@PostMapping("/binary")
    public String binary(HttpServletRequest request) {
        try (InputStream inputStream = request.getInputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                // 处理数据（例如保存到文件或数据库）
            }
            return "上传成功！";
        } catch (IOException e) {
            e.printStackTrace();
            return "上传失败！";
        }
    }
	
}