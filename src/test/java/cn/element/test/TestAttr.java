package cn.element.test;

import cn.element.pojo.common.Attribute;
import cn.element.service.AttributeService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestAttr {

    @Autowired
    private AttributeService attributeService;

    @Test
    public void test01(){

        List<Attribute> list = attributeService.getAttributesByCatId(1191, "only");

        list.forEach(System.out :: println);
    }
}
