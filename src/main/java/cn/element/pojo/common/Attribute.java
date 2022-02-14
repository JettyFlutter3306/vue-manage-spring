package cn.element.pojo.common;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@TableName(value = "tb_attribute")
public class Attribute {

    @TableId(type = IdType.AUTO)
    private Integer attrId; //属性id

    private String attrName; //属性名称

    private Integer catId; //分类id

    private String attrSel; //选择方式 only输入框(静态属性),many单选框或者下拉列表(动态参数)

    private String attrWrite; //录入方式 manual手工录入 list从列表选择

    private String attrVal; //可选值列表信息 例如颜色：白色,红色,绿色,多个可选值通过逗号分隔

    @JsonIgnore
    private Date deleteTime; //删除时间


}
