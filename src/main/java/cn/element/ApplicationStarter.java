package cn.element;

import cn.element.util.SpringUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {

        ApplicationContext context = SpringApplication.run(ApplicationStarter.class, args);

        SpringUtil.setApplicationContext(context);
    }
}