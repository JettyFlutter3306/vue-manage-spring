package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.Report;
import cn.apple.service.ReportService;
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
