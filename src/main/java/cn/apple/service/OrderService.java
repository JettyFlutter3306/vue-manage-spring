package cn.apple.service;

import cn.apple.mapper.OrderMapper;
import cn.apple.pojo.Order;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderMapper orderMapper;

    /**
     * 查询订单列表
     */
    public Page<Order> getOrderList(Integer pageNum,Integer pageSize,String query){

        Page<Order> page = new Page<>(pageNum,pageSize);

        QueryWrapper<Order> wrapper = new QueryWrapper<>();

        wrapper
                .like("order_bill_title",query)
                .or()
                .like("order_bill_company",query)
                .or()
                .like("order_bill_content",query);

        orderMapper.selectPage(page,wrapper);

        return page;
    }


}
