package cn.element.manage.controller;

import cn.element.manage.common.Constant;
import cn.element.manage.common.ResultInfo;
import cn.element.manage.pojo.common.Report;
import cn.element.manage.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PreAuthorize("hasAuthority('report:select')")
    @GetMapping("/type/{number}")
    public ResultInfo getReportData(@PathVariable("number") Integer number) {
        List<Report> list = reportService.getReportsByTypeNumber(number);

        if (!CollectionUtils.isEmpty(list)) {
            return ResultInfo.ok(Constant.SELECT_SUCCESS,list);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }
}
