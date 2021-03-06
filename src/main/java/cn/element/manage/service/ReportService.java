package cn.element.manage.service;

import cn.element.manage.pojo.common.Report;
import cn.element.manage.mapper.ReportMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReportService {

    @Autowired
    private ReportMapper reportMapper;

    /**
     * 获取数据报表信息
     */
    public List<Report> getReportsByTypeNumber(Integer number){

        QueryWrapper<Report> wrapper = new QueryWrapper<>();

        wrapper.select("rp1_area").groupBy("rp1_area");

        List<Report> reportList = reportMapper.selectList(wrapper);

        reportList.forEach(System.out :: println);

        for (Report report : reportList) {
            report.setUserCountList(reportMapper.selectUserCountByArea(report.getRp1Area()));
        }

        return reportList;
    }


}
