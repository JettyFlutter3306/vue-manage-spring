package cn.apple.test;

import cn.apple.mapper.RightMapper;
import cn.apple.pojo.Right;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
public class TestRight {

    @Autowired
    private RightMapper rightMapper;

    @Test
    public void test01(){

        List<Right> list = rightMapper.selectList(null);

        List<Right> list1 = list.stream().sorted(Comparator.comparing(Right::getLevel).reversed()).collect(Collectors.toList());

        Map<Integer,Right> map = new HashMap<>();

        list1.forEach(r -> {
            map.put(r.getId(),r);
        });

        for (Right right : list1) {
            if(map.containsKey(right.getParentId())){
                map.get(right.getParentId()).getChildren().add(right);
            }
        }

        List<Right> list2 = list1.stream().filter(s -> s.getParentId() == 0).collect(Collectors.toList());

        list2.forEach(System.out :: println);
    }
}
