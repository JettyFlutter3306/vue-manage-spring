package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.Report;
import cn.element.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Autowired
    private ReportService reportService;

    @GetMapping("/type/{number}")
    public ResultInfo getReportData(@PathVariable("number") Integer number){

        List<Report> list = reportService.getReportsByTypeNumber(number);

        if(!CollectionUtils.isEmpty(list)){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }
}
