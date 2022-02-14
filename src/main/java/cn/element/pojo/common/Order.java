package cn.element.pojo.common;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName("tb_order")
public class Order {

    @TableId
    private Integer orderId;//订单id

    private Integer userId;//会员id

    private String orderNumber;//订单编号

    private Double orderPrice;//订单总金额

    private String orderPay;//支付方式 0: 未支付 1: 支付宝 2: 微信 3: 银行卡

    private String isSend;//是否发货

    private String tradeNo;//支付宝交易的流水号码

    private String orderBillTitle;//发票抬头 enum 个人 公司

    private String orderBillCompany;//公司名称

    private String orderBillContent;//发票内容

    private String consigneeAddr;//收货人地址

    private Integer payStatus;//订单状态 0: 未付款 1: 已付款

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;//创建时间

    @TableField(fill = FieldFill.INSERT_UPDATE)
    @JsonIgnore
    private Date updateTime;//修改时间

}
