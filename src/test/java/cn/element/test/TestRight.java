package cn.element.test;

import cn.element.mapper.RightMapper;
import cn.element.pojo.Right;
import cn.element.service.RightService;
import cn.element.service.RoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.*;
import java.util.stream.Collectors;

@SpringBootTest
public class TestRight {

    @Autowired
    private RightMapper rightMapper;

    @Autowired
    private RightService rightService;

    @Autowired
    private RoleService roleService;

    @Test
    public void test01() {
        List<Right> list = rightMapper.selectList(null);

        List<Right> list1 = list.stream().sorted(Comparator.comparing(Right::getLevel).reversed()).collect(Collectors.toList());

        Map<Integer,Right> map = new HashMap<>();

        list1.forEach(r -> map.put(r.getId(),r));

        for (Right right : list1) {
            if(map.containsKey(right.getParentId())){
                map.get(right.getParentId()).getChildren().add(right);
            }
        }

        List<Right> list2 = list1.stream().filter(s -> s.getParentId() == 0).collect(Collectors.toList());

        list2.forEach(System.out :: println);
    }

    @Test
    public void test02() {
        List<Right> idList = rightService.getRightIdAndPIdList();

        List<Integer> listByRightIdList = rightService.getCascadeListByRightId(101, new ArrayList<>(), idList);

        listByRightIdList.forEach(System.out::println);
    }

    @Test
    public void test03() {
        List<Right> rightList = rightMapper.selectRightListByRoleId(42);

        rightList.forEach(System.out::println);

    }
}
