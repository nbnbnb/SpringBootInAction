package me.zhangjin.springboot.demo.basic;

import me.zhangjin.springboot.demo.basic.config.AuthorSettings;
import me.zhangjin.springboot.demo.starter.HelloService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class BasicDemoApplication {

    // 使用 Spirng Boot 的自动配置进行注入
    @Autowired
    private HelloService helloService;

    @Autowired
    private AuthorSettings authorSettings;

    // 注入 application.properties 中的配置信息
    @Value("${book.author}")
    private String bookAuthor;

    @Value("${book.name}")
    private String bookName;

    public static void main(String[] args) {
        SpringApplication.run(BasicDemoApplication.class, args);
    }

    @RequestMapping("/book")
    public String book() {
        //return "book name is: " + bookName + " and book author is: " + bookAuthor;
        return "book name is: " + authorSettings.getName() + " and book author is: " + authorSettings.getAuthor();
    }

    @RequestMapping("/")
    public String index() {
        return "Hello Spring Boot";
    }

    @RequestMapping("/starter")
    public String hello() {
        return helloService.sayHello();
    }
}
