package myapp.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomePageController {

    @RequestMapping("")
    public String home(){
        return "homePage";
    }

    @GetMapping("/homePage")
    public String homePage(){
        return "homePage";
    }

    @GetMapping("/signin")
    public String login(){
        return "login";
    }

    @PostMapping("/signin")
    public String loginPost(){
        return "/";
    }
}
