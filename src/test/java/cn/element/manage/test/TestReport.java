package cn.element.manage.test;

import cn.element.manage.mapper.ReportMapper;
import cn.element.manage.pojo.common.Report;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class TestReport {

    @Autowired
    private ReportMapper reportMapper;

    @Test
    public void test01() {
        QueryWrapper<Report> wrapper = new QueryWrapper<>();

        wrapper.select("rp1_area").groupBy("rp1_area");

        List<Report> reportList = reportMapper.selectList(wrapper);

        reportList.forEach(System.out :: println);

        for (Report report : reportList) {
            report.setUserCountList(reportMapper.selectUserCountByArea(report.getRp1Area()));
        }

        reportList.forEach(System.out::println);
    }

    @Test
    public void test02() {
        List<Integer> list = reportMapper.selectUserCountByArea("华东");
        System.out.println("list = " + list);
    }
}
