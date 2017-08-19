package me.zhangjin.springboot.demo.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


// 使用自动配置功能
// author 表示配置文件中的前缀

@Component
@ConfigurationProperties(prefix = "book")
public class AuthorSettings {

    private String name;
    private String author;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
