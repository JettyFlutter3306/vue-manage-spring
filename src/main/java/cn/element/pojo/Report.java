package cn.element.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@TableName("tb_report_1")
public class Report {

    @TableId
    private Integer id;

    private String rp1UserCount;//用户数量

    private String rp1Area;//地区

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date rp1Date;//时间

    @TableField(exist = false)
    private List<Integer> userCountList;


}
