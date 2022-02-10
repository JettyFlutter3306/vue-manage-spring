package cn.element.controller;

import cn.element.common.Constant;
import cn.element.common.ResultInfo;
import cn.element.pojo.Order;
import cn.element.service.OrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PreAuthorize("hasAuthority('order:select')")
    @GetMapping
    public ResultInfo getOrderList(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("query") String query) {
        Page<Order> page = orderService.getOrderList(pageNum, pageSize, query);

        if (!CollectionUtils.isEmpty(page.getRecords())) {
            return ResultInfo.ok(Constant.SELECT_SUCCESS,page);
        }

        return ResultInfo.notFound(Constant.SYSTEM_ERROR);
    }
}
