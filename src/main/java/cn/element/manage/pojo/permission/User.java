package cn.element.manage.pojo.permission;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)
@TableName("tb_user")
public class User {

    public static final int USER_STATUS_ON = 1;
    public static final int USER_STATUS_OFF = 0;

    @TableId
    private Integer id;

    @NotBlank(message="用户名不能为空")
    private String username;

    private String password;

    @Pattern(regexp = "^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\\.[a-zA-Z0-9_-])")
    private String email;

    @Pattern(regexp = "^(0|86|17951)?(13[0-9]|15[0123456789]|17[678]|18[0-9]|14[57])[0-9]{8}$")
    private String mobile;

    private Integer status; //用户状态,默认1

    @TableField(fill = FieldFill.INSERT)
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime; //创建时间

    @TableField(exist = false)
    private List<Role> roleList;
}
