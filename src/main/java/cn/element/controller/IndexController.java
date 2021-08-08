package cn.element.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class IndexController {

    @RequestMapping("/")
    public String index(){

        return "HelloWorld!";
    }

    @PostMapping("/userLogin")
    public String login(@RequestParam("username") String username,
                        @RequestParam("password") String password) {

        return "login";
    }
}
