package cn.element.manage.test.aspect;

import org.springframework.stereotype.Component;

@Component
public class Person implements Eatable {

    @Override
    public void eat() {
        System.out.println("正在吃饭...");
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    
}
