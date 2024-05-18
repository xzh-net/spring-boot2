package net.xzh.emqx.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.PostConstruct;
import java.util.HashMap;

/**
 * HTTP 认证
 * @author admin
 *
 */
@RestController
@RequestMapping("/mqtt")
public class AuthController {
    
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    
    private HashMap<String,String> users;
    
    @PostConstruct
    public void init(){
        users = new HashMap<>();
        users.put("user","123456");
        users.put("emq-client2","123456");
        users.put("emq-client3","123456");
    }
    
    
    @PostMapping("/auth")
    public ResponseEntity auth(@RequestParam("clientid") String clientid,
                               @RequestParam("username") String username,
                               @RequestParam("password") String password,
                               @RequestParam("ip") String ip){
        
        log.info("emqx http认证组件开始调用任务服务完成认证,clientid={},username={},password={},ip={}",clientid,username,password,ip);

        String value = users.get(username);
        if(StringUtils.isEmpty(value)){
        	return ResponseEntity.status(HttpStatus.OK).body("ignore");
        }
        
        if(!value.equals(password)){
            return new ResponseEntity(HttpStatus.UNAUTHORIZED);
        }
 
        return new ResponseEntity(HttpStatus.OK);
        
    }
    
    
    
}
