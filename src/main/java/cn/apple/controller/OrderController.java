package cn.apple.controller;

import cn.apple.common.Constant;
import cn.apple.common.ResultInfo;
import cn.apple.pojo.Order;
import cn.apple.service.OrderService;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public ResultInfo getOrderList(@RequestParam("pageNum") Integer pageNum,
                                   @RequestParam("pageSize") Integer pageSize,
                                   @RequestParam("query") String query){

        Page<Order> page = orderService.getOrderList(pageNum, pageSize, query);

        if(!CollectionUtils.isEmpty(page.getRecords())){
            return ResultInfo.ok(Constant.SELECT_SUCCESS,page);
        }

        return ResultInfo.serverError(Constant.SYSTEM_ERROR);
    }
}
