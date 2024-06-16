package net.xzh.emqx.controller;

import java.util.Map;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 规则引擎
 */
@RestController
@RequestMapping("/mqtt")
public class RuleController {
    
    @RequestMapping("/process")
    public void process(@RequestBody Map<String,Object> params){
        System.out.println("规则引擎--------");
        params.entrySet().stream().forEach(x->{
            System.out.println(x.getKey() + ":" + x.getValue());
        });
    }
}
