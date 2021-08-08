package cn.element;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication
public class ApplicationStarter {

    public static void main(String[] args) {

        SpringApplication.run(ApplicationStarter.class, args);
    }
}