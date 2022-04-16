package cn.element.manage.test;

import cn.element.manage.test.aspect.Eatable;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TestAspect {

    @Autowired
    private Eatable person;
    
    @Test
    public void testAspect() {
        person.eat();
    }

}
