package net.xzh.activiti.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import net.xzh.activiti.common.exception.Asserts;
import net.xzh.activiti.common.model.ResultBean;
import net.xzh.activiti.common.utils.CaptchaUtil;
import net.xzh.activiti.model.User;

@Controller
public class LoginController {

	@Autowired
	private AuthenticationManager authenticationManager;
	
    @GetMapping("/index")
    public String login(Model model) {
        return "index";
    }

    @PostMapping("/login")
    @ResponseBody
    public ResultBean<?> login(HttpSession session,User user, @RequestParam(value = "captcha", required = false) String captcha) {
        String sessionCaptcha = (String)session.getAttribute("captcha");
        // session 中的验证码过期了
        if (sessionCaptcha == null || !sessionCaptcha.equals(captcha.toLowerCase())) {
        	Asserts.fail("验证码错误");
        }
        /**
         * 这是一段调试代码，生产环境不允许使用
         * 使用 AuthenticationManager 进行正式认证
         */
        UsernamePasswordAuthenticationToken authenticationToken = 
            new UsernamePasswordAuthenticationToken(user.getUsername(), user.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        
        return ResultBean.success("登录成功");
    }

    @GetMapping("/logout")
    public String logout(HttpSession session) {
    	if (session != null) {
			session.invalidate();
		}
        return "redirect:login";
    }

    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response,
			HttpSession session) throws IOException {
        //定义图形验证码的长、宽、验证码字符数、干扰元素个数
        CaptchaUtil.Captcha captcha = CaptchaUtil.createCaptcha(140, 38, 4, 10, 30);
        session.setAttribute("captcha", captcha.getCode());
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(captcha.getImage(), "png", os);
    }

}
