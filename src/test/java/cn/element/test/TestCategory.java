package cn.element.test;

import cn.element.pojo.common.Category;
import cn.element.service.CategoryService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
public class TestCategory {

    @Autowired
    private CategoryService categoryService;

    /**
     * 测试获取一个分类对象的所有下级节点的id,返回一个List<Integer>集合
     */
    @Test
    public void test01(){

        List<Category> idList = categoryService.getCategoryIdAndPIdList();

        List<Integer> list = categoryService.getSubIdListByParentId(40, new ArrayList<>(), idList);

        System.out.println("list = " + list);
        System.out.println("list.size() = " + list.size());
    }
}
