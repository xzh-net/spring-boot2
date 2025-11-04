package net.xzh.kaptcha.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.code.kaptcha.impl.DefaultKaptcha;

@Controller
public class KaptchaController {
    
    @Autowired
    private DefaultKaptcha defaultKaptcha;
    
    /**
     * 首页
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }
    
    /**
     * 生成验证码图片
     */
    @GetMapping("/kaptcha")
    public void defaultKaptcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // 生成验证码文本
        String text = defaultKaptcha.createText();
        
        // 将验证码文本存入session
        HttpSession session = request.getSession();
        session.setAttribute("kaptchaCode", text);
        session.setAttribute("kaptchaTime", System.currentTimeMillis());
        
        // 根据文本生成图片
        BufferedImage image = defaultKaptcha.createImage(text);
        
        // 设置响应头
        response.setDateHeader("Expires", 0);
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        response.setHeader("Pragma", "no-cache");
        response.setContentType("image/jpeg");
        
        // 将图片写入响应流
        ServletOutputStream outputStream = response.getOutputStream();
        ImageIO.write(image, "jpg", outputStream);
        outputStream.flush();
        outputStream.close();
    }
    
    /**
     * 验证验证码
     */
    @PostMapping("/verify")
    @ResponseBody
    public Map<String, Object> verify(@RequestParam("code") String code, 
                                      HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // 从session中获取验证码
        HttpSession session = request.getSession();
        String sessionCode = (String) session.getAttribute("kaptchaCode");
        Long kaptchaTime = (Long) session.getAttribute("kaptchaTime");
        
        // 验证码为空或已过期
        if (sessionCode == null || kaptchaTime == null) {
            result.put("success", false);
            result.put("message", "验证码已过期，请刷新重试");
            return result;
        }
        
        // 验证码5分钟有效
        if (System.currentTimeMillis() - kaptchaTime > 5 * 60 * 1000) {
            session.removeAttribute("kaptchaCode");
            session.removeAttribute("kaptchaTime");
            result.put("success", false);
            result.put("message", "验证码已过期，请刷新重试");
            return result;
        }
        
        // 忽略大小写比较验证码
        if (sessionCode.equalsIgnoreCase(code)) {
            // 验证成功后清除session中的验证码
            session.removeAttribute("kaptchaCode");
            session.removeAttribute("kaptchaTime");
            result.put("success", true);
            result.put("message", "验证码正确");
        } else {
            result.put("success", false);
            result.put("message", "验证码错误");
        }
        
        return result;
    }
    
    /**
     * 刷新验证码
     */
    @GetMapping("/refreshKaptcha")
    @ResponseBody
    public Map<String, Object> refreshKaptcha(HttpServletRequest request) {
        Map<String, Object> result = new HashMap<>();
        
        // 生成新的验证码文本
        String text = defaultKaptcha.createText();
        
        // 将验证码文本存入session
        HttpSession session = request.getSession();
        session.setAttribute("kaptchaCode", text);
        session.setAttribute("kaptchaTime", System.currentTimeMillis());
        
        result.put("success", true);
        result.put("message", "验证码已刷新");
        
        return result;
    }
}