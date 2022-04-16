package cn.element.manage.test;

import cn.element.manage.pojo.common.Attribute;
import cn.element.manage.service.AttributeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestAttr {

    @Autowired
    private AttributeService attributeService;

    @Test
    public void test01() {
        List<Attribute> list = attributeService.getAttributesByCatId(1191, "only");
        list.forEach(System.out::println);
    }
}
