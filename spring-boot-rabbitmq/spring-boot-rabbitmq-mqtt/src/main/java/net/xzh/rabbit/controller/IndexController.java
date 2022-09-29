package net.xzh.rabbit.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import io.swagger.annotations.Api;

/**
 * Created by macro on 2020/9/16.
 */
@Api(tags = "首页")
@Controller
@RequestMapping("/page")
public class IndexController {

    @GetMapping("/index")
    public String index(){
        return "index";
    }
}
